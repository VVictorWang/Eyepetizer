package com.victor.myeyepetizer.repository

import com.victor.myeyepetizer.api.EyeApi
import com.victor.myeyepetizer.bean.HomeBean
import com.victor.myeyepetizer.bean.Item
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
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
        EyeApi.service.getHomeData().flatMap { homeBean -> EyeApi.service.getMoreHomeData(homeBean.body()?.nextPageUrl!!) }
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Response<HomeBean>?> {
                    override fun onError(e: Throwable) {
                        callBack.callFailure(e.message!!)
                    }

                    override fun onNext(t: Response<HomeBean>) {
                        if (!t.isSuccessful) {
                            callBack.callFailure(t.errorBody().toString())
                        } else {
                            val newItemList = t.body()!!.itemList
                            val items = ArrayList<Item>()
                            newItemList.filter { item -> item.type == "video" }.forEach({ item: Item ->
                                items.add(item)
                            })
                            t.body()!!.itemList = items
                            callBack.callSuccess(t.body()!!)
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onComplete() {
                    }
                })
    }

    fun getCatogory(callBack: RepositoryCallBack<HomeBean>) {

    }

    fun getMoreHomeData(url: String, callBack: RepositoryCallBack<HomeBean>) {
        EyeApi.service.getMoreHomeData(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Response<HomeBean>?> {
                    override fun onNext(t: Response<HomeBean>) {
                        if (!t.isSuccessful) {
                            callBack.callFailure(t.errorBody().toString())
                        } else {
                            val newItemList = t.body()!!.itemList
                            val items = ArrayList<Item>()
                            newItemList.filter { item -> item.type == "video" }.forEach({ item: Item ->
                                items.add(item)
                            })
                            t.body()!!.itemList = items
                            callBack.callSuccess(t.body()!!)
                        }
                    }

                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                        callBack.callFailure(e.message!!)
                    }

                    override fun onSubscribe(d: Disposable) {
                    }
                })
    }

    fun <T> rxShedulerHelper(value: Observable<Response<T>>, callBack: RepositoryCallBack<T>) {
        value.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(RxObserverCallBack<T>(callBack))
    }
}