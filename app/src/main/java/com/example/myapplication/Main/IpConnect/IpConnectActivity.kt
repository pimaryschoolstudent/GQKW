package com.example.myapplication.Main.IpConnect

import com.example.myapplication.Base.BaseActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.ip_connect.*

class IpConnectActivity : BaseActivity(),IpConnectManifest.IpView {
    var ipPresenter = IpPresenter(this)
    override fun TopbarInit() {
        showBlackIcon(true)
        setTitle("连接成功")
    }

    override fun addTobBar(): Boolean {
         return true
     }

     override fun Entrance() {
         ipPresenter.startConnect()
         ipPresenter.getAddress()
         TopbarInit()

         setListener()
     }

     override fun SetIp(ip:String,port:Int) {
         tv_Address.setText("http://"+ip+":"+port+"/")
    }


    override fun getLayoutId(): Int {
        return R.layout.ip_connect
    }

    private fun setListener(){
        btn_next.setOnClickListener {
            ipPresenter.connectOk()
        }
    }

}
