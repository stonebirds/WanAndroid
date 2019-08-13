package com.stone.wanandroid.category.bean

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-09
 */
data class CategoryBean(
    var children: List<CategoryBean>,
    var courseId: Int,
    var id: String,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int,
    var isSelected: Boolean = false
)