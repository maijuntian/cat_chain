package com.mai.cat_chain.block.main.star

import com.mai.cat_chain.javabean.NotCollectedM
import com.mai.cat_chain.javabean.Rank
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

class StarBaseModel : StarBaseContract.Model {
    override fun queryRank(type: String): Observable<Response<Rank>> {
        return NetWorkManager.getInstance().getRequest()!!.rank(HTTPClientUtil.getRequestUrl("mstar/getCoinRank",
                MParams().add("start", 0)
                        .add("end", 99)
                        .add("type", type),
                SharedPreferencesUtils.getString(Key.SESSION_ID),
                SharedPreferencesUtils.getString(Key.USER_KEY)))
    }

    override fun collectedM(id: String, coinNumber: Float): Observable<Response<Any>> {
        return NetWorkManager.getInstance().getRequest()!!.collectedMeowDrill(HTTPClientUtil.getRequestUrl("mstar/collectedMeowDrill",
                MParams().add("id", id)
                        .add("coinNumber", coinNumber),
                SharedPreferencesUtils.getString(Key.SESSION_ID),
                SharedPreferencesUtils.getString(Key.USER_KEY)))
    }

    override fun queryUserNotCollectedM(): Observable<Response<List<NotCollectedM>>> {
        return NetWorkManager.getInstance().getRequest()!!.queryUserNotCollectedM(HTTPClientUtil.getRequestUrl("mstar/queryUserNotCollectedM",
                MParams().add("pageSize", 100)
                        .add("pageNum", 1),
                SharedPreferencesUtils.getString(Key.SESSION_ID),
                SharedPreferencesUtils.getString(Key.USER_KEY)))
    }
}

