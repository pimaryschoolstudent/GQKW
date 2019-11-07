package com.example.myapplication.Main.MainList

import android.content.Context
import android.content.Intent
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable.RESTART
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.myapplication.Main.FileConnect.FileConnectActivity
import com.example.myapplication.Main.MainList.Model.ListData
import com.example.myapplication.R
import com.example.myapplication.Utils.ToastUtils


class MainListAdapter(data: ArrayList<ListData>,id:Int,context: Context): BaseQuickAdapter<ListData, BaseViewHolder>(id,data) {
    var context = context
    var connectOk =false
    override fun convert(helper:BaseViewHolder?, item: ListData?) {
        helper?.setText(R.id.item_tv,item?.title)
        var animationView=helper?.getView<LottieAnimationView>(R.id.item_lav)
        animationView?.setAnimation(item?.AnimationPath)
        helper?.itemView?.setOnClickListener {
            if (connectOk){
                   context.startActivity(Intent(context,FileConnectActivity::class.java))
            }else{
                ToastUtils.get.showText("暂未连接至电脑")
            }
        }
        animationView?.playAnimation()
    }
    fun setConnect(boolean: Boolean){
        connectOk=true
    }
}