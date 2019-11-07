package com.example.myapplication.Main.IpConnect

interface IpConnectManifest {
    interface IpView{
        fun SetIp(ip:String,port:Int)
        fun TopbarInit()
    }
    interface IpPresenter {
        fun connectOk()
        fun startConnect()
        fun getAddress()
    }
}