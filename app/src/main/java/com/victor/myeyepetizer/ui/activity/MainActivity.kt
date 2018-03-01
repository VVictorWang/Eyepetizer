package com.victor.myeyepetizer.ui.activity

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import com.victor.myeyepetizer.R
import com.victor.myeyepetizer.ui.adapter.FragmentPageAdapter
import com.victor.myeyepetizer.ui.base.BaseActivity
import com.victor.myeyepetizer.ui.fragment.CatogoryFragment
import com.victor.myeyepetizer.ui.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private var home: TabLayout.Tab? = null
    private var catogory: TabLayout.Tab? = null
    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        val fragments = ArrayList<Fragment>()
        fragments.add(HomeFragment())
        fragments.add(CatogoryFragment())
        viewPager.adapter = FragmentPageAdapter(supportFragmentManager, fragments, arrayListOf("首页", "分类"))
        viewPager.currentItem = 0
        tabs.setupWithViewPager(viewPager)
        setTab()
        initEvent()
    }

    fun setTab() {
        home = tabs.getTabAt(0)
        catogory = tabs.getTabAt(1)
        home?.icon = resources.getDrawable(R.mipmap.home_selected)
        catogory?.icon = resources.getDrawable(R.mipmap.ic_tab_strip_icon_category)
    }

    fun initEvent() {
        tabs.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                if (tab == tabs.getTabAt(1)) {
                    catogory?.icon = resources.getDrawable(R.mipmap.ic_tab_strip_icon_category)
                } else if (tab == tabs.getTabAt(0)) {
                    home?.icon = resources.getDrawable(R.mipmap.home_normal)
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab == tabs.getTabAt(0)) {
                    home?.icon = resources.getDrawable(R.mipmap.home_selected)
                    catogory?.icon = resources.getDrawable(R.mipmap.ic_tab_strip_icon_category)
                } else if (tab == tabs.getTabAt(1)) {
                    home?.icon = resources.getDrawable(R.mipmap.home_normal)
                    catogory?.icon = resources.getDrawable(R.mipmap.ic_tab_strip_icon_category_selected)
                }
            }
        })
    }


}
