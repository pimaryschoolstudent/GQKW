package com.example.myapplication.Main.PcConnecting

interface PcConnectingManifest {
    interface PcConnectingView{
        fun TopBarInit()
        fun setHint(message:String)
    }
    interface PcConnectingPersenter{
        fun toIpConnect()
        fun checkConnectType()
    }
}