package com.victor.myeyepetizer.ui.presenter

import com.victor.myeyepetizer.bean.HomeBean
import com.victor.myeyepetizer.repository.EyeRepository
import com.victor.myeyepetizer.repository.RepositoryCallBack
import com.victor.myeyepetizer.ui.contract.HomeContract

/**
 * @author victor
 * @date 3/1/18
 * @email vvictorwan@gmail.com
 * @blog www.victorwan.cn                                            #
 */
class HomePresenter(val mView: HomeContract.View) : HomeContract.Presenter {
    private var nextPageUrl: String? = null
    override fun loadMore() {
        nextPageUrl?.let {
            EyeRepository.getInstanc().getMoreHomeData(it, object : RepositoryCallBack<HomeBean> {
                override fun callSuccess(data: HomeBean) {
                    nextPageUrl = data.nextPageUrl
                    mView.addItemData(data.itemList)
                }

                override fun callFailure(message: String) {
                    mView.callFailure(message)
                }
            })
        }
    }

    init {
        mView.setPresenter(this)
    }

    override fun start() {
        EyeRepository.getInstanc().getHomeData(object : RepositoryCallBack<HomeBean> {
            override fun callFailure(message: String) {
                mView.callFailure(message)
            }

            override fun callSuccess(data: HomeBean) {
                nextPageUrl = data.nextPageUrl
                mView.setItemData(data.itemList)
            }
        })
    }

}