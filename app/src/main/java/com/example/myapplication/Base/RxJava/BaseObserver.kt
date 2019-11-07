package com.example.myapplication.Base.RxJava

import android.util.Log
import com.example.myapplication.Utils.ToastUtils
import com.google.gson.JsonSyntaxException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

abstract class BaseObserver<T>: Observer<T> {
    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: T) {
        onSucess(t)
    }

    override fun onError(e: Throwable) {
        try {
            val msg: String
            if (e is SocketTimeoutException) {
                msg = "连接服务器超时,请检查您的网络状态"
                onFailure(msg)
            } else if (e is ConnectException) {
                msg = "网络中断，请检查您的网络状态"
                onFailure(msg)
            } else if (e is TimeoutException) {
                msg = "连接超时，请检查您的网络状态"
               onFailure(msg)
            } else if (e is JsonSyntaxException) {
                Log.e("SubscriberCallBack", "JSON解析错误，请查看JSON结构", e)
                e.printStackTrace()
               onFailure(e.message)
            } else {
                onFailure(e.message)
            }
        } catch (e1: Exception) {
            onFailure(e1.message)
            e1.printStackTrace()
        }

        e.message
    }
    fun onFailure(message:String?){
        ToastUtils.get.showText(message!!)
    }
    abstract fun onSucess(result:T)
}