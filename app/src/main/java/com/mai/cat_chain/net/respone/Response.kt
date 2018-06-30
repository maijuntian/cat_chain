package com.mai.cat_chain.net.respone

/**
 * Created by maijuntian on 2018/5/19.
 * 返回参数封装
 */
class Response<T> {

    var data: T? = null

    var status: Int = 0


    constructor(data: T?, status: Int) {
        this.data = data
        this.status = status
    }

    override fun toString(): String {
        return "Response(data=$data, status=$status)"
    }

}