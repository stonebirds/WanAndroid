package com.stone.wanandroid.web

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.LogUtils
import com.stone.common.base.BaseActivity
import com.stone.wanandroid.R
import com.stone.wanandroid.constant.CommonConstant
import kotlinx.android.synthetic.main.activity_coommon_web_activity.*
import kotlinx.android.synthetic.main.layout_common_title.*

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-05
 */
class CommonWebViewActivity : BaseActivity() {
    private lateinit var mWebView: WebView
    private lateinit var mProgressBar: ProgressBar
    private var url: String? = null
    private var title: String? = null

    override fun layoutId(): Int {
        return R.layout.activity_coommon_web_activity
    }

    override fun start() {
    }

    override fun initView() {
        iv_back.setOnClickListener { it -> finish() }

        mWebView = WebView(this)
        mProgressBar = ProgressBar(this)

        url = intent.getStringExtra(CommonConstant.COMMON_WEB_URL)
        title = intent.getStringExtra(CommonConstant.COMMON_WEB_TITLE)

        initProgressBar(mProgressBar)

        rl_container_common_web_activity.addView(mWebView)
        rl_container_common_web_activity.addView(mProgressBar)

        initWebView()
    }

    private fun initProgressBar(progressBar: ProgressBar) {
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(1f))
        progressBar.layoutParams = params
        progressBar.isIndeterminate = false
        progressBar.max = 100
        progressBar.progressDrawable = resources.getDrawable(R.drawable.webview_progress_horizontal)
    }

    private fun initWebView() {
        val settings = mWebView.settings
        settings.setAppCacheEnabled(true)
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.javaScriptEnabled = true
        settings.setSupportZoom(false)

        mWebView.webChromeClient = MWebChromeClient()
        mWebView.webViewClient = MWebViewClient()

        mWebView.loadUrl(url)
    }

    override fun initData() {

    }

    inner class MWebChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            LogUtils.d("newProgress------------$newProgress")
            super.onProgressChanged(view, newProgress)
            if (newProgress == 100) {
                mProgressBar.visibility = View.GONE
            } else {
                mProgressBar.visibility = View.VISIBLE
                mProgressBar.progress = newProgress
            }
        }
    }

    inner class MWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageFinished(view: WebView, url: String?) {
            super.onPageFinished(view, url)
            tv_title.text = view.title
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (mWebView.canGoBack()) {
            mWebView.goBack()
        } else {
            finish()
        }
    }
}