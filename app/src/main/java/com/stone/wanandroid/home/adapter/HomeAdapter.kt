package com.stone.wanandroid.home.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.stone.wanandroid.R
import com.stone.wanandroid.home.bean.Data

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-07-26
 */
class HomeAdapter(layoutResId: Int) : BaseQuickAdapter<Data, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder?, item: Data?) {
        helper?.setText(R.id.tv_title_home_article_item, item?.title)
    }
}