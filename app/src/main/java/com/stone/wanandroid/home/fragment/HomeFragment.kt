package com.stone.wanandroid.home.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.stone.common.base.BaseFragment
import com.stone.wanandroid.R
import com.stone.wanandroid.home.bean.HomeBannerBean
import com.stone.wanandroid.home.contract.HomeContract
import com.stone.wanandroid.home.presenter.HomePresenter
import kotlinx.android.synthetic.main.fragment_home.*

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-07-02
 */
class HomeFragment : BaseFragment(), HomeContract.View {
   private val mPresenter = HomePresenter()

    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        mPresenter.attachView(this)
        srl_home_fragment.setOnRefreshListener { onRefresh() }
        rv_home_fragment.layoutManager = LinearLayoutManager(context)
    }

    private fun onRefresh() {
        LogUtils.e("onRefresh-----------")
        mPresenter.getHomeBanner()
    }

    override fun lazyLoad() {
        mPresenter.getHomeBanner()
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun getHomeBannerSuccess(date: List<HomeBannerBean>) {
        LogUtils.e("getHomeBannerSuccess-----------${date[0].imagePath}")
        srl_home_fragment.finishRefresh()
    }

    override fun getHomeBannerFailed(errCode: Int, message: String) {
        LogUtils.e("getHomeBannerFailed-----------$message")
        srl_home_fragment.finishRefresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}