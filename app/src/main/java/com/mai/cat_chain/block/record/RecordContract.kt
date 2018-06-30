package com.mai.cat_chain.block.record

import com.mai.cat_chain.base.BasePresenter
import com.mai.cat_chain.javabean.*
import io.reactivex.Observable
import com.mai.cat_chain.net.respone.Response

/**
 * Created by maijuntian on 2018/3/20.
 */
interface RecordContract {

    interface View {
        fun queryHRecordSuccess(data: RecordList)
        fun queryMRecordSuccess(data: RecordList)
        fun fail(msg: String)
    }

    interface Presenter : BasePresenter {
        fun queryHRecord(beginTime: String, endTime: String)
        fun queryMRecord(beginTime: String, endTime: String)
    }

    interface Model {
        fun queryHRecord(beginTime: String, endTime: String): Observable<Response<RecordList>>
        fun queryMRecord(beginTime: String, endTime: String): Observable<Response<RecordList>>
    }
}