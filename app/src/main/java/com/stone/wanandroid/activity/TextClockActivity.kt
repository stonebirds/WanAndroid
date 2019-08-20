package com.stone.wanandroid.activity

import com.stone.common.base.BaseActivity
import com.stone.common.util.StatusBarUtil
import com.stone.wanandroid.R
import kotlinx.android.synthetic.main.activity_navigation.*
import java.util.*
import kotlin.concurrent.timer

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-15
 */
class TextClockActivity : BaseActivity() {
    private var mTimer : Timer? = null

    override fun layoutId(): Int {
        return R.layout.activity_navigation
    }

    override fun initData() {

    }

    override fun initView() {
        StatusBarUtil.setRootViewFitsSystemWindows(this, false)
        StatusBarUtil.setStatusBarDarkTheme(this, false)

        startTextClock()
    }

    private fun startTextClock() {
        mTimer = timer(period = 1000){
            runOnUiThread {
                tcv_text_clock_activity.doInvalidate()
            }
        }
    }

    override fun start() {

    }
}