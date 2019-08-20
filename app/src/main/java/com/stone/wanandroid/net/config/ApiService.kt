package com.stone.wanandroid.net.config

import com.stone.wanandroid.bean.CategoryArticleBean
import com.stone.wanandroid.bean.CategoryBean
import com.stone.wanandroid.net.bean.BaseResult
import com.stone.wanandroid.bean.HomeBannerBean
import com.stone.wanandroid.bean.ArticleBean
import com.stone.wanandroid.bean.LoginBean
import com.stone.wanandroid.bean.LogoutBean
import io.reactivex.Observable
import retrofit2.http.*


interface ApiService {
    //登录
    @FormUrlEncoded
    @POST("user/login")
    fun doLogin(@Field("username") username: String, @Field("password") password: String): Observable<BaseResult<LoginBean>>

    //注册
    @FormUrlEncoded
    @POST("user/register")
    fun doRegister(@FieldMap params: HashMap<String, String>): Observable<BaseResult<LoginBean>>

    //退出登录
    @GET("user/logout/json")
    fun doLogout(): Observable<BaseResult<LogoutBean>>

    //首页Banner
    @GET("banner/json")
    fun getHomeBanner(): Observable<BaseResult<List<HomeBannerBean>>>

    //文章
    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Observable<BaseResult<ArticleBean>>

    //项目
    @GET("article/listproject/{page}/json")
    fun getProjectList(@Path("page") page: Int): Observable<BaseResult<ArticleBean>>

    //体系
    @GET("tree/json")
    fun getCategoryList(): Observable<BaseResult<List<CategoryBean>>>

    //体系下的文章
    @GET("article/list/{page}/json")
    fun getCategoryArticleList(@Path("page") page: Int, @Query("cid") cid: String): Observable<BaseResult<CategoryArticleBean>>

}