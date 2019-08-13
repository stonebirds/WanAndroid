package com.stone.wanandroid.activity

import android.view.View
import com.stone.common.base.BaseActivity
import com.stone.wanandroid.R
import kotlinx.android.synthetic.main.layout_common_title.*

class FlavorActivity : BaseActivity(), View.OnClickListener {

    override fun layoutId(): Int {
        return R.layout.activity_flavor
    }

    override fun initView() {
        tv_title.text = "我的收藏"

        iv_back.setOnClickListener(this@FlavorActivity)
    }

    override fun start() {

    }

    override fun initData() {

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> {
                finish()
            }
            else -> {

            }
        }
    }
}
