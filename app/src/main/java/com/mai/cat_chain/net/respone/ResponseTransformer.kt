package com.mai.cat_chain.net.respone

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function
import com.mai.cat_chain.net.exception.ApiException
import com.mai.cat_chain.net.exception.CustomException
import com.mai.cat_chain.utils.MLog

/**
 * Created by AxeChen on 2018/3/20.
 * rxjava2变换封装，分离数据和异常
 */
class ResponseTransformer {

    companion object {
        fun <T> handleResult(): ObservableTransformer<Response<T>, T> {
            return MyObservableTransformer()
        }
    }

    class MyObservableTransformer<T> : ObservableTransformer<Response<T>, T> {
        override fun apply(upstream: Observable<Response<T>>): ObservableSource<T> {
            return upstream.onErrorResumeNext(ErrorResumeFunction())
                    .flatMap(ResponseFunction())
        }
    }

    /**
     * 非服务器产生的异常，比如解析错误，网络连接错误
     */
    class ErrorResumeFunction<T> : Function<Throwable, ObservableSource<out Response<T>>> {
        override fun apply(t: Throwable): ObservableSource<out Response<T>> {
            return Observable.error(CustomException.handleException(t))
        }
    }

    /**
     * 服务器产生的错误：HTTP错误代码
     */
    class ResponseFunction<T> : Function<Response<T>, ObservableSource<T>> {
        override fun apply(t: Response<T>): ObservableSource<T> {
            val status: Int = t.status
            MLog.log("status--->" + t.status)
            return if (status == 200) {
                Observable.just(t.data)
            } else {
                Observable.error(ApiException("服务器出错了", null, status))
            }
        }
    }
}