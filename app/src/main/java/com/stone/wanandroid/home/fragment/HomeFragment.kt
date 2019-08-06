package com.stone.wanandroid.home.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.stone.common.base.BaseFragment
import com.stone.wanandroid.R
import com.stone.wanandroid.home.adapter.HomeAdapter
import com.stone.wanandroid.home.bean.Data
import com.stone.wanandroid.home.bean.HomeBannerBean
import com.stone.wanandroid.home.bean.HomeBean
import com.stone.wanandroid.home.contract.HomeContract
import com.stone.wanandroid.home.presenter.HomePresenter
import com.stone.wanandroid.util.ActivityRouter
import com.stone.wanandroid.util.BannerImageLoader
import com.youth.banner.Banner
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_home_banner.view.*
import android.support.v7.widget.RecyclerView.OnScrollListener as OnScrollListener1

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-07-02
 */
class HomeFragment : BaseFragment(), HomeContract.View{
    private var mTitle: String? = null

    private val mPresenter = HomePresenter()

    private val mHomeAdapter: HomeAdapter? = HomeAdapter(R.layout.item_home_articl)

    private var mBannerView: Banner? = null

    private var pageIndex = 0

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
        initRefreshLayout()
        initRecyclerView()
        initBannerView()
    }

    private fun initRefreshLayout() {
        srl_home_fragment.setOnRefreshListener { onRefresh() }
        srl_home_fragment.setOnLoadMoreListener { onLoadMore() }
    }

    private fun initRecyclerView() {
        rv_home_fragment.layoutManager = LinearLayoutManager(context)
        rv_home_fragment.adapter = mHomeAdapter

        mHomeAdapter?.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val item: Data = adapter.getItem(position) as Data
            val link = item.link
            context?.let { ActivityRouter.startCommonWebView(it, link, "") }
        }
    }

    private fun initBannerView() {
        val homeBannerView = LayoutInflater.from(context).inflate(R.layout.item_home_banner, null)
        mHomeAdapter?.addHeaderView(homeBannerView)

        mBannerView = homeBannerView.bn_home_banner_item
        mBannerView?.setImageLoader(BannerImageLoader())
        mBannerView?.isAutoPlay(true)
    }

    private fun onRefresh() {
        pageIndex = 0
        LogUtils.d("onRefresh-----------")
        srl_home_fragment.resetNoMoreData()
        mPresenter.getHomeBanner()
        mPresenter.getHomeArticle(true, pageIndex)
    }

    private fun onLoadMore() {
        mPresenter.getHomeArticle(false, pageIndex)
    }

    override fun lazyLoad() {
        mPresenter.getHomeBanner()
        mPresenter.getHomeArticle(false, pageIndex)
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun getHomeBannerSuccess(date: List<HomeBannerBean>) {
        LogUtils.e("getHomeBannerSuccess-----------${date[0].imagePath}")

        var imageList = ArrayList<String>()

        imageList.clear()

        for (homeBannerBean in date) {
            imageList.add(homeBannerBean.imagePath)
        }
        mBannerView?.setImages(imageList)

        mBannerView?.start()
        srl_home_fragment.finishRefresh()
    }

    override fun getHomeBannerFailed(errCode: Int, message: String) {
        LogUtils.d("getHomeBannerFailed-----------$message")
        srl_home_fragment.finishRefresh()
    }

    override fun getHomeArticleSuccess(bean: HomeBean) {
        val datas = bean.datas

        var curPage = bean.curPage
        var totalPage = bean.total

        if (curPage != totalPage) {
            if (pageIndex == 0) {
                mHomeAdapter?.setNewData(datas)
            } else {
                mHomeAdapter?.addData(datas)
            }
            srl_home_fragment.finishLoadMore()
            pageIndex++
        } else {
            mHomeAdapter?.setNewData(datas)
            srl_home_fragment.finishLoadMoreWithNoMoreData()
        }
    }

    override fun getHomeArticleFailed(errCode: Int, message: String) {
        LogUtils.d("getHomeArticleFailed-----------$message")
        srl_home_fragment.finishLoadMore()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}
