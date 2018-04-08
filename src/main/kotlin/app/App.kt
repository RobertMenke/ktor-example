package app

import com.corundumstudio.socketio.listener.DataListener
import freemarker.cache.ClassTemplateLoader
import freemarker.cache.FileTemplateLoader
import io.ktor.application.*
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.sessions.sessions
import io.ktor.util.moveToByteArray
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSockets
import io.ktor.websocket.readText
import io.ktor.websocket.webSocket
import models.Location
import session.AuthenticatedSession
import sockets.SecureSocketServer
import java.time.Duration

fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)
    //https://ktor.io/features/websockets.html
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(60) // Disabled (null) by default
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE // Disabled (max value). The connection will be closed if surpassed this length.
        masking = false
    }

    //https://ktor.io/features/sessions.html#install-basic
    install(Sessions) {
        cookie<AuthenticatedSession>(AuthenticatedSession.NAME)
    }
    //https://ktor.io/features/freemarker.html
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(Application::class.java.classLoader, "templates")
    }
    //https://ktor.io/features/routing.html
    install(Routing) {
        get("/") {
            call.respond(FreeMarkerContent("index.ftl", mapOf("test_name" to "World")))
        }
    }

    val server = SecureSocketServer()
    server.addEvent("location-in", Location::class.java, DataListener { client, data, ackRequest ->
        log.info(data.toString())
    })
}

