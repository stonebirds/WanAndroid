package com.stone.wanandroid.contract

import com.stone.common.base.IBaseView
import com.stone.common.base.IPresenter
import com.stone.wanandroid.bean.ArticleBean

interface ProjectContract {
    interface View : IBaseView {
        fun getProjectListSuccess(bean: ArticleBean)

        fun getProjectListFailed(errCode: Int, message: String)
    }

    interface Presenter : IPresenter<View> {
        fun getProjectList(isRefresh: Boolean, page: Int)
    }
}
