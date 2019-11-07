package com.example.myapplication.Base

import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.Utils.DensityUtil

public abstract class BaseActivity (): AppCompatActivity() {
    lateinit var Topbar:Toolbar
    var menuArray:ArrayList<Int> ?= ArrayList()
    private var menu:Menu ? =null
    private var rightItemListener:RightItemListener ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(getLayoutId())
        if (addTobBar()){
            setTopBar()
        }
        Entrance()
    }
    fun setTopBar(){
        Topbar = Toolbar(this)
        Topbar.title="标题"
        var lp = ConstraintLayout.LayoutParams (MATCH_PARENT,DensityUtil.dip2px(this,50f))
        lp.leftToLeft=ConstraintLayout.LayoutParams.PARENT_ID
        lp.rightToRight=ConstraintLayout.LayoutParams.PARENT_ID
        lp.topToTop=ConstraintLayout.LayoutParams.PARENT_ID
        addContentView(Topbar,lp)
        setSupportActionBar(Topbar)

    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId==android.R.id.home){
            this.finish()
        }
        for (id in menuArray!!){
            if (item?.itemId == id){
                rightItemListener?.onClick(id)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun ShowTopBar(b:Boolean){
        if (b){
            Topbar.visibility = View.VISIBLE
        }else{
            Topbar.visibility = View.GONE
        }
    }

    fun getTopBar(): ActionBar? {
        return supportActionBar
    }

    fun setTitle(title:String){
        Topbar.title=title
    }

    fun setBackground(color:Int){
        Topbar.background = ColorDrawable(color)
    }

    fun showBlackIcon(b:Boolean){
        if (b){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }else{
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }
   fun addRightMenuItem(vararg resourceId:Int){
       resourceId.map {
           menuArray?.add(it)
       }
    }
    fun setRightItemListenter(rightItemListener: RightItemListener){
        this.rightItemListener = rightItemListener
    }
   override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuArray?.map {
           menu?.add(1,it,1,"清空回收站")?.setIcon(it)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
       }
        return super.onCreateOptionsMenu(menu)
    }
    interface RightItemListener{
        fun onClick(id:Int)
    }
    abstract fun addTobBar():Boolean
    abstract fun getLayoutId():Int
    abstract fun Entrance()

}