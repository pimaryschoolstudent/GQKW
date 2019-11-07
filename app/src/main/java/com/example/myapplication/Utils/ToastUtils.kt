package com.example.myapplication.Utils

import android.content.Context
import android.widget.Toast

class ToastUtils {
    private lateinit var context: Context
    private var toast:Toast?=null
    companion object{
        val get:ToastUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ToastUtils()
        }
    }
    fun init(context:Context){
        this.context = context
    }
    fun showText(message:String){
        if (toast==null){
            toast = Toast.makeText(context,message,Toast.LENGTH_SHORT)
        }else{
            toast?.setText(message)
        }
        toast?.show()
    }
}