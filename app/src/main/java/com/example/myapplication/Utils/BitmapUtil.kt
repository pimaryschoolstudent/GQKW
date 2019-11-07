package com.example.myapplication.Utils

import android.Manifest
import android.util.Base64
import android.util.Log
import com.example.myapplication.Main.FileConnect.DataBase.FileBean
import java.io.File
import java.io.FileOutputStream

/**
 * @author HuangJiaHeng
 * @date 2019/11/7.
 */
object BitmapUtil {
    val TAG = "BitmapUtil"
    fun Base64ToFile(data: FileBean, path:String): File {
        Log.e(TAG,path)
        var dataByte = Base64.decode(data.file,Base64.DEFAULT)
        var file = File(path+data.fileName)
        if (file.exists()){
            file.delete()
        }
        file.createNewFile()
        FileOutputStream(file).write(dataByte)
        ToastUtils.get.showText("文件已保存，请到内部存储根目录查看！"+path+data.fileName)
        return file
    }
}