package com.stone.wanandroid.bean

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-13
 */
data class CategoryArticleBean(
    val curPage: Int,
    val datas: List<Data>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)