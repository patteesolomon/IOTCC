package com.project

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import io.ktor.server.application.*
import io.ktor.server.config.tryGetString
import kotlinx.coroutines.runBlocking
import org.bson.Document

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
    val mongoPw = System.getenv("db.mongo.password")
    val databaseName = environment.config.tryGetString("db.mongo.database.name") ?: "myDatabase"

    val connectionString = "mongodb+srv://${System.getenv("db.mongo.username")}:${mongoPw}@cluster0.rpmloin.mongodb.net/ktor-auth?retryWrites=true&w=majority&appName=Cluster0"

    val serverApi = ServerApi.builder()
        .version(ServerApiVersion.V1)
        .build()
    val mongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(connectionString))
        .serverApi(serverApi)
        .build()
    // Create a new client and connect to the server
    val mongoClient = MongoClients.create(mongoClientSettings).getDatabase("ktor-auth")
    val database = mongoClient
//    val userDataSource = MongoUserDataSource(database)
    configureSerialization()
    configureFrameworks()
    configureAdministration()
    configureTemplating()
    configureDatabases()
    configureHTTP()
    configureSecurity()
    configureRouting()
}
