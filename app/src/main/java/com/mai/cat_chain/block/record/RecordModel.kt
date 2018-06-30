package com.mai.cat_chain.block.record

import com.mai.cat_chain.javabean.*
import com.mai.cat_chain.net.NetWorkManager
import com.mai.cat_chain.net.interceptor.HTTPClientUtil
import com.mai.cat_chain.net.request.MParams
import com.mai.cat_chain.utils.Key
import com.mai.cat_chain.utils.SharedPreferencesUtils
import io.reactivex.Observable
import com.mai.cat_chain.net.respone.Response

/**
 * Created by maijuntian on 2018/6/19.
 */

class RecordModel : RecordContract.Model {
    override fun queryHRecord(beginTime: String, endTime: String): Observable<Response<RecordList>> {
        return NetWorkManager.getInstance().getRequest()!!.getHvalueByTime(HTTPClientUtil.getRequestUrl("mstar/getHvalueByTime",
                MParams().add("beginTime", beginTime)
                        .add("endTime", endTime),
                SharedPreferencesUtils.getString(Key.SESSION_ID),
                SharedPreferencesUtils.getString(Key.USER_KEY)))
    }

    override fun queryMRecord(beginTime: String, endTime: String): Observable<Response<RecordList>> {
        return NetWorkManager.getInstance().getRequest()!!.getMDrilldetailed(HTTPClientUtil.getRequestUrl("mstar/getMDrilldetailed",
                MParams().add("beginTime", beginTime)
                        .add("endTime", endTime),
                SharedPreferencesUtils.getString(Key.SESSION_ID),
                SharedPreferencesUtils.getString(Key.USER_KEY)))
    }

}

