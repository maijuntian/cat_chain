package com.mai.cat_chain.block.login

import com.mai.cat_chain.base.BasePresenter
import io.reactivex.Observable
import com.mai.cat_chain.net.respone.Response

/**
 * Created by maijuntian on 2018/3/20.
 * 登陆注册Activity
 */
interface LoginContract {

    interface View {
        fun loginSuccess()
        fun loginFail(errorMsg: String)
        fun userNameIsEmpty()
        fun passwordIsEmpty()
    }

    interface Presenter : BasePresenter {
        fun login(userName: String, password: String)
    }

    interface Model {
        fun login(userName: String, password: String): Observable<Response<String>>
    }
}