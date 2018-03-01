package com.victor.myeyepetizer.repository

import com.victor.myeyepetizer.api.EyeApi
import com.victor.myeyepetizer.bean.HomeBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

/**
 * @author victor
 * @date 3/1/18
 * @email vvictorwan@gmail.com
 * @blog www.victorwan.cn                                            #
 */
class EyeRepository {

    companion object {
        private var instance: EyeRepository? = null
        fun getInstanc(): EyeRepository {
            if (instance == null) {
                instance = EyeRepository()
            }
            return instance as EyeRepository
        }
    }

    fun getHomeData(callBack: RepositoryCallBack<HomeBean>) {
        rxShedulerHelper(EyeApi.service.getHomeData(), callBack)
    }

    fun <T> rxShedulerHelper(value: Observable<Response<T>>, callBack: RepositoryCallBack<T>) {
        value.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(RxObserverCallBack<T>(callBack))
    }
}