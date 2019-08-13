package com.stone.wanandroid.contract

import com.stone.common.base.IBaseView
import com.stone.common.base.IPresenter
import com.stone.wanandroid.bean.HomeBannerBean
import com.stone.wanandroid.bean.ArticleBean

interface HomeContract {
    interface View : IBaseView {
        fun getHomeBannerSuccess(date: List<HomeBannerBean>)

        fun getHomeBannerFailed(errCode: Int, message: String)

        fun getHomeArticleSuccess(bean: ArticleBean)

        fun getHomeArticleFailed(errCode: Int, message: String)
    }

    interface Presenter : IPresenter<View> {
        fun getHomeBanner()

        fun getHomeArticle(isRefresh: Boolean, page: Int)
    }
}
