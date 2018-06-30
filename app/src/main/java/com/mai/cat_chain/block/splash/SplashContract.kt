package com.mai.cat_chain.block.splash

import com.mai.cat_chain.base.BasePresenter
import io.reactivex.Observable
import com.mai.cat_chain.net.respone.Response

/**
 * Created by maijuntian on 2018/3/20.
 * 启动页Activity
 */
interface SplashContract {

    interface View {
        fun sessionSuccess()
        fun sessionFail(errorMsg: String)
        fun loginSuccess()
    }

    interface Presenter : BasePresenter {
        fun session()
    }

    interface Model {
        fun session(): Observable<Response<String>>
    }
}