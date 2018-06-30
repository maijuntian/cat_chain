package com.mai.cat_chain.net

import com.mai.cat_chain.net.converter.GsonConverterFactory
import com.mai.cat_chain.net.request.Request
import com.mai.cat_chain.utils.MLog
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * Created by maijuntian on 2018/3/19.
 *
 * 网络请求管理类
 */
class NetWorkManager private constructor() {

    companion object {
        var mManger: NetWorkManager? = null
        var mRetrofit: retrofit2.Retrofit? = null
        var mRequest: Request? = null

        // 初始化NetWorkManager单例
        fun getInstance(): NetWorkManager {
            if (mManger == null) {
                synchronized(NetWorkManager::class) {
                    if (mManger == null) {
                        mManger = NetWorkManager()
                    }
                }
            }
            return mManger!!
        }
    }

    fun getRequest(): Request? {
        if (mRequest == null) {
            synchronized(Request::class) {
                mRequest = mRetrofit?.create(Request::class.java)
            }
        }
        return mRequest
    }

    /**
     * http请求日志
     * @param message
     */
    protected fun logHttpMessage(message: String) {
        MLog.log(message)
    }


    fun init() {

        val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> logHttpMessage(message) })
        logging.level = HttpLoggingInterceptor.Level.BODY

        // 初始化OKHTTP
        val client: okhttp3.OkHttpClient.Builder = okhttp3.OkHttpClient.Builder().apply {
            addInterceptor(logging)
        }

        // 初始化Retrofit
        mRetrofit = retrofit2.Retrofit.Builder().apply {
            client(client.build())
            baseUrl(Request.HOST)
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            addConverterFactory(GsonConverterFactory.create())


        }.build()
    }

}