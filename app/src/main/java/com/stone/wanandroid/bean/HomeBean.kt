package com.stone.wanandroid.bean


data class ArticleBean(
    val curPage: Int,
    val datas: List<Data>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class Tag(
    val name: String,
    val url: String
)