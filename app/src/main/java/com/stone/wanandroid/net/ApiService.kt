package com.stone.wanandroid.net

import com.stone.net.bean.BaseResult
import com.stone.wanandroid.home.bean.HomeBannerBean
import com.stone.wanandroid.home.bean.HomeBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
    @GET("banner/json")
    fun getHomeBanner(): Observable<BaseResult<List<HomeBannerBean>>>

    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Observable<BaseResult<HomeBean>>
}