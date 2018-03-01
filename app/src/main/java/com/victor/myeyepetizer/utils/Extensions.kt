package com.victor.myeyepetizer.utils

import android.content.Context
import android.support.v4.app.Fragment
import android.widget.Toast

/**
 * @author victor
 * @date 3/1/18
 * @email vvictorwan@gmail.com
 * @blog www.victorwan.cn                                            #
 */
fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    if (duration == 0 || duration == 1) {
        Toast.makeText(applicationContext, msg, duration).show()
    } else {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    activity!!.toast(msg, duration)
}