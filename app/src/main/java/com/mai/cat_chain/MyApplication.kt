package com.mai.cat_chain

import android.app.Application
import com.mai.cat_chain.javabean.RecordList
import com.mai.cat_chain.javabean.User
import com.mai.cat_chain.net.NetWorkManager
import com.mai.cat_chain.utils.Key
import com.mai.cat_chain.utils.SharedPreferencesUtils

/**
 * Created by maijuntian on 2018/6/13.
 */
class MyApplication : Application() {
    companion object {
        var instance: MyApplication? = null

    }


    var hRecordList: RecordList? = null
    var mRecordList: RecordList? = null

    var beginTime: String? = null
    var endTime: String? = null


    var user: User? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        NetWorkManager.getInstance().init()
    }

    fun logout() {
        user = null
        SharedPreferencesUtils.putString(Key.USER_KEY, "")
        SharedPreferencesUtils.putString(Key.PASSWORD, "")
    }

    open fun setRecordData(hRecordList: RecordList, mRecordList: RecordList, beginTime: String, endTime: String) {
        this.beginTime = beginTime
        this.endTime = endTime
        this.hRecordList = hRecordList
        this.mRecordList = mRecordList
    }
}