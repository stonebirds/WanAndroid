package com.stone.wanandroid.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.stone.common.base.BaseFragment
import com.stone.common.util.StatusBarUtil
import com.stone.wanandroid.R
import com.stone.wanandroid.bean.LoginBean
import com.stone.wanandroid.manager.UserInfoManager
import com.stone.wanandroid.util.ActivityRouter
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.layout_common_title.*

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-07-03
 */
class MineFragment : BaseFragment(), View.OnClickListener {
    private var mTitle: String? = null
    private var nickname: String? = null

    companion object {
        fun getInstance(title: String): MineFragment {
            val fragment = MineFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView() {

        iv_back.visibility = View.GONE
        tv_title.text = "我的"

        val layoutParams = v_status_bar_placeholder_layout_common_title.layoutParams
        layoutParams.height = activity!!.let { StatusBarUtil.getStatusBarHeight(it) }

        ll_user_info_mine_fragment.visibility = if (UserInfoManager.isLogin()) View.VISIBLE else View.GONE
        tv_user_info_mine_fragment.visibility = if (!UserInfoManager.isLogin()) View.VISIBLE else View.GONE

        initUserInfo()

        initClickListener()
    }

    private fun initClickListener() {
        btn_login.setOnClickListener(this@MineFragment)
        btn_flavor_mine_fragment.setOnClickListener(this@MineFragment)
        btn_about_mine_fragment.setOnClickListener(this@MineFragment)
        btn_setting_mine_fragment.setOnClickListener(this@MineFragment)
    }

    private fun initUserInfo() {
        var userInfo: LoginBean? = UserInfoManager.getUserInfo()

        nickname = userInfo?.nickname

        if (!TextUtils.isEmpty(nickname)) {
            tv_username_mine_fragment.text = nickname
        }

        context?.let {
            Glide.with(it)
                .load(userInfo?.icon)
                .placeholder(R.drawable.bg_mine)
                .error(R.drawable.bg_mine)
                .into(iv_head_mine_fragment)

        }
    }

    override fun lazyLoad() {
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login -> {
                if (!UserInfoManager.isLogin()) {
                    context?.let { ActivityRouter.startLoginActivity(it) }
                } else {
                    ToastUtils.showShort("我是$nickname")
                }
            }
            R.id.btn_flavor_mine_fragment -> {
                context?.let { ActivityRouter.startFlavorActivity(it) }
            }
            R.id.btn_about_mine_fragment -> {
                context?.let { ActivityRouter.startCommonWebView(it, "https://github.com/stonebirds", "") }
            }
            R.id.btn_setting_mine_fragment -> {
                context?.let { ActivityRouter.startSettingActivity(it) }
            }
            else -> {
            }
        }
    }

}