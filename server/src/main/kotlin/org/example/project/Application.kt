package com.project

import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureFrameworks()
    configureAdministration()
    configureTemplating()
    configureDatabases()
    configureHTTP()
    configureSecurity()
    configureRouting()
}
