package com.example.myapplication.Main.FileConnect

import android.os.Handler
import android.util.Log
import com.example.myapplication.Base.RxJava.BaseObserver
import com.example.myapplication.Main.FileConnect.Adapter.FileListAdapter
import com.example.myapplication.Main.FileConnect.DataBase.FileBean
import com.example.myapplication.Main.FileConnect.DataBase.FileDataBase
import com.example.myapplication.R
import com.example.myapplication.Utils.RxBus
import com.example.myapplication.Utils.ToastUtils
import com.example.myapplication.Utils.registerInBus
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.file_connect.*

class FileConnectPresenter (var context:FileConnectActivity):FileConnectManifest.FileConnectPresenr{
    private val TAG = "FileConnect"
    private var listData =  ArrayList<FileBean>()
    private lateinit var listAdapter:FileListAdapter
    private var mHandler:Handler = Handler()
    override fun setAdapter() {
        listAdapter = FileListAdapter(R.layout.file_connect_list_item,listData)
        context.file_rv_list.adapter = listAdapter
        listAdapter.bindToRecyclerView(context.file_rv_list)
        listAdapter.setEmptyView(R.layout.file_connect_none)
        getFileDataBaseAllData()
    }

    override fun deleteAllFileData() {
        Observable.create(ObservableOnSubscribe<Boolean> {
            FileDataBase.getInstance(context).deleteAllData()
            it.onNext(true)
        }).compose(RxBus.handler())
            .subscribe(object :BaseObserver<Boolean>(){
                override fun onSucess(result: Boolean) {
                    if (result){
                        listData.clear()
                        listAdapter.notifyDataSetChanged()
                    }
                }
            })
        ToastUtils.get.showText("清空历史记录")
    }

    private fun getFileDataBaseAllData(){
        listData.clear()
        Observable.create(ObservableOnSubscribe<List<FileBean>> {
            it.onNext(FileDataBase.getInstance(context).loadDataBaseData())
        }).compose(RxBus.handler())
            .subscribe(object :BaseObserver<List<FileBean>>(){
                override fun onSucess(result: List<FileBean>) {
                    Log.e(TAG,result.size.toString())
                    listData.addAll(result)
                    listAdapter.notifyDataSetChanged()
                }
            })

    }
    override fun registerReceiver(){
            RxBus.util.observe<FileBean>()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.e(TAG, "接收到文件" + it.fileName)
                    listData.add(it)
                    listAdapter.notifyDataSetChanged()
                }.registerInBus(this)
    }
    override fun unregisterReceiver() {
        RxBus.util.unregister(this)
    }
}