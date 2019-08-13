package com.stone.wanandroid.fragment

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.stone.common.base.BaseFragment
import com.stone.common.util.StatusBarUtil
import com.stone.wanandroid.R
import com.stone.wanandroid.adapter.CategoryAdapter
import com.stone.wanandroid.adapter.CategoryAdapterTwo
import com.stone.wanandroid.adapter.CategoryArticleAdapter
import com.stone.wanandroid.bean.CategoryArticleBean
import com.stone.wanandroid.bean.CategoryBean
import com.stone.wanandroid.contract.CategoryContract
import com.stone.wanandroid.home.presenter.CategoryPresenter
import com.stone.wanandroid.util.ActivityRouter
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.layout_common_title.*

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-07-03
 */
class CategoryFragment : BaseFragment(), CategoryContract.View, BaseQuickAdapter.OnItemClickListener,
    OnRefreshListener, OnLoadMoreListener {

    private val mPresenter by lazy { CategoryPresenter() }
    private val mCategoryAdapter by lazy { CategoryAdapter(R.layout.item_category) }
    private val mCategoryAdapterTwo by lazy { CategoryAdapterTwo(R.layout.item_category) }
    private val mCategoryArticleAdapter by lazy { CategoryArticleAdapter(R.layout.item_home_articl) }

    private var mTitle: String? = null
    private var cid: String? = null
    private var pageIndex: Int = 0

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
        mPresenter.attachView(this)

        iv_back.visibility = View.GONE
        tv_title.text = "体系"
        val layoutParams = v_status_bar_placeholder_layout_common_title.layoutParams
        layoutParams.height = activity!!.let { StatusBarUtil.getStatusBarHeight(it) }

        initRefreshLayout()

        initCategoryRecyclerView()
    }

    private fun initRefreshLayout() {
        srl_category_fragment.setOnRefreshListener(this@CategoryFragment)
        srl_category_fragment.setOnLoadMoreListener(this@CategoryFragment)
    }

    private fun initCategoryRecyclerView() {
        rv_article_list_category_fragment.adapter = mCategoryArticleAdapter

        rv_category_one.adapter = mCategoryAdapter
        rv_category_two.adapter = mCategoryAdapterTwo

        mCategoryAdapter.onItemClickListener = this@CategoryFragment
        mCategoryAdapterTwo.onItemClickListener = this@CategoryFragment
        mCategoryArticleAdapter.onItemClickListener = this@CategoryFragment
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        when (adapter) {
            // 点击一级分类数据
            is CategoryAdapter -> {
                // 一级分类数据
                var mCategoryDataListOne: List<CategoryBean> = adapter.data

                mCategoryDataListOne.forEach { it.isSelected = false }
                mCategoryDataListOne[position].isSelected = true
                mCategoryAdapter.notifyDataSetChanged()

                // 二级分类数据
                val list: List<CategoryBean> = mCategoryDataListOne[position].children
                list.forEach { it.isSelected = false }
                list[0].isSelected = true
                mCategoryAdapterTwo.setNewData(list)

                cid = list[0].id
                getArticleList(cid!!, pageIndex)
            }
            // 二级分类点击事件
            is CategoryAdapterTwo -> {
                var mCategoryDataListTwo: List<CategoryBean> = adapter.data
                mCategoryDataListTwo.forEach { it.isSelected = false }
                mCategoryDataListTwo[position].isSelected = true
                mCategoryAdapterTwo.notifyDataSetChanged()

                this.cid = mCategoryDataListTwo[0].id

                getArticleList(cid!!, pageIndex)
            }
            // 文章列表点击事件
            is CategoryArticleAdapter -> {
                val data = adapter.data[position]
                context?.let { ActivityRouter.startCommonWebView(it, data.link, "") }
            }
            else -> {

            }
        }
    }

    override fun lazyLoad() {
        showProgressDialog()
        mPresenter.getCategoryList()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageIndex = 0
        srl_category_fragment.resetNoMoreData()
        getArticleList(cid!!, pageIndex)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pageIndex++
        getArticleList(cid!!, pageIndex)
    }


    private fun getArticleList(cid: String, pageIndex: Int) {
        mPresenter.getCategoryArticleList(pageIndex, cid)
    }

    override fun getCategoryListSuccess(date: List<CategoryBean>) {
        hideProgressDialog()
        LogUtils.e("getCategoryListSuccess---->${date[0].name}------>${date[0].children[0].name}")

        val first = date.first()
        first.isSelected = true

        mCategoryAdapter.setNewData(date)

        val firstTwo = date[0].children.first()
        firstTwo.isSelected = true

        mCategoryAdapterTwo.setNewData(date[0].children)

        cid = firstTwo.id
        getArticleList(cid!!, pageIndex)
    }

    override fun getCategoryListFailed(errCode: Int, message: String) {
        hideProgressDialog()
        ToastUtils.showShort(message)
    }

    override fun getCategoryArticleListSuccess(date: CategoryArticleBean) {
        srl_category_fragment.finishRefresh()

        if (date.curPage != date.pageCount) {
            if (date.curPage == 1) {
                mCategoryArticleAdapter.setNewData(date.datas)
            } else {
                mCategoryArticleAdapter.addData(date.datas)
            }

            srl_category_fragment.finishLoadMore()
        } else {
            mCategoryArticleAdapter.addData(date.datas)
            srl_category_fragment.finishLoadMoreWithNoMoreData()
        }
    }

    override fun getCategoryArticleListFailed(errCode: Int, message: String) {
        ToastUtils.showShort(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}