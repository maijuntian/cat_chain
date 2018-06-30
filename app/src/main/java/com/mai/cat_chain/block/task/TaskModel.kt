package com.mai.cat_chain.block.task

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

class TaskModel : TaskContract.Model {
    override fun getAdvert(): Observable<Response<MutableList<String>>> {
        return NetWorkManager.getInstance().getRequest()!!.getAllCarousel("mstar/getAllCarousel?sessionId=" +
                SharedPreferencesUtils.getString(Key.SESSION_ID))
    }

    override fun getTodayTask(): Observable<Response<MutableList<TodayTask>>> {
        return NetWorkManager.getInstance().getRequest()!!.getTodayTask("mstar/getTodayTask?sessionId=" +
                SharedPreferencesUtils.getString(Key.SESSION_ID))
    }

    override fun getBaseTask(): Observable<Response<MutableList<BaseTask>>> {
        return NetWorkManager.getInstance().getRequest()!!.getAllBaseTask("mstar/getAllBaseTask?sessionId=" +
                SharedPreferencesUtils.getString(Key.SESSION_ID))
    }

}

