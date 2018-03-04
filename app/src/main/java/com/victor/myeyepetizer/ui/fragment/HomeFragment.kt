package com.victor.myeyepetizer.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.victor.myeyepetizer.R
import com.victor.myeyepetizer.bean.Item
import com.victor.myeyepetizer.ui.adapter.ItemListAdapter
import com.victor.myeyepetizer.ui.base.BaseFragment
import com.victor.myeyepetizer.ui.contract.HomeContract
import com.victor.myeyepetizer.ui.presenter.HomePresenter
import com.victor.myeyepetizer.utils.toast
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment(), HomeContract.View {
    override fun addItemData(itemLists: ArrayList<Item>) {
        itemListAdapter?.addData(itemLists)
    }

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
        lists.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val childCount = lists.childCount
                    val itemCount = lists.layoutManager.itemCount
                    val firstVisibleItem = (lists.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem + childCount == itemCount) {
                        mPresenter.loadMore()
                    }
                }
            }
        })
        mPresenter.start()
    }


}// Required empty public constructor
