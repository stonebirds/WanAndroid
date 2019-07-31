package com.stone.net.manager

import com.stone.net.BuildConfig
import com.stone.net.config.NetUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object RetrofitHelper {

    private const val CONNECT_TIME_OUT = 30
    private const val WRITE_TIME_OUT = 30
    private const val READ_TIME_OUT = 30

    private fun initOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.connectTimeout(CONNECT_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        } else {
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE))
        }
        return builder.build()
    }


    fun createRetrofit(): Retrofit {
        val builder = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(NetUrl.BASE_URL)
            .client(initOkHttpClient())
        return builder.build()
    }

    fun createRetrofit(url: String): Retrofit {
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

