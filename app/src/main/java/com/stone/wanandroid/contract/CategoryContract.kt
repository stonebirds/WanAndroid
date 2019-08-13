package com.stone.wanandroid.contract

import com.stone.common.base.IBaseView
import com.stone.common.base.IPresenter
import com.stone.wanandroid.bean.CategoryArticleBean
import com.stone.wanandroid.bean.CategoryBean

interface CategoryContract {
    interface View : IBaseView {
        fun getCategoryListSuccess(date: List<CategoryBean>)

        fun getCategoryListFailed(errCode: Int, message: String)

        fun getCategoryArticleListSuccess(date: CategoryArticleBean)

        fun getCategoryArticleListFailed(errCode: Int, message: String)
    }

    interface Presenter : IPresenter<View> {
        fun getCategoryList()

        fun getCategoryArticleList(page: Int, cid: String)
    }
}
