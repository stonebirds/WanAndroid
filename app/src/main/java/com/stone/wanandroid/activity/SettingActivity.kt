package com.stone.wanandroid.activity

import android.view.View
import com.blankj.utilcode.util.CacheDiskStaticUtils
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ToastUtils
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.stone.common.base.BaseActivity
import com.stone.wanandroid.MainActivity
import com.stone.wanandroid.R
import com.stone.wanandroid.bean.event.LogoutEvent
import com.stone.wanandroid.contract.SettingContract
import com.stone.wanandroid.manager.UserInfoManager
import com.stone.wanandroid.presenter.SettingPresenter
import com.stone.wanandroid.util.ActivityRouter
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.layout_common_title.*
import org.greenrobot.eventbus.EventBus

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-13
 */
class SettingActivity : BaseActivity(), View.OnClickListener, SettingContract.View {
    private val mPresenter by lazy { SettingPresenter() }

    override fun layoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initView() {
        mPresenter.attachView(this)

        tv_title.text = "设置"
        tv_cache_setting_activity.text = ConvertUtils.byte2FitMemorySize(CacheDiskStaticUtils.getCacheSize())

        initClickListener()
    }

    private fun initClickListener() {
        iv_back.setOnClickListener(this@SettingActivity)
        btn_clear_setting_activity.setOnClickListener(this@SettingActivity)
        btn_logout_setting_activity.setOnClickListener(this@SettingActivity)
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
        showProgressDialog()
        mPresenter.doLogout()
    }

    override fun doLogoutSuccess() {
        hideProgressDialog()

        UserInfoManager.deleteUserInfo()

        val cookiePersistor = SharedPrefsCookiePersistor(this)
        cookiePersistor.clear()

        ActivityRouter.startMainActivity(this,MainActivity.FRAGMENT_HOME)

        finish()
    }

    override fun doLogoutFailed(errorCode: Int, message: String) {
        ToastUtils.showShort(message)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}