package sockets

import com.corundumstudio.socketio.Configuration
import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.listener.DataListener

class SecureSocketServer {

    private val config = getConfiguration()
    private val server = SocketIOServer(config)

    init {
        server.start()
    }

    fun <T>addEvent(eventName : String, serializeTo : Class<T>, listener : DataListener<T>) {
        server.addEventListener(eventName, serializeTo, listener)
    }

    private fun getConfiguration() : Configuration {
        val config = Configuration()
        config.hostname = "localhost"
        config.port = 2323
        config.keyStorePassword = "testing"
        config.pingTimeout = 60
        config.pingInterval = 60
        config.keyStore = SecureSocketServer::class.java.getResourceAsStream("/jks/selfsigned.jks")

        return config
    }
}
