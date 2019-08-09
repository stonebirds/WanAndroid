package com.stone.wanandroid.project.contract

import com.stone.common.base.IBaseView
import com.stone.common.base.IPresenter
import com.stone.wanandroid.home.bean.HomeBannerBean
import com.stone.wanandroid.home.bean.ArticleBean

interface ProjectContract {
    interface View : IBaseView {
        fun getProjectListSuccess(bean: ArticleBean)

        fun getProjectListFailed(errCode: Int, message: String)
    }

    interface Presenter : IPresenter<View> {
        fun getProjectList(isRefresh: Boolean, page: Int)
    }
}
