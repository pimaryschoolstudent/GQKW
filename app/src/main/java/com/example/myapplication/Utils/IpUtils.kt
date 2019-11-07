package com.example.myapplication.Utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import androidx.core.content.ContextCompat.getSystemService



@Suppress("DEPRECATION")
class IpUtils private constructor() {
    private var context:Context?=null
    private var connectivityManager:ConnectivityManager ?=null
    companion object {
        val instance: IpUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            IpUtils() }
    }

    fun getIpAddress(context: Context):String{
        this.context = context
        var  info:NetworkInfo?=null
        connectivityManager= context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        info = connectivityManager?.activeNetworkInfo  //获取当前网络状态
        if (info==null){   //用户没开WiFi 和移动网络
            return getMobileIp()  //返回无线网卡ip
        }
        if(info.type ==ConnectivityManager.TYPE_WIFI){
            return getWifiIp() //返回wifiip
        }else{
            return getMobileIp()  //返回无线网卡ip(移动网络)
        }
    }

    fun checkWifi():Boolean{
        var info = connectivityManager?.activeNetworkInfo
        if (info != null){
            if (info.type==ConnectivityManager.TYPE_WIFI){
                return true
            }
        }
        return false
    }


    private fun getWifiIp():String{
        val wifiManager = context?.getApplicationContext()?.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        val ipAddress = wifiInfo.ipAddress
        Log.i("getIp",ipAddress.toString()+"")
        val ip =(ipAddress and 0xff).toString() + "." + (ipAddress shr 8 and 0xff) + "." + (ipAddress shr 16 and 0xff) + "." + (ipAddress shr 24 and 0xff)
        return ip;
    }
    private fun getMobileIp():String{
        var en = NetworkInterface.getNetworkInterfaces()//获取所有网络接口
        var i=0
        while(en.hasMoreElements()){
            var nif = en.nextElement()
            var eip = nif.inetAddresses
            i++
            Log.i("getIp","第"+i.toString()+"个interface\t"+nif.displayName)
            var j = 0
            while(eip.hasMoreElements()){
                var ia = eip.nextElement()
                j++
                Log.i("getIp","\t第"+j.toString()+"个inetAddress\t"+ia.hostAddress)
                if (nif.displayName.contains("wlan0")){ //获取无线网卡Ip地址
                    if (checkIp(ia.hostAddress)){
                        return ia.hostAddress
                    }
                }
            }
         }
        return "0.0.0.0"
    }

    private fun checkIp(ip:String):Boolean{
        if (ip.split(".").size==4&&!ip.equals("127.0.0.1")){
            return true
        }
        return false
    }

    fun checkHotPort():Boolean{
        val manager = context?.getSystemService(Context.WIFI_SERVICE) as WifiManager
        //通过放射获取 getWifiApState()方法
        val method = manager.javaClass.getMethod("getWifiApState")
        //调用getWifiApState() ，获取返回值
        val state = method.invoke(manager) as Int
        //通过放射获取 WIFI_AP的开启状态属性
        val field = manager.javaClass.getDeclaredField("WIFI_AP_STATE_ENABLED")
        //获取属性值
        val value = field.get(manager) as Int
        //判断是否开启
        return if (state == value) {
            true
        } else {
            false
        }
    }
}