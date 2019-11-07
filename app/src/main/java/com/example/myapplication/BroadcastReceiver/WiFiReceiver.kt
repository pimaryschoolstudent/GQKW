package com.example.myapplication.BroadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.util.Log
import com.example.myapplication.Main.MainList.MainActivity
import com.example.myapplication.Main.MainList.Model.ConnectEvent
import com.example.myapplication.Utils.RxBus
import android.net.NetworkInfo
import com.example.myapplication.Base.Model.Constract.Companion.CONNECTION_STATUS

import com.example.myapplication.Main.MainList.MainPresenter


class WiFiReceiver ():BroadcastReceiver(){
    private var nowTime:Long=0
    private var isOpen=false
    override fun onReceive(p0: Context?, p1: Intent?) {
        val mainActivity = p0 as MainActivity
        var mainPresenter = mainActivity.getPresenter()
        if (p1?.action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            val info = p1?.getParcelableExtra<NetworkInfo>(WifiManager.EXTRA_NETWORK_INFO)
            Log.i("getState", "connectState:" + info?.state)
            var systemTime = System.currentTimeMillis()
            var state = info?.state
            if (state?.equals(NetworkInfo.State.CONNECTED)!!){//链接上wifi,防重复调用
                getNewIp(mainPresenter,systemTime)
                isOpen=true
            }else if (state?.equals(NetworkInfo.State.DISCONNECTED)){//关闭连接
                isOpen=false
                getNewIp(mainPresenter,systemTime)
            }
        }
    }
    private fun getNewIp(mainPresenter:MainPresenter,systemTime: Long){
        if (checkTimeinterval(systemTime)){
            mainPresenter.getIp()
        }
        if (CONNECTION_STATUS&&isOpen){
            RxBus.util.post(ConnectEvent(true,"请确保电脑手机连接至同一WiFi"))
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