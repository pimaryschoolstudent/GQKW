package com.example.myapplication.Main.FileConnect

interface FileConnectManifest {
    interface FileConnectView{
        fun ToolbarInit()
        fun initList()
        fun notifyListDataChange()
        fun checkPermission()
    }
    interface FileConnectPresenr{
        fun setAdapter()
        fun deleteAllFileData()
        fun registerReceiver()
        fun unregisterReceiver()
    }
}