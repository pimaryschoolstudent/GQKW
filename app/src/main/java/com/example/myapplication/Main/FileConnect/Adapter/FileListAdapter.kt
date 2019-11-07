package com.example.myapplication.Main.FileConnect.Adapter

import android.os.Environment
import android.widget.Button
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.myapplication.Main.FileConnect.Bean.FileData
import com.example.myapplication.Main.FileConnect.DataBase.FileBean
import com.example.myapplication.R
import com.example.myapplication.Utils.BitmapUtil
import com.example.myapplication.widget.LoadingDialog
import java.text.DecimalFormat

import java.math.BigDecimal


/**
 * @author HuangJiaHeng
 * @date 2019/10/16.
 */
class FileListAdapter(item:Int,data:List<FileBean>):BaseQuickAdapter<FileBean,BaseViewHolder>(item,data){

    override fun convert(helper: BaseViewHolder?, item: FileBean?) {
        checkFileType(item,helper)
        checkFileSize(item,helper)
        helper?.setText(R.id.tv_file_name,item?.fileName)
        helper?.getView<Button>(R.id.btn_receivce)?.setOnClickListener {
            var dialog = LoadingDialog(helper?.itemView.context)
            dialog.showDialog()
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                BitmapUtil.Base64ToFile(item!!,Environment.getExternalStorageDirectory().absolutePath+"/")
            }
            dialog.close()
        }
    }
    private fun checkFileSize(item: FileBean?,helper: BaseViewHolder?){
        var size = item?.fileSize?.toLong()!!
        if (size<1024L){
            helper?.setText(com.example.myapplication.R.id.tv_size,"$size B")
        }else if (size<1048576L){
            helper?.setText(com.example.myapplication.R.id.tv_size,"${size/1024} KB")
        }else if (size<1073741824L){
            helper?.setText(com.example.myapplication.R.id.tv_size,"${size/(1024*1024)} MB")
        }else{
            helper?.setText(com.example.myapplication.R.id.tv_size,"${size/(1024*1024*1024)} GB")
        }

    }
    private fun checkFileType(item: FileBean?,helper: BaseViewHolder?){
        if (item?.fileName?.contains("apk")!!){
            helper?.setImageResource(com.example.myapplication.R.id.iv_type, com.example.myapplication.R.drawable.file_apk)
        }else if (item?.fileName?.contains("doc")||item?.fileName?.contains("docx")){
            helper?.setImageResource(com.example.myapplication.R.id.iv_type, com.example.myapplication.R.drawable.file_docx)
        }else if (item?.fileName?.contains("png")||item?.fileName?.contains("jpg")||
            item?.fileName?.contains("jpeg")||item?.fileName?.contains("gif")){
            helper?.setImageResource(com.example.myapplication.R.id.iv_type, com.example.myapplication.R.drawable.file_image)
        }else if (item?.fileName?.contains("avi")||item?.fileName?.contains("mp4")||
            item?.fileName?.contains("mp3")||item?.fileName?.contains("mkv")
            ||item?.fileName?.contains("mov")){
            helper?.setImageResource(com.example.myapplication.R.id.iv_type, com.example.myapplication.R.drawable.file_mp4)
        }else if (item?.fileName?.contains("ppt")){
            helper?.setImageResource(com.example.myapplication.R.id.iv_type, com.example.myapplication.R.drawable.file_ppt)
        }else if (item?.fileName?.contains("aar")||item?.fileName?.contains("zip")||item?.fileName?.contains("7z")){
            helper?.setImageResource(com.example.myapplication.R.id.iv_type, com.example.myapplication.R.drawable.file_rar)
        }else{
            helper?.setImageResource(com.example.myapplication.R.id.iv_type, com.example.myapplication.R.drawable.file_txt)
        }
    }
}