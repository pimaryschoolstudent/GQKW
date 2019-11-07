package com.example.myapplication.Utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.myapplication.Base.Model.Constract
import com.example.myapplication.BuildConfig
import com.example.myapplication.Main.FileConnect.FileConnectActivity
import com.example.myapplication.Main.MainList.MainActivity
import com.example.myapplication.Main.MainList.MainPresenter
import com.example.myapplication.R
import okhttp3.internal.notify

/**
 * @author HuangJiaHeng
 * @date 2019/10/15.
 */
class NotificationUtil {
    companion object{
        private val FILE_CHANNEL_ID = 1001
        private var message:Notification ? =null
        private var util:NotificationUtil?=null
        get() {
            if (field==null){
                field = NotificationUtil()
            }
            return field
        }
        private lateinit var context:Context
        @Synchronized
        fun getInstance(context: Context):NotificationUtil{
            this.context = context
            return util!!
        }
    }

    fun showReceiveFileNotification(fileName:String){
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra(Constract.MAINACTIVITY_OPENTYPE,"Notification")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        message = NotificationCompat.Builder(context,BuildConfig.APPLICATION_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("【收到新文件】")
                    .setContentText("收到文件$fileName，点击进行接收")
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(PendingIntent.getActivity(context,0,intent,0))
                    .build()
        sendNotification()
    }

    private fun sendNotification(){
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = BuildConfig.APPLICATION_ID
            val descriptionText = BuildConfig.APPLICATION_ID
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(BuildConfig.APPLICATION_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (notificationManager.areNotificationsEnabled()){
                notificationManager.notify(FILE_CHANNEL_ID,message)
            }else{
                ToastUtils.get.showText("检测你通知权限没开，请开启通知权限")
                context.startActivity(Intent().setAction(ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.fromParts("package",BuildConfig.APPLICATION_ID,null)))
            }
        } else {
            notificationManager.notify(FILE_CHANNEL_ID,message)
        }

    }
}