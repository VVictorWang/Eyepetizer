package com.victor.myeyepetizer.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import com.victor.myeyepetizer.R
import com.victor.myeyepetizer.bean.Item
import com.victor.myeyepetizer.ui.adapter.ItemListAdapter
import com.victor.myeyepetizer.ui.base.BaseFragment
import com.victor.myeyepetizer.ui.contract.HomeContract
import com.victor.myeyepetizer.ui.presenter.HomePresenter
import com.victor.myeyepetizer.utils.toast
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment(), HomeContract.View {
    override fun callFailure(message: String) {
        toast(message)
    }

    private var itemListAdapter: ItemListAdapter? = null
    override fun setPresenter(presenter: HomeContract.Presenter) {
        mPresenter = presenter
    }

    override fun setItemData(itemLists: ArrayList<Item>) {
        itemListAdapter?.setData(itemLists)
    }

    private lateinit var mPresenter: HomeContract.Presenter

    override fun getLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        mPresenter = HomePresenter(this)
        lists.layoutManager = LinearLayoutManager(context)
        itemListAdapter = ItemListAdapter(ArrayList())
        lists.adapter = itemListAdapter
        mPresenter.start()
    }


}// Required empty public constructor
