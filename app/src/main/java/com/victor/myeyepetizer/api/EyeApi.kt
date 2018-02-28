package com.victor.myeyepetizer.api

import com.victor.myeyepetizer.bean.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author victor
 * @date 3/1/18
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */
class EyeApi(client: OkHttpClient) {
    private val service: EyeApiService

    companion object {
        var instance: EyeApi? = null
        fun getInstance(client: OkHttpClient): EyeApi {
            if (instance == null) {
                instance = EyeApi(client)
            }
            return instance as EyeApi
        }
    }

    init {
        val retrofit = Retrofit.Builder().baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
        service = retrofit.create(EyeApiService::class.java)
    }
}