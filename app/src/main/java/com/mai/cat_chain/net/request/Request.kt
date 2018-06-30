package com.mai.cat_chain.net.request

import com.mai.cat_chain.javabean.*
import io.reactivex.Observable
import com.mai.cat_chain.net.respone.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * Created by maijuntian on 2018/6/13.
 */
interface Request {
    companion object {
//        var HOST: String = "http://10.0.20.140:7009/"
        var HOST: String = "http://mstar.healthmall.cn/app/"
    }

    @POST("session/openSession?device=android")
    fun openSession(): Observable<Response<String>>


    @POST
    fun userLogin(@Url url: String): Observable<Response<String>>

    @POST
    fun collectedMeowDrill(@Url url: String): Observable<Response<Any>>

    @GET
    fun queryUserNotCollectedM(@Url url: String): Observable<Response<List<NotCollectedM>>>

    @GET
    fun rank(@Url url: String): Observable<Response<Rank>>

    @GET
    fun queryUserMHInfo(@Url url: String): Observable<Response<User>>

    @GET
    fun getHvalueByTime(@Url url: String): Observable<Response<RecordList>>

    @GET
    fun getMDrilldetailed(@Url url: String): Observable<Response<RecordList>>

    @GET
    fun getAllCarousel(@Url url: String): Observable<Response<MutableList<String>>>

    @GET
    fun getAllBaseTask(@Url url: String): Observable<Response<MutableList<BaseTask>>>

    @GET
    fun getTodayTask(@Url url: String): Observable<Response<MutableList<TodayTask>>>
}