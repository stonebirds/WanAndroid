package com.stone.net.manager

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class RxSubscribe<T> : Observer<T> {
    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        if (e is HttpException) { //连接服务器成功但服务器返回错误状态码
            val apiErrorModel: ServerException = when (e.code()) {
                ApiErrorType.INTERNAL_SERVER_ERROR.code ->
                    ApiErrorType.INTERNAL_SERVER_ERROR.getServerException()
                ApiErrorType.BAD_GATEWAY.code ->
                    ApiErrorType.BAD_GATEWAY.getServerException()
                ApiErrorType.NOT_FOUND.code ->
                    ApiErrorType.NOT_FOUND.getServerException()

                else -> ApiErrorType.UNEXPECTED_ERROR.getServerException()
            }
            onFailed(e.code(), apiErrorModel.errorMsg)
            return
        }

        val apiErrorType: ApiErrorType = when (e) {  //发送网络问题或其它未知问题，请根据实际情况进行修改
            is UnknownHostException -> ApiErrorType.NETWORK_NOT_CONNECT
            is ConnectException -> ApiErrorType.NETWORK_NOT_CONNECT
            is SocketTimeoutException -> ApiErrorType.CONNECTION_TIMEOUT
            else -> ApiErrorType.UNEXPECTED_ERROR
        }
        onFailed(apiErrorType.code, apiErrorType.getServerException().errorMsg)
    }

    override fun onComplete() {

    }

    abstract fun onSuccess(data: T)

    abstract fun onFailed(errorCode: Int, errorMsg: String)

}

enum class ApiErrorType(val code: Int, val message: String) {
    //根据实际情况进行增删
    INTERNAL_SERVER_ERROR(500, "服务器出错"),
    BAD_GATEWAY(502, "服务器出错"),
    NOT_FOUND(404, "请求路径错误"),
    CONNECTION_TIMEOUT(408, "连接超时"),
    NETWORK_NOT_CONNECT(499, "网络出错"),
    UNEXPECTED_ERROR(700, "未知错误");

    private val DEFAULT_CODE = 1

    fun getServerException(): ServerException {
        return ServerException(DEFAULT_CODE, message)
    }
}

data class ServerException(val errorCode: Int, val errorMsg: String)

