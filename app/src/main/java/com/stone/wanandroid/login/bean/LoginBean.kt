package com.stone.wanandroid.login.bean

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-06
 */
data class LoginBean(
    val admin: Boolean,
    val chapterTops: List<Any>,
    val collectIds: List<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val token: String,
    val type: Int,
    val username: String
)