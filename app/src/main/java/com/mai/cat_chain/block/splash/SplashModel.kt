package com.mg.axechen.wanandroid.block.login

import com.mai.cat_chain.block.splash.SplashContract
import com.mai.cat_chain.net.NetWorkManager
import io.reactivex.Observable
import com.mai.cat_chain.net.respone.Response
import com.mai.cat_chain.utils.MLog

/**
 * Created by maijuntian on 2018/3/20.
 * 登陆model
 */
class SplashModel : SplashContract.Model {
    override fun session(): Observable<Response<String>> {
        return NetWorkManager.getInstance().getRequest()!!.openSession()
    }
}