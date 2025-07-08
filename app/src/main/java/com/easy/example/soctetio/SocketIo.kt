package com.easy.example.soctetio

import com.easy.core.utils.log.LogUtils
import io.socket.client.IO
import io.socket.client.Socket
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.json.JSONObject

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.soctetio
 * @Date : 16:57
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class SocketIo{

    fun initIO() {

        val serverUrl = "http://117.73.18.229:5850"
        val options = IO.Options().apply {
            forceNew = true
            reconnection = true
            transports = arrayOf("websocket")  // 可确保不使用轮询
            timeout = 10000
        }

        val socket = IO.socket(serverUrl, options)

// 标准 Socket.IO 事件监听
        socket.on(Socket.EVENT_CONNECT) {
            LogUtils.e("✅ EVENT_CONNECT 已连接")
//            val loginJson = JSONObject().apply {
//                put("name", "测试人员22")
//                put("userId", "4bd0eaad5f434b199a9f618231401838")
//                // 其他字段略...
//            }
            val loginJson =  JSONObject("{\"name\":\"测试人员22\",\"userId\":\"4bd0eaad5f434b199a9f618231401838\",\"avatar\":\"http:\\/\\/117.73.18.229:81\\/file\\/download\\/AppIcon\\/2025\\/June\\/30\\/receitDpZRWMHAaAKNvcyI5r9SlzB9K0fRO.jpg\",\"password\":\"\",\"keepServerMail\":\"@inspur.com\",\"inUse\":1,\"type\":1,\"macAddress\":\"98d15c85-3beb-4f40-a5f3-2495df8dafb9\",\"loginTime\":1751621282274,\"version\":\"4.1.5\",\"organId\":\"05f55be45ce541f4850ee2f4b36c7d60\",\"token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJjZXNoaXJlbnl1YW4yMiIsImN0diI6IiIsImRpZCI6ImZmZmZmZmZmLWIwYzEtYjIwMS1mZmZmLWZmZmZlZjA1YWM0YSIsImR0eSI6Im1vYmlsZSIsImV4cCI6MTc1MTc3MzA0MSwiaWF0IjoxNzUxNTkzMDQxLCJpc3MiOiJodGltZWFwcCIsImp0aSI6IjU4ODZlZjZjNWM3MTRiNjNiZjJjYzJmNTA4YWNhMTU2IiwibmJmIjoxNzUxNTkzMDQxLCJvcmciOiIwNWY1NWJlNDVjZTU0MWY0ODUwZWUyZjRiMzZjN2Q2MCIsInVpZCI6IjRiZDBlYWFkNWY0MzRiMTk5YTlmNjE4MjMxNDAxODM4IiwidW5hIjoi5rWL6K-V5Lq65ZGYMjIifQ.AAmuqyjPQEEgtp33NcMs2t0vMwVa1hzSnJCSnDWBCG8\"}")
            socket.emit("login", loginJson)

        }

        socket.on(Socket.EVENT_DISCONNECT) {
            LogUtils.e("❌ EVENT_DISCONNECT 连接断开")
        }

        socket.on(Socket.EVENT_CONNECT_ERROR) {
            LogUtils.e("❗ EVENT_CONNECT_ERROR 连接错误: ${it.getOrNull(0)}")
        }

        socket.on(Socket.EVENT_CONNECT_TIMEOUT) {
            LogUtils.e("⏱ EVENT_CONNECT_TIMEOUT 超时")
        }

        socket.on(Socket.EVENT_ERROR) {
            LogUtils.e("💥 EVENT_ERROR 异常错误")
        }

        socket.on(Socket.EVENT_CONNECTING) {
            LogUtils.e("🔄 EVENT_CONNECTING 正在连接")
        }

// 自定义服务端事件监听
        socket.on("message") {
            LogUtils.e("📩 message 收到消息: ${it.getOrNull(0)}")
        }

        socket.on("login") {
            LogUtils.e("🔐 login 收到登录响应: ${it.getOrNull(0)}")
        }

        socket.on("newGroupMsg") {
            LogUtils.e("👥 newGroupMsg 收到群组消息: ${it.getOrNull(0)}")
        }

        socket.on("newTask") {
            LogUtils.e("📝 newTask 收到新任务: ${it.getOrNull(0)}")
        }

        socket.on("logout") {
            LogUtils.e("🚪 logout 被登出: ${it.getOrNull(0)}")
        }

        socket.on("exit_organ") {
            LogUtils.e("🏢 exit_organ 组织退出通知")
        }

        socket.on("ping") {
            LogUtils.e("📡 ping")
        }

        socket.on("pong") {
            LogUtils.e("📶 pong")
        }

// 开始连接
        socket.connect()

    }
    private fun doOkhttp() {
         val client = OkHttpClient()
         var webSocket: WebSocket? = null

        val request = Request.Builder()
//            .url("ws://192.168.212.88:8080/spider/crawling/wll")
            .url("http://117.73.18.229:5850")
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {

            override fun onOpen(webSocket:WebSocket, response:Response) {
                LogUtils.e( "✅ 连接成功")
                // 示例：发送一条消息
                webSocket.send("hello server")
            }

            override fun onMessage(webSocket:WebSocket, text: String) {
                LogUtils.e( "📨 接收到消息: $text")
            }

            override fun onMessage(webSocket:WebSocket, bytes:ByteString) {
                LogUtils.e("📨 接收到 ByteString: $bytes")
            }

            override fun onClosing(webSocket:WebSocket, code: Int, reason: String) {
                LogUtils.e( "⚠️ 正在关闭: $code / $reason")
                webSocket.close(code, reason)
            }

            override fun onClosed(webSocket:WebSocket, code: Int, reason: String) {
                LogUtils.e("❎ 已关闭: $code / $reason")
            }

            override fun onFailure(webSocket:WebSocket, t: Throwable, response: Response?) {
                LogUtils.e( "❌ 连接失败", t)
            }
        })

    }

}
