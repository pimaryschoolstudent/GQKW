package com.example.myapplication.Utils

import android.content.Context
import android.content.Intent

class SkipUtils {
    companion object{
        private var context:Context ? =null
        private var instance:SkipUtils ?= null
        get() {
            if (field==null){
                field = SkipUtils()
            }
            return field
        }
        @Synchronized
        fun get(context: Context):SkipUtils{
            this.context= context
            return instance!!
        }
    }

    fun skipActivity(c: Class<*>) {
        var intent = Intent(context,c)
        context?.startActivity(intent)
    }


    fun skipActivityWithParems(c: Class<*>,map:HashMap<String,Any>) {
        var intent = Intent(context,c)
        for((k,v) in map){
            when{
                v is Int -> intent.putExtra(k,v)
                v is String -> intent.putExtra(k,v)
                v is Boolean -> intent.putExtra(k,v)
                v is Long -> intent.putExtra(k,v)
                v is Float -> intent.putExtra(k,v)
                v is Double -> intent.putExtra(k,v)
                v is Char -> intent.putExtra(k,v)
            }
        }
        context?.startActivity(intent)
    }
}