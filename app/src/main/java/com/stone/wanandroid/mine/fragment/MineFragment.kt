package com.stone.wanandroid.mine.fragment

import com.stone.wanandroid.R
import android.os.Bundle
import android.view.View
import com.stone.common.base.BaseFragment
import com.stone.wanandroid.util.ActivityRouter
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-07-03
 */
class MineFragment : BaseFragment(), View.OnClickListener {
    private var mTitle: String? = null

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
        btn_login.setOnClickListener(this@MineFragment)
    }

    override fun lazyLoad() {
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login -> {
                context?.let { ActivityRouter.startLoginActivity(it) }
            }
            else -> {
            }
        }
    }

}