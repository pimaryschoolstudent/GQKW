package com.example.myapplication.Main.FileConnect

import android.Manifest
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Base.BaseActivity
import com.example.myapplication.R
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.file_connect.*


class FileConnectActivity : BaseActivity(),FileConnectManifest.FileConnectView {
    private val fileConnectPresenter = FileConnectPresenter(this)
    override fun checkPermission() {
        RxPermissions(this)
            .request(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe {
                if (!it){
                    finish()
                }
            }
    }
    override fun ToolbarInit() {
        setTitle("文件传输")
        showBlackIcon(true)
        addRightMenuItem(R.drawable.file_delete_icon)
        setRightItemListenter(object : RightItemListener{
            override fun onClick(id: Int) {
                if (id == R.drawable.file_delete_icon){
                    fileConnectPresenter.deleteAllFileData()
                }
            }
        })
    }
    override fun notifyListDataChange() {

    }
    override fun initList() {
        file_rv_list.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        fileConnectPresenter.setAdapter()
    }

    override fun addTobBar(): Boolean {
        return true
    }

    override fun getLayoutId(): Int {
        return R.layout.file_connect
    }

    override fun Entrance() {
        checkPermission()
        ToolbarInit()
        initList()
        fileConnectPresenter.registerReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()
        fileConnectPresenter.unregisterReceiver()
    }

}