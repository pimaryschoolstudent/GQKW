package com.example.myapplication.WebSocket

import android.util.Log
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

/**
 * @author HuangJiaHeng
 * @date 2019/11/1.
 */
open class AndroidWebSocketClient(serverUri: URI) : WebSocketClient(serverUri, Draft_6455()) {
    private val TAG = "AndroidWebSocketClient"
    override fun onOpen(handshakedata: ServerHandshake?) {
        Log.e(TAG,"onOpen")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        Log.e(TAG,"onClose")
    }

    override fun onMessage(message: String?) {
        Log.e(TAG,"onMessage")
    }

    override fun onError(ex: Exception?) {
        Log.e(TAG,"onError")
    }

}