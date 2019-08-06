package com.stone.wanandroid.web

import android.os.Build
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
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

        mProgressBar.max = 100

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mProgressBar.progressDrawable = resources.getDrawable(R.drawable.common_web_progress_bg, null)
        } else {
            mProgressBar.progressDrawable = resources.getDrawable(R.drawable.common_web_progress_bg)
        }

        rl_container_common_web_activity.addView(mWebView)
        rl_container_common_web_activity.addView(mProgressBar)

        initWebView()
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
            super.onProgressChanged(view, newProgress)
            if (newProgress == 100) {
                this@CommonWebViewActivity.mProgressBar.visibility = View.GONE
            } else {
                this@CommonWebViewActivity.mProgressBar.visibility = View.VISIBLE
                this@CommonWebViewActivity.mProgressBar.progress = newProgress
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