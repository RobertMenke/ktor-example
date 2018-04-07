package app

import io.ktor.application.*
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.websocket.WebSockets
import session.AuthenticatedSession
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
    //https://ktor.io/features/routing.html
    install(Routing) {
        get("/") {
            call.respondText("My Example lol", ContentType.Text.Html)
        }
    }
}