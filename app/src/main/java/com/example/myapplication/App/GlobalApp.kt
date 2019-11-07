package com.example.myapplication.App

import android.app.Application
import com.example.myapplication.Utils.ToastUtils
import com.example.myapplication.Utils.ScreenAdapter

class GlobalApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ToastUtils.get.init(this)
        resetDensity()
    }

    fun resetDensity(){
        ScreenAdapter.setup(this)
        ScreenAdapter.register(this,360f,ScreenAdapter.MATCH_BASE_WIDTH,ScreenAdapter.MATCH_UNIT_DP)
    }
}