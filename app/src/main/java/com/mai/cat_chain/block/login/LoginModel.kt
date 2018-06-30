package com.mg.axechen.wanandroid.block.login

import com.mai.cat_chain.block.login.LoginContract
import com.mai.cat_chain.net.NetWorkManager
import com.mai.cat_chain.net.interceptor.HTTPClientUtil
import com.mai.cat_chain.net.request.MParams
import io.reactivex.Observable
import com.mai.cat_chain.net.respone.Response
import com.mai.cat_chain.utils.Key
import com.mai.cat_chain.utils.SharedPreferencesUtils


/**
 * Created by maijuntian on 2018/3/20.
 * 登陆model
 */
class LoginModel : LoginContract.Model {

    override fun login(userName: String, password: String): Observable<Response<String>> {


        return NetWorkManager.getInstance().getRequest()!!.userLogin(HTTPClientUtil.getRequestUrl("accept/doLogin", MParams().add("userName", userName)
                .add("pwd", password), SharedPreferencesUtils.getString(Key.SESSION_ID)))
    }

}