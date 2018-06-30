package com.mai.cat_chain.block.main

import com.mai.cat_chain.base.BasePresenter
import com.mai.cat_chain.javabean.NotCollectedM
import com.mai.cat_chain.javabean.Rank
import com.mai.cat_chain.javabean.User
import io.reactivex.Observable
import com.mai.cat_chain.net.respone.Response

/**
 * Created by maijuntian on 2018/3/20.
 */
interface MainContract {

    interface View {
        fun queryUserInfoSuccess(datas: User)
        fun fail(msg: String)
    }

    interface Presenter : BasePresenter {
        fun queryUserInfo()
    }

    interface Model {
        fun queryUserInfo(): Observable<Response<User>>
    }
}