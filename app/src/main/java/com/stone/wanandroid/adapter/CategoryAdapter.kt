package com.stone.wanandroid.adapter

import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.coorchice.library.SuperTextView
import com.stone.wanandroid.R
import com.stone.wanandroid.bean.CategoryBean

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-12
 */
class CategoryAdapter(layoutResId: Int) : BaseQuickAdapter<CategoryBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: CategoryBean) {
        var tv: SuperTextView = helper.getView(R.id.tv_name_category_item) as SuperTextView
        tv.text = item.name

        if (item.isSelected) {
            tv.solid = ColorUtils.getColor(R.color.black_333333)
            tv.strokeColor = ColorUtils.getColor(R.color.black_333333)
            tv.strokeWidth = ConvertUtils.dp2px(1f).toFloat()
            tv.setTextColor(ColorUtils.getColor(R.color.white))
        } else {
            tv.solid = ColorUtils.getColor(R.color.white)
            tv.strokeColor = ColorUtils.getColor(R.color.gray_999999)
            tv.strokeWidth = ConvertUtils.dp2px(1f).toFloat()
            tv.setTextColor(ColorUtils.getColor(R.color.gray_999999))
        }
    }
}