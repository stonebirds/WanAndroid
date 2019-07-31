package com.stone.net.bean

data class BaseResult<T>(
    var errorCode: Int = 0,
    var errorMsg: String = "",
    var data: T
)