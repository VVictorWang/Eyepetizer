package com.victor.myeyepetizer.api

import com.victor.myeyepetizer.bean.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author victor
 * @date 3/1/18
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */
object EyeApi {
    private val retrofit: Retrofit
    private val client: OkHttpClient

    init {
        client = OkHttpClient.Builder().connectTimeout(6, TimeUnit.SECONDS)
                .build()
        retrofit = Retrofit.Builder().baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
    }

    val service: EyeApiService by lazy { retrofit.create(EyeApiService::class.java) }
}