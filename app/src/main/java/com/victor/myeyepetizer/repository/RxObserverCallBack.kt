package com.victor.myeyepetizer.repository

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.Response

/**
 * @author victor
 * @date 3/1/18
 * @email vvictorwan@gmail.com
 * @blog www.victorwan.cn                                            #
 */
class RxObserverCallBack<T>(var callBack: RepositoryCallBack<T>) : Observer<Response<T>?> {
    override fun onSubscribe(d: Disposable) {

    }

    override fun onError(e: Throwable) {
        callBack.callFailure(e.message!!)
    }

    override fun onComplete() {

    }

    override fun onNext(t: Response<T>) {
        if (t.isSuccessful) {
            callBack.callSuccess(t.body()!!)
        } else {
            callBack.callFailure(t.errorBody().toString())
        }
    }
}