package com.example.myapplication.Main.MainList

import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface MainManifest{
    interface MainView{
        fun initHeader()
        fun showList()
        fun setIp(ip:String)
        fun showBtn()
        fun setPort(port:String)
        fun TopBarInit()
        fun ProhibitedConnect(message:String)
    }
    interface MainPresenter{
        fun registerBus()
        fun setAdapter(list: RecyclerView,id:Int,HeaderLayoutId:Int): View
        fun toIpConnect(port:Int)
        fun getIp()
        fun stopConnect()
        fun registerReceiver()
        fun checkOpenType()
        fun openListCheck()
    }
}