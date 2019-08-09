package com.stone.wanandroid.project.fragment

import com.stone.wanandroid.R
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
import com.stone.wanandroid.home.bean.ArticleBean
import com.stone.wanandroid.home.bean.Data
import com.stone.wanandroid.home.fragment.HomeFragment
import com.stone.wanandroid.project.ProjectAdapter
import com.stone.wanandroid.project.contract.ProjectContract
import com.stone.wanandroid.project.presenter.ProjectPresenter
import com.stone.wanandroid.util.ActivityRouter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_project.*
import kotlinx.android.synthetic.main.layout_common_title.*

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-07-03
 */
class ProjectFragment : BaseFragment(), ProjectContract.View, OnRefreshListener, OnLoadMoreListener,
    View.OnClickListener {

    private var isGrid: Boolean = true

    private val mPresenter: ProjectPresenter by lazy { ProjectPresenter() }

    private var mProjectAdapter: ProjectAdapter? = null

    private var mTitle: String? = null

    private var pageIndex: Int = 0

    companion object {
        fun getInstance(title: String): ProjectFragment {
            val fragment = ProjectFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_project
    }

    override fun initView() {
        mPresenter.attachView(this)

        iv_back.visibility = View.GONE
        iv_right.visibility = View.VISIBLE
        tv_title.text = "项目"
        val layoutParams = v_status_bar_placeholder_layout_common_title.layoutParams
        layoutParams.height = activity!!.let { StatusBarUtil.getStatusBarHeight(it) }

        iv_right.setOnClickListener(this@ProjectFragment)

        initRefreshLayout()
        initRecyclerView()
    }

    private fun initRefreshLayout() {
        srl_project_fragment.setOnRefreshListener(this@ProjectFragment)
        srl_project_fragment.setOnLoadMoreListener(this@ProjectFragment)
    }

    private fun initRecyclerView() {
        changeLayoutManager(false)

        mProjectAdapter?.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val item: Data = adapter.getItem(position) as Data
            val link = item.link
            context?.let { ActivityRouter.startCommonWebView(it, link, "") }
        }
    }

    override fun onClick(v: View) {
        changeLayoutManager(isGrid)
    }

    private fun changeLayoutManager(isGrid: Boolean) {
        if (isGrid) {
            iv_right.setImageResource(R.drawable.icon_list)
            mProjectAdapter = ProjectAdapter(R.layout.item_project_grid)
            rv_project_fragment.layoutManager = GridLayoutManager(context, 2)
        } else {
            iv_right.setImageResource(R.drawable.icon_grid)
            mProjectAdapter = ProjectAdapter(R.layout.item_project_list)
            rv_project_fragment.layoutManager = LinearLayoutManager(context)
        }

        rv_project_fragment.adapter = mProjectAdapter

        srl_project_fragment.autoRefresh()

        this.isGrid = !isGrid
    }


    override fun lazyLoad() {
        mPresenter.getProjectList(false, pageIndex)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pageIndex = 0
        srl_project_fragment.resetNoMoreData()
        mPresenter.getProjectList(true, pageIndex)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getProjectList(false, pageIndex)
    }

    override fun getProjectListSuccess(bean: ArticleBean) {
        srl_project_fragment.finishRefresh()

        val datas = bean.datas

        var curPage = bean.curPage
        var totalPage = bean.total

        if (curPage != totalPage) {
            if (pageIndex == 0) {
                mProjectAdapter?.setNewData(datas)
            } else {
                mProjectAdapter?.addData(datas)
            }
            srl_project_fragment.finishLoadMore()
            pageIndex++
        } else {
            mProjectAdapter?.setNewData(datas)
            srl_project_fragment.finishLoadMoreWithNoMoreData()
        }
    }

    override fun getProjectListFailed(errCode: Int, message: String) {
        srl_project_fragment.finishRefresh()
        ToastUtils.showShort(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}