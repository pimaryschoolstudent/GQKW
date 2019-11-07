package com.example.myapplication.Main.MainList

import android.os.Build
import android.text.Editable
import android.view.View
import androidx.annotation.RequiresApi
import com.example.myapplication.Base.BaseActivity
import com.example.myapplication.Base.Listener.BaseTextWatcher
import com.example.myapplication.Base.Model.Constract
import com.example.myapplication.R
import com.example.myapplication.Utils.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_header.*
import kotlinx.android.synthetic.main.activity_main_header.view.*
class MainActivity:BaseActivity(),MainManifest.MainView{

    var MainPresenter = MainPresenter(this)
    lateinit var HeaderView:View
    override fun addTobBar(): Boolean {
       return true
    }

    override fun initHeader() {

    }

    override fun ProhibitedConnect(message:String) {
        HeaderView.tv_ip.textSize=23f
        HeaderView.edt_port.textSize=23f
        HeaderView.ip_vs.visibility=View.VISIBLE
        HeaderView.prot_vs.visibility= View.VISIBLE
        HeaderView.tv_hint.text="温馨提示：请用电脑浏览器登录以上网址"
        HeaderView.main_lav.isClickable=false
        HeaderView.tv_connect.text=message
        HeaderView.edt_port.setFocusable(false)
        HeaderView.edt_port.setFocusableInTouchMode(false)
    }

    override fun setPort(port: String) {
        HeaderView.edt_port.setText(port)
    }

    override fun showBtn() {
        HeaderView.main_lav.setAnimation("btn_connect.json")
        HeaderView.main_lav.playAnimation()
    }

    override fun showList() {
        HeaderView=MainPresenter.setAdapter(main_list,R.layout.item_main_list,R.layout.activity_main_header)
    }

    override fun setIp(ip: String) {
        HeaderView.tv_ip.text = ip+":"
//        HeaderView.prot_vs.visibility=View.GONE
//        HeaderView.ip_vs.visibility=View.GONE
        HeaderView.edt_port.setFocusable(true)
        HeaderView.edt_port.setFocusableInTouchMode(true)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun Entrance() {
//        testUtils.AndroidWindow(this)
//        MainPresenter.getIp()//通过receiver调用
        MainPresenter.registerReceiver()
        MainPresenter.registerBus()
        showList()
        showBtn()
        setListener()
        TopBarInit()
        MainPresenter.checkOpenType()
    }

    override fun TopBarInit(){
        super.setTitle("菜单")
    }

    override fun onDestroy() {
        super.onDestroy()
        Constract.CONNECTION_STATUS =false
        MainPresenter.stopConnect()
    }

    fun setListener(){
        HeaderView.main_lav.setOnClickListener {
            MainPresenter.toIpConnect(edt_port.text.toString().toInt())
        }
        HeaderView.edt_port.addTextChangedListener(object: BaseTextWatcher() {
            override fun afterTextChanged(p0: Editable?) {
                var value = p0.toString()
                if (!value.isEmpty()){
                    var port = value.toInt()
                    if (port<=0||port>65535){
                        ToastUtils.get.showText("端口号只能在1至65535之间")
                        edt_port.setText("")
                    }
                }
            }
        })
    }

    fun getPresenter():MainPresenter{
        return MainPresenter
    }

}