package com.victor.myeyepetizer.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * @author victor
 * @date 3/1/18
 * @email vvictorwan@gmail.com
 * @blog www.victorwan.cn                                            #
 */
class FragmentPageAdapter(val fragmentManager: FragmentManager, val fragmnets: ArrayList<Fragment>, val titles: ArrayList<String>? = null) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return fragmnets[position]
    }

    override fun getCount(): Int {
        return fragmnets.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (titles == null) {
            return super.getPageTitle(position)
        } else {
            return titles[position]
        }
    }

}
