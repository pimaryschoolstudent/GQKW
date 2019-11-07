package com.example.myapplication.Main.MainList

import android.content.Context
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.NETWORK_STATE_CHANGED_ACTION
import android.view.View
import androidx.recyclerview.widget.*
import com.example.myapplication.Base.Model.Constract
import com.example.myapplication.Base.Model.Constract.Companion.MAINACTIVITY_OPENTYPE
import com.example.myapplication.Base.Model.Constract.Companion.TO_IP_CONNECT
import com.example.myapplication.Base.Model.Constract.Companion.TO_IP_PORT
import com.example.myapplication.BroadcastReceiver.HotspotReceiver
import com.example.myapplication.BroadcastReceiver.WiFiReceiver
import com.example.myapplication.Main.FileConnect.FileConnectActivity
import com.example.myapplication.Main.MainList.Model.ConnectEvent
import com.example.myapplication.Main.MainList.Model.ListData
import com.example.myapplication.Main.PcConnecting.PcConnectingActivity
import com.example.myapplication.Utils.*
import com.example.myapplication.Utils.ServerUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main_header.*

class MainPresenter(context:MainActivity):MainManifest.MainPresenter{
    var context =context
    lateinit var MainListAdapter:MainListAdapter
    lateinit var wifiReceiver:WiFiReceiver
    lateinit var hotspotReceiver:HotspotReceiver
    override fun checkOpenType() {
        var type = context.intent.getStringExtra(MAINACTIVITY_OPENTYPE)
        if (type!=null){
            if (type.equals("Notification")){
                openListCheck()
                context.ProhibitedConnect("连接成功") //防止广播调用重置ip
                SkipUtils.get(context).skipActivity(FileConnectActivity::class.java)
            }
        }
    }

    override fun registerReceiver() {
        wifiReceiver = WiFiReceiver()
        hotspotReceiver = HotspotReceiver()
        val manager = context.applicationContext?.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val field = manager.javaClass.getDeclaredField("WIFI_AP_STATE_CHANGED_ACTION")
        var hotspotIntent=IntentFilter(field.get(manager)as String)
        context.registerReceiver(hotspotReceiver,hotspotIntent)
        var wifiIntent = IntentFilter(NETWORK_STATE_CHANGED_ACTION)
        context.registerReceiver(wifiReceiver,wifiIntent)
    }

    override fun stopConnect() {
        context.unregisterReceiver(wifiReceiver)
        context.unregisterReceiver(hotspotReceiver)
        ServerUtils.get().stopServer()
    }
    override fun openListCheck(){
        MainListAdapter.setConnect(true)
    }
    override fun registerBus() {
        RxBus.util.observe<ConnectEvent>()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
                if (it.connectOk){
                    ToastUtils.get.showText(it.message)
                    context.ProhibitedConnect("连接成功")
                    openListCheck()
                }
            }

    }

    override fun setAdapter(list: RecyclerView,id:Int,headerLayout:Int):View {
        var GridLayoutManager= GridLayoutManager(context,1)
        list.layoutManager=GridLayoutManager
        MainListAdapter = MainListAdapter(getListData(),id,context)
        var HeaderView = context.layoutInflater.inflate(headerLayout,null)
        MainListAdapter.addHeaderView(HeaderView)
        list.adapter=MainListAdapter
        return HeaderView
    }

    override fun toIpConnect(port:Int) {
        if (checkProt()){
            if (checkIp()){
                startConnect(port)
            }else{
                ToastUtils.get.showText("请确保手机开启热点或者连接了WiFi")
            }
        }else{
            ToastUtils.get.showText("端口号只能在1至65535之间")
        }
    }
    @Synchronized
    override fun getIp() {
        context.setIp(IpUtils.instance.getIpAddress(context))
        if (Constract.CONNECTION_STATUS){
            context.ProhibitedConnect("连接成功")
        }
    }

    private fun checkProt():Boolean{
        if (!context.edt_port.text.isEmpty()){
            return true
        }
        return false
    }

    private fun checkIp():Boolean{
        if(!context.tv_ip.text.toString().equals("0.0.0.0:")) {
            return true
        }
        return false
    }

    private fun startConnect(port: Int){
        var map = HashMap<String,Any>()
        map.put(TO_IP_CONNECT,IpUtils.instance.getIpAddress(context))
        map.put(TO_IP_PORT,port)
        SkipUtils.get(context).skipActivityWithParems(PcConnectingActivity::class.java,map)
    }


    private fun getListData():ArrayList<ListData>{
        return arrayListOf(ListData("文件传输","file.json"))
    }
}