package com.example.myapplication.Main.PcConnecting

import com.example.myapplication.Base.BaseActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.pc_connecting.*

class PcConnectingActivity ():BaseActivity(),PcConnectingManifest.PcConnectingView{

    var pcConnectingPersenter = PcConnectingPersenter(this)

    override fun setHint(message: String) {
        tv_hint.text = message
    }

    override fun TopBarInit() {
        setTitle("连接中")
        showBlackIcon(true)
    }


    override fun getLayoutId(): Int {
        return R.layout.pc_connecting
    }

    override fun addTobBar(): Boolean {
        return true
    }

    override fun Entrance() {
        TopBarInit()
        setListener()
        pcConnectingPersenter.checkConnectType()
    }

    private fun setListener(){
        btn_next.setOnClickListener {
            pcConnectingPersenter.toIpConnect()

        }
    }


}