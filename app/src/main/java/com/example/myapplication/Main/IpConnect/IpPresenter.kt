package com.example.myapplication.Main.IpConnect

import com.example.myapplication.Base.Model.Constract.Companion.CONNECTION_STATUS
import com.example.myapplication.Base.Model.Constract.Companion.TO_IP_CONNECT
import com.example.myapplication.Base.Model.Constract.Companion.TO_IP_PORT
import com.example.myapplication.Main.MainList.MainActivity
import com.example.myapplication.Main.MainList.Model.ConnectEvent
import com.example.myapplication.Utils.RxBus
import com.example.myapplication.Utils.ServerUtils
import com.example.myapplication.Utils.SkipUtils
import com.example.myapplication.WebSocket.AndroidWebSocketServer


class IpPresenter(context: IpConnectActivity):IpConnectManifest.IpPresenter{


    private val context =context

    override fun connectOk() {
        CONNECTION_STATUS=true
        SkipUtils.get(context).skipActivity(MainActivity::class.java)
        context.finish()
    }

    override fun startConnect() {
        AndroidWebSocketServer.util.start()
        ServerUtils.get().startServer(context.intent.getIntExtra(TO_IP_PORT,0),context)
        RxBus.util.post(ConnectEvent(true,"连接成功"))
    }

    override fun getAddress() {
        context.SetIp(context.intent.getStringExtra(TO_IP_CONNECT),context.intent.getIntExtra(TO_IP_PORT,0))
    }
}