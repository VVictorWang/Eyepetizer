package com.victor.myeyepetizer.repository

/**
 * @author victor
 * @date 3/1/18
 * @email vvictorwan@gmail.com
 * @blog www.victorwan.cn                                            #
 */
interface RepositoryCallBack<T> {

    fun callSuccess(data: T)
    fun callFailure(message: String)
}