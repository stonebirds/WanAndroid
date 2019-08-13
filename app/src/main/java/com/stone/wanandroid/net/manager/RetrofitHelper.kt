package com.stone.wanandroid.net.manager

import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.stone.wanandroid.BuildConfig
import com.stone.wanandroid.WanAndroidApplication
import com.stone.wanandroid.net.config.NetUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {

    private const val CONNECT_TIME_OUT = 30
    private const val WRITE_TIME_OUT = 30
    private const val READ_TIME_OUT = 30


    private fun initOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val setCookieCache = SetCookieCache()
        val sharedPrefsCookiePersistor = SharedPrefsCookiePersistor(WanAndroidApplication.getContext())
        sharedPrefsCookiePersistor.loadAll()
        val cookieJar: ClearableCookieJar = PersistentCookieJar(setCookieCache, sharedPrefsCookiePersistor)

        builder.connectTimeout(CONNECT_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .cookieJar(cookieJar)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        } else {
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE))
        }
        return builder.build()
    }


    private fun createRetrofit(): Retrofit {
        val builder = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(NetUrl.BASE_URL)
            .client(initOkHttpClient())
        return builder.build()
    }

    private fun createRetrofit(url: String): Retrofit {
        val builder = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .client(initOkHttpClient())
        return builder.build()
    }

    fun <T> createService(url: String, clazz: Class<T>): T {
        val retrofit = createRetrofit(url)
        return retrofit.create(clazz)
    }

    fun <T> createService(clazz: Class<T>): T {
        val retrofit = createRetrofit()
        return retrofit.create(clazz)
    }
}

