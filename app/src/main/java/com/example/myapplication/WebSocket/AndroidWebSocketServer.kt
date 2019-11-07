package com.example.myapplication.WebSocket

import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.lang.Exception
import android.R.array
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.InetSocketAddress
import java.net.UnknownHostException
import java.nio.ByteBuffer


/**
 * @author HuangJiaHeng
 * @date 2019/11/1.
 */
class AndroidWebSocketServer :WebSocketServer{
    companion object{
        val util:AndroidWebSocketServer by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
            AndroidWebSocketServer(53788)
        }
        fun getServer():AndroidWebSocketServer{
            return util
        }
    }
    private val TAG = "AndroidWebSocketServer"
    @Throws(UnknownHostException::class)
    constructor(port: Int):super(InetSocketAddress(port))

    constructor(address: InetSocketAddress):super(address)


    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        conn.send("有人连接了服务器!") //This method sends a message to the new client
        broadcast("新的连接: " + handshake.resourceDescriptor) //This method sends a message to all clients connected
        Log.e(TAG,conn.remoteSocketAddress.address.hostAddress + " 加入当前连接!")
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        broadcast("$conn 离开连接!")
        Log.e(TAG,"$conn 离开连接!")
    }

    override fun onMessage(conn: WebSocket, message: String) {
        broadcast(message)
        Log.e(TAG,"$conn: $message")
    }

    override fun onMessage(conn: WebSocket, message: ByteBuffer) {
        broadcast(message.array())
        Log.e(TAG,"$conn: $message")
    }

    override fun onError(conn: WebSocket?, ex: Exception) {
        ex.printStackTrace()
        if (conn != null) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    override fun onStart() {
        Log.e(TAG,"服务开启")
        connectionLostTimeout = 0
        connectionLostTimeout = 100
    }

}