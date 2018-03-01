package com.victor.myeyepetizer.api

import com.victor.myeyepetizer.bean.HomeBean
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

/**
 * @author victor
 * @date 3/1/18
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */
interface EyeApiService {

    @GET("tabs/selected?udid=11111&vc=168")
    fun getHomeData(): Observable<Response<HomeBean>>
}