package com.victor.myeyepetizer.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * @author victor
 * @date 3/1/18
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        initView()
    }

    protected abstract fun getLayout(): Int
    protected abstract fun initView()

}