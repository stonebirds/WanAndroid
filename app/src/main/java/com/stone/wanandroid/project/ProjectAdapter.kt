package com.stone.wanandroid.project

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.stone.wanandroid.R
import com.stone.wanandroid.home.bean.Data

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-09
 */
class ProjectAdapter(layoutId: Int) : BaseQuickAdapter<Data, BaseViewHolder>(layoutId) {
    override fun convert(helper: BaseViewHolder, item: Data) {
        helper.setText(R.id.tv_title_project_list_item, item.title)
        helper.setText(R.id.tv_intro_project_list_item, item.desc)
        helper.setText(
            R.id.tv_author_project_list_item,
            mContext.resources.getString(R.string.article_author, item.author)
        )
        helper.setImageResource(
            R.id.iv_flavor_pic_project_list_item,
            if (item.collect) R.drawable.icon_flavor else R.drawable.icon_unflavor
        )
        helper.setText(R.id.tv_time_project_list_item, item.niceDate)

        var ivPic: ImageView = helper.getView<ImageView>(R.id.iv_pic_project_list_item)

        Glide.with(mContext)
            .load(item.envelopePic)
            .placeholder(R.drawable.bg_mine)
            .error(R.drawable.bg_mine)
            .into(ivPic)
    }
}