package com.project

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.project.data.User
import io.ktor.server.application.*
import io.ktor.server.config.tryGetString
import io.ktor.server.netty.EngineMain
import org.bson.codecs.pojo.annotations.BsonId

fun main(args: Array<String>) {
    EngineMain.main(args)
}

@Suppress("unused")
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

suspend fun Application.connectToMongoDbII(): MongoDatabase{
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
    val mongoDatabase = MongoClients.create(mongoClientSettings).getDatabase("ktor-auth")

    val des = MongoUserDataSource(mongoDatabase)
    des.insertUser(User("Sol","thisTHing", "idex00099u9"))
    return mongoDatabase
}

