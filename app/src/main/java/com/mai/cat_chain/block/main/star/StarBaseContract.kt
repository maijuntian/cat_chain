package com.mai.cat_chain.block.main.star

import com.mai.cat_chain.base.BasePresenter
import com.mai.cat_chain.javabean.NotCollectedM
import com.mai.cat_chain.javabean.Rank
import io.reactivex.Observable
import com.mai.cat_chain.net.respone.Response

/**
 * Created by maijuntian on 2018/3/20.
 */
interface StarBaseContract {

    interface View {
        fun queryMSuccess(datas: List<NotCollectedM>)
        fun collectedMSuccess(id: String, number: Float)
        fun collectedFail()
        fun queryMRankSuccess(datas: Rank)
        fun queryHRankSuccess(datas: Rank)
        fun fail(msg: String)
    }

    interface Presenter : BasePresenter {
        fun queryM()
        fun queryRank(type: String)
        fun collectedM(id: String, coinNumber: Float)
    }

    interface Model {
        fun queryUserNotCollectedM(): Observable<Response<List<NotCollectedM>>>
        fun queryRank(type: String): Observable<Response<Rank>>
        fun collectedM(id: String, coinNumber: Float): Observable<Response<Any>>
    }
}