package com.mai.cat_chain.block.main

import com.mai.cat_chain.javabean.User
import com.mai.cat_chain.net.NetWorkManager
import com.mai.cat_chain.utils.Key
import com.mai.cat_chain.utils.SharedPreferencesUtils
import io.reactivex.Observable
import com.mai.cat_chain.net.respone.Response

/**
 * Created by maijuntian on 2018/6/19.
 */

class MainModel : MainContract.Model {
    override fun queryUserInfo(): Observable<Response<User>> {
        return NetWorkManager.getInstance().getRequest()!!.queryUserMHInfo("mstar/queryUserMHInfo?sessionId=" +
                SharedPreferencesUtils.getString(Key.SESSION_ID))
    }
}

