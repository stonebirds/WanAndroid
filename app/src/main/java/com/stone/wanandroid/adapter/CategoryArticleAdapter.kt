package com.stone.wanandroid.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.stone.wanandroid.R
import com.stone.wanandroid.bean.Data

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-07-26
 */
class CategoryArticleAdapter(layoutResId: Int) : BaseQuickAdapter<Data, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: Data) {
        helper.setText(R.id.tv_title_home_article_item, item.title)
        helper.setText(
            R.id.tv_author_home_article_item,
            mContext.resources.getString(R.string.article_author, item.author)
        )
        helper.setImageResource(
            R.id.iv_flavor_home_article_item,
            if (item.collect) R.drawable.icon_flavor else R.drawable.icon_unflavor
        )
        helper.setText(R.id.tv_time_home_article_item, item.niceDate)
    }
}