package com.stone.wanandroid.util

import android.content.Context
import android.content.Intent
import com.stone.wanandroid.constant.CommonConstant
import com.stone.wanandroid.web.CommonWebViewActivity

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-05
 */
class ActivityRouter {
    companion object {

        /**
         * 启动WebView
         */
        fun startCommonWebView(context: Context,url: String, title: String) {
            var intent = Intent(context,CommonWebViewActivity::class.java)
            intent.putExtra(CommonConstant.COMMON_WEB_URL,url)
            intent.putExtra(CommonConstant.COMMON_WEB_TITLE,title)
            context.startActivity(intent)
        }
    }
}