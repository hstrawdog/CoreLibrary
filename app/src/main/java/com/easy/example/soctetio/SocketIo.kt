package com.easy.example.soctetio

import com.easy.core.utils.gson.GsonUtil
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
var  a =0
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
            val loginJson =  JSONObject("{\"name\":\"测试人员22\",\"userId\":\"4bd0eaad5f434b199a9f618231401838\",\"avatar\":\"http:\\/\\/117.73.18.229:81\\/file\\/download\\/AppIcon\\/2025\\/June\\/30\\/receitDpZRWMHAaAKNvcyI5r9SlzB9K0fRO.jpg\",\"password\":\"\",\"keepServerMail\":\"@inspur.com\",\"inUse\":1,\"type\":1,\"macAddress\":\"1ed6a329-1fa2-4ca4-bad9-cfb2bbaf4e66\",\"loginTime\":1752723394709,\"version\":\"4.1.5\",\"organId\":\"05f55be45ce541f4850ee2f4b36c7d60\",\"token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJjZXNoaXJlbnl1YW4yMiIsImN0diI6IiIsImRpZCI6ImZmZmZmZmZmLWIwYzEtYjIwMS1mZmZmLWZmZmZlZjA1YWM0YSIsImR0eSI6Im1vYmlsZSIsImV4cCI6MTc1MjgyODA3MywiaWF0IjoxNzUyNjQ4MDczLCJpc3MiOiJodGltZWFwcCIsImp0aSI6ImJiYmNiMTFlOGNkODRhN2U4OTlhMDk3YTA4MGJmMTY1IiwibmJmIjoxNzUyNjQ4MDczLCJvcmciOiIwNWY1NWJlNDVjZTU0MWY0ODUwZWUyZjRiMzZjN2Q2MCIsInVpZCI6IjRiZDBlYWFkNWY0MzRiMTk5YTlmNjE4MjMxNDAxODM4IiwidW5hIjoi5rWL6K-V5Lq65ZGYMjIifQ.5Kn5HuBK0rWxxe3pya9DISpPco7P6t3VsrGXkU7BcIg\"}")
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

            if (a==0){
                a=1

                var  request = JSONObject(it.getOrNull(0).toString())

//               var  jsonObject = JSONObject("{\"ConnectionId\":\"${request.getString("ConnectionId")}\",\"Body\":{\"mailId\":\"686224b0dbe5d57602345d69\",\"uuid\":\"bc614b12-87b6-4148-8bbe-42b9b22b4655\",\"msg\":{\"from\":{\"id\":\"4bd0eaad5f434b199a9f618231401838\",\"name\":\"测试人员22\"},\"to\":[],\"at\":[],\"content\":\"YxKKK8MeezaWhTYBQomA9Q==\",\"isEncrypt\":true,\"type\":2,\"notShowMe\":false,\"isPhone\":true}},\"ClientId\":\"bc614b12-87b6-4148-8bbe-42b9b22b4655\",\"isPhone\":true}")
//               var  jsonObject = JSONObject("{\"ConnectionId\":\"${request.getString("ConnectionId")}\",\"Body\":{\"mailId\":\"686224b0dbe5d57602345d69\",\"uuid\":\"03dd1e19-0c8a-4ad2-9975-8c58ac1f0779\",\"msg\":{\"from\":{\"id\":\"4bd0eaad5f434b199a9f618231401838\",\"name\":\"测试人员22\"},\"to\":[],\"at\":[],\"content\":\"Fqqfl51Atgy+etZLxAyr7g==\",\"isEncrypt\":true,\"type\":2,\"notShowMe\":false,\"isPhone\":true}},\"ClientId\":\"03dd1e19-0c8a-4ad2-9975-8c58ac1f0779\",\"isPhone\":true}")
//               var  jsonObject = JSONObject("{\"ConnectionId\":\"${request.getString("ConnectionId")}\",\"Body\":{\"mailId\":\"6858b9b6dbe5d57602345372\",\"uuid\":\"9145cf69-de29-4205-be2a-cd37aafa544d\",\"msg\":{\"from\":{\"id\":\"4bd0eaad5f434b199a9f618231401838\",\"name\":\"测试人员22\",\"role\":\"\"},\"to\":[],\"at\":[],\"content\":\"\\/\\/0r8DMvoM4LEW03iYqzUg==\",\"isEncrypt\":true,\"type\":2,\"notShowMe\":false,\"isPhone\":true}},\"ClientId\":\"9145cf69-de29-4205-be2a-cd37aafa544d\",\"isPhone\":true}")
               var  jsonObject = JSONObject("{\"ConnectionId\":\"${request.getString("ConnectionId")}\",\"Body\":{\"mailId\":\"68786b0a92802d75fb77ed9b\",\"uuid\":\"ab23f979-6811-4555-a58c-14c1ac77f0c5\",\"msg\":{\"from\":{\"id\":\"4bd0eaad5f434b199a9f618231401838\",\"name\":\"测试人员22\",\"role\":\"\"},\"to\":[],\"at\":[],\"content\":\"Qz4lGbRIA\\/jzyk1k2vog\\/A==\",\"isEncrypt\":true,\"type\":2,\"notShowMe\":false,\"isPhone\":true}},\"ClientId\":\"ab23f979-6811-4555-a58c-14c1ac77f0c5\",\"isPhone\":true}")
                socket.emit("sendChatMsg",jsonObject)
            }



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
