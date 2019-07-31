package com.stone.wanandroid.category.fragment

import com.stone.wanandroid.R
import android.os.Bundle
import com.stone.common.base.BaseFragment

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-07-03
 */
class CategoryFragment : BaseFragment() {
    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): CategoryFragment {
            val fragment = CategoryFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }
    override fun getLayoutId(): Int {
        return R.layout.fragment_category
    }

    override fun initView() {
    }

    override fun lazyLoad() {
    }
}