package org.example.project
import org.example.project.Command
import org.example.project.CommandService
import com.codahale.metrics.*
import com.fasterxml.jackson.databind.*
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings.builder
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.*
import com.ucasoft.ktor.simpleRedisCache.*
import io.github.flaxoos.ktor.server.plugins.ratelimiter.*
import io.github.flaxoos.ktor.server.plugins.ratelimiter.implementations.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.serialization.jackson.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.html.*
import io.ktor.server.metrics.dropwizard.*
import io.ktor.server.metrics.micrometer.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.forwardedheaders.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.micrometer.prometheus.*
import kotlinx.html.*
import kotlinx.rpc.krpc.serialization.json.*
import com.mongodb.kotlin.client.coroutine.MongoClient
import kotlinx.coroutines.runBlocking
import java.lang.System.getenv

class MongoLoaderClass {
    private var username = "default";
    private var password = "0";
    private var routeId = "";

    constructor () {
        password = getenv("keypwd") ?: "default_value";
        username = getenv("username") ?:"default_value";
        routeId = getenv("userrouteid") ?:"default_value";
    }
    fun main() {
        val user = username
        val password = this.password
        val connectionString = "mongodb+srv://Kipedo000:${password}@cluster0.rpmloin.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
        val host = environment.config.tryGetString("db.mongo.host") ?: "127.0.0.1"
        val port = environment.config.tryGetString("db.mongo.port") ?: "27017"
        val maxPoolSize = environment.config.tryGetString("db.mongo.maxPoolSize")?.toInt() ?: 20
        val databaseName = environment.config.tryGetString("db.mongo.database.name") ?: "myDatabase"
        val credentials = user?.let { userVal -> password?.let { passwordVal -> "$userVal:$passwordVal@" } }.orEmpty()
        val uri = "mongodb://$credentials$host:$port/?maxPoolSize=$maxPoolSize&w=majority"
        val mongoDatabase = connectToMongoDB()
        val CommandService = CommandService(mongoDatabase)
        val serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build()
        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(connectionString))
            .serverApi(serverApi)
            .build()

        //mongo
        MongoClients.create(mongoClientSettings)
        //uri
        val mongoClient = MongoClients.create(uri)
        val database = mongoClient.getDatabase(databaseName)

        monitor.subscribe(ApplicationStopped) {
            mongoClient.close()
        }

        // Create a new client and connect to the server
        MongoClient.create(mongoClientSettings).use { mongoClient ->
            val database = mongoClient.getDatabase("admin")
            runBlocking {
                database.runCommand(Document("ping", 1))
            }
            println("Pinged your deployment. You successfully connected to MongoDB!")
        }

        routing {
            // Create Command
            post("user/${routeId}/Commands") {
                val Command = call.receive<Command>()
                val id = CommandService.create(Command)
                call.respond(HttpStatusCode.Created, id)
            }
            // GET ALL
            get("user/${routeId}/Commands/all"){
                CommandService.collection?.let { Command ->
                    call.respond(Command)
                }
            }
            // Read Command
            get("user/${routeId}/Commands/{id}") {
                val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
                CommandService.read(id)?.let { Command ->
                    call.respond(Command)
                } ?: call.respond(HttpStatusCode.NotFound)
            }
            // Update Command
            put("user/${routeId}/Commands/{id}") {
                val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
                val Command = call.receive<Command>()
                CommandService.update(id, Command)?.let {
                    call.respond(HttpStatusCode.OK)
                } ?: call.respond(HttpStatusCode.NotFound)
            }
            // Delete Command
            delete("user/${routeId}/Commands/{id}") {
                val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
                CommandService.delete(id)?.let {
                    call.respond(HttpStatusCode.OK)
                } ?: call.respond(HttpStatusCode.NotFound)
            }
        }
        //return database

    }
}