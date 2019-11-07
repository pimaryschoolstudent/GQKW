package com.example.myapplication.Utils

import android.content.Context
import android.util.Log
import com.example.myapplication.Base.RxJava.BaseObserver
import com.example.myapplication.Main.FileConnect.DataBase.FileBean
import com.example.myapplication.Main.FileConnect.DataBase.FileDataBase
import com.example.myapplication.WebSocket.AndroidWebSocketClient

import com.koushikdutta.async.AsyncServer
import com.koushikdutta.async.callback.CompletedCallback
import com.koushikdutta.async.http.server.AsyncHttpServer
import com.koushikdutta.async.http.server.AsyncHttpServerResponse
import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.koushikdutta.async.http.server.HttpServerRequestCallback
import org.json.JSONObject
import java.lang.Exception
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.*
import java.net.URI
import java.net.URLDecoder


class ServerUtils {
    private var TAG = "ServerUtil"
    companion object{
        private var server:AsyncServer ? =null
        private var httpServer:AsyncHttpServer ? =null
        private var listFile :ArrayList<FileBean> = ArrayList()
        private var inStance: ServerUtils?= null
            get() {
            if (field==null){
                field = ServerUtils()
            }
            return field
        }
        fun get(): ServerUtils {

            if (server ==null){
                server = AsyncServer()
            }
            if (httpServer ==null){
                httpServer = AsyncHttpServer()
            }
            return inStance!!
        }
    }

    fun startServer(port:Int,context: Context){
        initHttpServer(port,context)
    }
    fun stopServer(){
        if (httpServer !=null){
            httpServer?.stop()
        }
        if (server !=null){
            server?.stop()
        }
    }

    private fun getIndexContent(path:String,context: Context):String{
        var b = context?.assets?.open(path)?.readBytes()
        return String(b!!)
    }

    private fun getAssetsStream(fullPath: String,context: Context):BufferedInputStream{
        var fullPath = fullPath.replace("%20", " ")
        var resourceName = fullPath
        if (resourceName.startsWith("/")) {
            resourceName = resourceName.substring(1)
        }
        if (resourceName.indexOf("?") > 0) {
            resourceName = resourceName.substring(0, resourceName.indexOf("?"))
        }
       return BufferedInputStream(context.assets.open("gkqw/"+resourceName))
    }

    private fun initHttpServer(port:Int,context: Context){
        ListenIndexHtml(context)

        ListenIndexJs(context)

        ListenIndexCss(context)

        ListenServerError(context)

        ListenFilesUpload(context)

        connectAndroidWebSocket(context)

        httpServer?.listen(port)
    }

    private fun connectAndroidWebSocket(context: Context){
        val uri = URI.create("ws://"+IpUtils.instance.getIpAddress(context)+"")
        val client = object : AndroidWebSocketClient(uri) {
            override fun onMessage(message: String?) {

            }
        }
    }

    private fun ListenIndexHtml(context: Context){
        httpServer?.get("/",object: HttpServerRequestCallback {
            override fun onRequest(request: AsyncHttpServerRequest?, response: AsyncHttpServerResponse?) {
                try {
                    response?.send(getIndexContent("gkqw/index.html",context))
                }catch (e: Exception){
                    response?.code(404)?.end()
                }
            }
        })
    }

    private fun ListenIndexJs(context: Context){
        httpServer?.get("/js/easyUploader.jq.js",
            HttpServerRequestCallback { request, response ->
                try {
                    var fullPath = request.path
                    Log.e(TAG,"请求的链接：$fullPath")
                    val bInputStream =  getAssetsStream(fullPath,context)
                    response.sendStream(bInputStream, bInputStream.available().toLong())
                } catch (e: IOException) {
                    e.printStackTrace()
                    response.code(404).end()
                    return@HttpServerRequestCallback
                }
            })
    }

    private fun ListenIndexCss(context:Context){
        httpServer?.get("/css/main.css",
            HttpServerRequestCallback { request, response ->
                try {
                    var fullPath = request.path
                    Log.e(TAG, "请求的链接：$fullPath")
                    val bInputStream = getAssetsStream(fullPath,context)
                    response.sendStream(bInputStream, bInputStream.available().toLong())
                } catch (e: IOException) {
                    e.printStackTrace()
                    response.code(404).end()
                    return@HttpServerRequestCallback
                }
            })
    }

    private fun ListenServerError(context: Context){
        httpServer?.errorCallback = CompletedCallback { ex -> Log.e(TAG,"ExP :"+ex?.message) }
    }



    private fun ListenFilesUpload(context: Context){
        httpServer?.post("/uploadFiles")
        { request, response ->
            var requestData = URLDecoder.decode(request.body.get().toString(),"utf-8")
            var data = GsonUtil.StringToObject(requestData,FileBean::class.java)
            Log.e(TAG,data.fileName)
            listFile?.add(data)
            NotificationUtil.getInstance(context).showReceiveFileNotification(data.fileName)
            sendSuccessResponse(response,"文件发送成功")
            RxBus.util.post(data)
            Observable.just(data).subscribeOn(Schedulers.io())
                .subscribe(object : BaseObserver<FileBean>(){
                    override fun onSucess(result: FileBean) {
                        FileDataBase.getInstance(context).insertFile(result)
                    }
                })
        }
    }
    private fun sendSuccessResponse(response: AsyncHttpServerResponse,message:String){
       var data = JSONObject()
        data.put("code",200)
        data.put("data",message)
        response.code(200).send(data)
    }

    fun getFileListData():List<FileBean>{
        return listFile
    }
}