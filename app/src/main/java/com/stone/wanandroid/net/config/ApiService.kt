package com.stone.wanandroid.net.config

import com.stone.wanandroid.net.bean.BaseResult
import com.stone.wanandroid.home.bean.HomeBannerBean
import com.stone.wanandroid.home.bean.HomeBean
import com.stone.wanandroid.login.bean.LoginBean
import io.reactivex.Observable
import retrofit2.http.*


interface ApiService {
    @FormUrlEncoded
    @POST("user/login")
    fun doLogin(@Field("username") username: String, @Field("password") password: String): Observable<BaseResult<LoginBean>>

    @FormUrlEncoded
    @POST("user/register")
    fun doRegister(@FieldMap params: HashMap<String, String>): Observable<BaseResult<LoginBean>>

    @GET("banner/json")
    fun getHomeBanner(): Observable<BaseResult<List<HomeBannerBean>>>

    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Observable<BaseResult<HomeBean>>
}