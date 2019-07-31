package com.stone.wanandroid.net

import com.stone.net.bean.BaseResult
import com.stone.wanandroid.home.bean.HomeBannerBean
import io.reactivex.Observable
import retrofit2.http.GET


interface ApiService {
    @GET("banner/json")
    fun getHomeBanner() : Observable<BaseResult<List<HomeBannerBean>>>
}