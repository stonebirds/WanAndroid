package com.stone.wanandroid.activity

import android.view.View
import com.blankj.utilcode.util.CacheDiskStaticUtils
import com.blankj.utilcode.util.CacheDiskUtils
import com.blankj.utilcode.util.ConvertUtils
import com.stone.common.base.BaseActivity
import com.stone.wanandroid.R
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.layout_common_title.*

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-13
 */
class SettingActivity : BaseActivity(), View.OnClickListener {

    override fun layoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initView() {
        tv_title.text = "设置"
        iv_back.setOnClickListener(this@SettingActivity)

        tv_cache_setting_activity.text = ConvertUtils.byte2FitMemorySize(CacheDiskStaticUtils.getCacheSize())
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

            R.id.btn_clear_setting_activity -> {
                clearAppCache()
            }

            R.id.btn_logout_setting_activity -> {
                logout()
            }

            else -> {

            }
        }
    }

    /**
     * 清除缓存
     */
    private fun clearAppCache() {
        CacheDiskStaticUtils.clear()
        tv_cache_setting_activity.text = "0M"
    }

    /**
     * 退出登录
     */
    private fun logout() {

    }
}