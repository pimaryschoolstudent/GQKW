package com.example.myapplication.Main.PcConnecting

import com.example.myapplication.Base.Model.Constract
import com.example.myapplication.Base.Model.Constract.Companion.TO_IP_CONNECT
import com.example.myapplication.Base.Model.Constract.Companion.TO_IP_PORT
import com.example.myapplication.Main.IpConnect.IpConnectActivity
import com.example.myapplication.Utils.IpUtils
import com.example.myapplication.Utils.SkipUtils
import com.example.myapplication.Utils.ToastUtils

class PcConnectingPersenter (context:PcConnectingActivity):PcConnectingManifest.PcConnectingPersenter{
    private var context=context

    override fun toIpConnect() {
        if (!IpUtils.instance.checkWifi()){
            if (IpUtils.instance.checkHotPort()){
                skipNext()
            }else{
                ToastUtils.get.showText("请打开手机热点开关")
            }
        }else{
            skipNext()
        }
    }

    override fun checkConnectType() {
        if (IpUtils.instance.checkWifi()){
            context.setHint("请确保电脑和手机连接同一个WiFi")
        }else{
            context.setHint("请使用电脑连接手机热点")
        }
    }

    private fun skipNext(){
        var map = HashMap<String,Any>()
        map.put(TO_IP_CONNECT, context.intent.getStringExtra(Constract.TO_IP_CONNECT))
        map.put(TO_IP_PORT,context.intent.getIntExtra(Constract.TO_IP_PORT,0))
        SkipUtils.get(context).skipActivityWithParems(IpConnectActivity::class.java,map)
        context.finish()
    }
}