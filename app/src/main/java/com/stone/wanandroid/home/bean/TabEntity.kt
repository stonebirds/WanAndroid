package com.stone.wanandroid.home.bean

import com.flyco.tablayout.listener.CustomTabEntity

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-07-02
 */
class TabEntity(var title: String, private var selectedIcon: Int, private var unSelectedIcon: Int):CustomTabEntity {
    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabTitle(): String {
        return title
    }
}