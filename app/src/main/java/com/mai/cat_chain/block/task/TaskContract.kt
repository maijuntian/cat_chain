package com.mai.cat_chain.block.task

import com.mai.cat_chain.base.BasePresenter
import com.mai.cat_chain.javabean.*
import io.reactivex.Observable
import com.mai.cat_chain.net.respone.Response

/**
 * Created by maijuntian on 2018/3/20.
 */
interface TaskContract {

    interface View {
        fun getAdvertSuccess(data: MutableList<String>)
        fun getTodayTaskSuccess(data: MutableList<TodayTask>)
        fun getBaseTaskSuccess(data: MutableList<BaseTask>)
        fun fail(msg: String)
    }

    interface Presenter : BasePresenter {
        fun getAdvert()
        fun getTodayTask()
        fun getBaseTask()
    }

    interface Model {
        fun getAdvert(): Observable<Response<MutableList<String>>>
        fun getTodayTask(): Observable<Response<MutableList<TodayTask>>>
        fun getBaseTask(): Observable<Response<MutableList<BaseTask>>>
    }
}