package com.stone.common.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import com.stone.common.R


/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-06
 */
class SProgressDialog(context: Context) : Dialog(context) {
    var pbDialog: ProgressBar? = null
    var tvDialog: TextView? = null
    var message: String = "正在加载中..."

    constructor(context: Context, message: String) : this(context) {
        this.message = message
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.dialog_progress)

        setCanceledOnTouchOutside(false)
        setCancelable(false)

        pbDialog = findViewById<ProgressBar>(R.id.pb_progress_dialog)
        tvDialog = findViewById<TextView>(R.id.tv_progress_dialog)

        tvDialog?.text = message
    }
}