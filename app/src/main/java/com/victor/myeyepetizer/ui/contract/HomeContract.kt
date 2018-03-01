package com.victor.myeyepetizer.ui.contract

import com.victor.myeyepetizer.bean.Item
import com.victor.myeyepetizer.ui.base.BasePresenter
import com.victor.myeyepetizer.ui.base.BaseView

/**
 * @author victor
 * @date 3/1/18
 * @email vvictorwan@gmail.com
 * @blog www.victorwan.cn                                            #
 */

interface HomeContract {
    interface View : BaseView<Presenter> {
        fun setItemData(itemLists: ArrayList<Item>)
        fun callFailure(message: String)
    }

    interface Presenter : BasePresenter {

    }
}