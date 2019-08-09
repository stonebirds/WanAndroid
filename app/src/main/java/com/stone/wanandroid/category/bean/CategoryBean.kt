package com.stone.wanandroid.category.bean

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-09
 */
data class CategoryBean(
    val children: List<Children>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)

data class Children(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)