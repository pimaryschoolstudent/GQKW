package com.example.myapplication.Utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser

/**
 * @author HuangJiaHeng
 * @date 2019/10/15.
 */
object GsonUtil {
    fun <T>StringToObject(data:String,clazz: Class<T>):T{
        var ob = Gson().fromJson<T>(data,clazz)
        return ob
    }
    fun StringToJsonObject(data:String):JsonObject{
        return JsonParser().parse(data).asJsonObject
    }
}