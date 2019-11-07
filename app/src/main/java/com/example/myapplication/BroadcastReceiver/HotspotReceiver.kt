package com.example.myapplication.BroadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.util.Log
import com.example.myapplication.Base.Model.Constract
import com.example.myapplication.Main.MainList.MainActivity
import com.example.myapplication.Main.MainList.MainPresenter
import com.example.myapplication.Main.MainList.Model.ConnectEvent



class HotspotReceiver ():BroadcastReceiver(){
    var nowTime =0L
    var isOpen = false
    override fun onReceive(p0: Context?, p1: Intent?) {
        var mainActivity = p0 as MainActivity
        val hotSpotState = p1?.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0)
        Log.i("getState",hotSpotState.toString())
        if (hotSpotState==13){//打开
            isOpen=true
            getNewIp(mainActivity.MainPresenter,System.currentTimeMillis());
        }else if(hotSpotState==11){//关闭
            isOpen=false
            getNewIp(mainActivity.MainPresenter,System.currentTimeMillis());
        }
    }
    private fun getNewIp(mainPresenter: MainPresenter, systemTime: Long){
        if (checkTimeinterval(systemTime)){
            mainPresenter.getIp()
        }

    }
    private fun checkTimeinterval(systemTime:Long):Boolean{
        Log.i("getState","判断结果："+systemTime+"-"+nowTime+"="+(systemTime-nowTime))
        if (systemTime-nowTime>800L){
            nowTime = System.currentTimeMillis()
            return true
        }
        return false
    }
}