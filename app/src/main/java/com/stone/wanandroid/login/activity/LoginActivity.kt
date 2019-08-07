package com.stone.wanandroid.login.activity

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ToastUtils
import com.stone.common.base.BaseActivity
import com.stone.wanandroid.R
import com.stone.wanandroid.login.bean.LoginBean
import com.stone.wanandroid.login.contract.LoginContract
import com.stone.wanandroid.login.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_common_title.*

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-06
 */
class LoginActivity : BaseActivity(), LoginContract.View, TextWatcher, View.OnClickListener {
    private val mPresenter: LoginPresenter by lazy { LoginPresenter() }

    private var username: String? = null
    private var password: String? = null

    override fun layoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        mPresenter.attachView(this)

        v_line.visibility = View.GONE

        iv_back.setOnClickListener(this@LoginActivity)
        btn_login_activity.setOnClickListener(this@LoginActivity)

        initEditText()
    }

    private fun initEditText() {
        et_username_login_activity.addTextChangedListener(this@LoginActivity)
        et_password_login_activity.addTextChangedListener(this@LoginActivity)
    }

    override fun afterTextChanged(s: Editable) {

    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        username = et_username_login_activity.text.toString()
        password = et_password_login_activity.text.toString()

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            btn_login_activity.setBackgroundColor(ColorUtils.getColor(R.color.black_333333))
            btn_login_activity.isEnabled = true
        } else {
            btn_login_activity.setBackgroundColor(ColorUtils.getColor(R.color.gray_cdcdcd))
            btn_login_activity.isEnabled = false
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> {
                finish()
            }

            R.id.btn_login_activity -> {
                doLogin()
            }
        }
    }

    private fun doLogin() {
        mPresenter.login(username!!, password!!)
    }

    override fun initData() {

    }

    override fun start() {
    }

    override fun loginSuccess(bean: LoginBean) {
        
    }

    override fun loginFailed(errorCode: Int, errorMsg: String) {
        ToastUtils.showShort(errorMsg)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}