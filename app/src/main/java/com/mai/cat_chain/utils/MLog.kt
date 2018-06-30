package com.mai.cat_chain.utils

import android.util.Log

/**
 * Created by maijuntian on 2018/6/14.
 */

object MLog {

    private var isShow = true


    @JvmStatic
    fun log(msg: String) {
        if (isShow)
            Log.e("mai", msg)
    }

    @JvmStatic
    fun setShow(isShow: Boolean) {
        this.isShow = isShow
    }
}