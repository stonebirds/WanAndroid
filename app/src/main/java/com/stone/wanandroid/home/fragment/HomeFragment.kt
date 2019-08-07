package com.stone.wanandroid.home.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.stone.common.base.BaseFragment
import com.stone.common.util.StatusBarUtil
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
import kotlinx.android.synthetic.main.layout_common_title.*

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-07-02
 */
class HomeFragment : BaseFragment(), HomeContract.View, OnRefreshListener, OnLoadMoreListener {

    private var mTitle: String? = null

    private val mPresenter by lazy { HomePresenter() }

    private val mHomeAdapter by lazy { HomeAdapter(R.layout.item_home_articl) }

    private var mBannerView: Banner? = null

    private var pageIndex = 0

    private var scrollY = 0

    companion object {
        var isStatusBarDark: Boolean = false

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

        iv_back.visibility = View.GONE
        tv_title.text = "玩安卓"
        val layoutParams = v_status_bar_placeholder_layout_common_title.layoutParams
        layoutParams.height = activity!!.let { StatusBarUtil.getStatusBarHeight(it) }

        initRefreshLayout()
        initRecyclerView()
        initBannerView()
    }

    private fun initRefreshLayout() {
        srl_home_fragment.setOnRefreshListener(this@HomeFragment)
        srl_home_fragment.setOnLoadMoreListener(this@HomeFragment)
    }

    private fun initRecyclerView() {
        rv_home_fragment.layoutManager = LinearLayoutManager(context)
        rv_home_fragment.adapter = mHomeAdapter

        rv_home_fragment.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                LogUtils.d("onScrolled-----------######    $newState")
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val statusBarHeight = activity!!.let { StatusBarUtil.getStatusBarHeight(it) }
                LogUtils.d("onScrolled-----------######    $dy--------------$statusBarHeight")
                scrollY += dy

                if ((1.0 * scrollY / ConvertUtils.dp2px(180f)).toFloat() > 0.5f) {
                    isStatusBarDark = true
                    activity?.let { StatusBarUtil.setStatusBarDarkTheme(it, true) }
                } else {
                    isStatusBarDark = false
                    activity?.let { StatusBarUtil.setStatusBarDarkTheme(it, false) }
                }



                ll_layout_common_title.alpha = (1.0 * scrollY / (ConvertUtils.dp2px(115f) - statusBarHeight)).toFloat()
            }

        })


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


    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageIndex = 0
        LogUtils.d("onRefresh-----------")
        srl_home_fragment.resetNoMoreData()
        mPresenter.getHomeBanner()
        mPresenter.getHomeArticle(true, pageIndex)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getHomeArticle(false, pageIndex)
    }

    override fun lazyLoad() {
        showProgressDialog()
        mPresenter.getHomeBanner()
        mPresenter.getHomeArticle(false, pageIndex)
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
        hideProgressDialog()

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
        hideProgressDialog()
        LogUtils.d("getHomeArticleFailed-----------$message")
        srl_home_fragment.finishLoadMore()
        ToastUtils.showShort(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}
