package com.project

import com.mongodb.client.*
import com.project.data.AIA
import com.project.data.BT
import com.project.data.CSA
import com.project.data.Config
import com.project.data.EmulationM
import com.project.data.InstallType
import com.project.data.NetRenderPod
import com.project.data.StartUpExe
import com.project.data.SystemMonitor
import com.project.data.User
import io.github.flaxoos.ktor.server.plugins.kafka.Kafka
import io.github.flaxoos.ktor.server.plugins.kafka.MessageTimestampType
import io.github.flaxoos.ktor.server.plugins.kafka.TopicName
import io.github.flaxoos.ktor.server.plugins.kafka.admin
import io.github.flaxoos.ktor.server.plugins.kafka.common
import io.github.flaxoos.ktor.server.plugins.kafka.consumer
import io.github.flaxoos.ktor.server.plugins.kafka.consumerConfig
import io.github.flaxoos.ktor.server.plugins.kafka.consumerRecordHandler
import io.github.flaxoos.ktor.server.plugins.kafka.producer
import io.github.flaxoos.ktor.server.plugins.kafka.registerSchemas
import io.github.flaxoos.ktor.server.plugins.kafka.topic
import io.ktor.client.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.Document
import org.bson.types.ObjectId
import java.sql.Connection
import java.sql.DriverManager
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.DriverManager.println
import kotlin.io.println

fun Application.configureDatabases() {
    val dbConnection: Connection = connectToPostgres(embedded = true)
    val commandService = CommandService(dbConnection)

    routing {

        // Create command
        post("/commands") {
            val command = call.receive<Command>()
            val id = commandService.create(command)
            call.respond(HttpStatusCode.Created, id)
        }

        // Read command
        get("/commands/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            try {
                val command = commandService.read(id)
                call.respond(HttpStatusCode.OK, command)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        // Update command
        put("/commands/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            val user = call.receive<Command>()
            commandService.update(id, user)
            call.respond(HttpStatusCode.OK)
        }

        // Delete command
        delete("/commands/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            commandService.delete(id)
            call.respond(HttpStatusCode.OK)
        }
    }
    val mongoDatabase = connectToMongoDB()

    install(Kafka) {
        schemaRegistryUrl = "my.schemaRegistryUrl"
        val myTopic = TopicName.named("my-topic")
        topic(myTopic) {
            partitions = 1
            replicas = 1
            configs {
                messageTimestampType = MessageTimestampType.CreateTime
            }
        }
        common { // <-- Define common properties
            bootstrapServers = listOf("my-kafka")
            retries = 1
            clientId = "my-client-id"
        }
        admin { } // <-- Creates an admin client
        producer { // <-- Creates a producer
            clientId = "my-client-id"
        }
        consumer { // <-- Creates a consumer
            groupId = "my-group-id"
            clientId = "my-client-id-override" //<-- Override common properties
        }
        consumerConfig {
            consumerRecordHandler(myTopic) { record ->
                // Do something with record
            }
        }
        registerSchemas {
            using { // <-- optionally provide a client, by default CIO is used
                HttpClient()
            }
            // MyRecord::class at myTopic // <-- Will register schema upon startup
        }
    }
    Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAy=-1", driver = "org.h2.Driver")

    val database = Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = "",
    )
    val userService = UserService(database)
    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(Tasks)
        val taskId = Tasks.insert {
            it[name] = "Yuya"
            it[description] = "Friends are cool."
            it[command] = "default"
            it[isCompleted] = false
        } get Tasks.id

        println("Created new tasks with ids $taskId.")

        Tasks.select(Tasks.id.count(), Tasks.isCompleted).groupBy(Tasks.isCompleted).forEach {
            println("${it[Tasks.isCompleted]}: ${it[Tasks.id.count()]} ")
        }
        println("Remaining tasks: ${Tasks.selectAll().toList()}")
    }
    routing {
        // Create user
        post("/users") {
            val user = call.receive<ExposedUser>()
            val id = userService.create(user)
            call.respond(HttpStatusCode.Created, id)
        }
        
        // Read user
        get("/users/{id}") {
            val id: String? = (call.parameters["id"])
            val user = userService.read(id)
            if (user != null) {
                call.respond(HttpStatusCode.OK, user)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
        
        // Update user
        put("/users/{id}") {
            val id: String? = call.parameters["id"]
            val user = call.receive<ExposedUser>()
            userService.update(id, user)
            call.respond(HttpStatusCode.OK)
        }
        
        // Delete user
        delete("/users/{id}") {
            val id = call.parameters["id"]
            userService.delete(id)
            call.respond(HttpStatusCode.OK)
        }
    }
}
/**
 * Makes a connection to a Postgres database.
 *
 * In order to connect to your running Postgres process,
 * please specify the following parameters in your configuration file:
 * - postgres.url -- Url of your running database process.
 * - postgres.user -- Username for database connection
 * - postgres.password -- Password for database connection
 *
 * If you don't have a database process running yet, you may need to [download]((https://www.postgresql.org/download/))
 * and install Postgres and follow the instructions [here](https://postgresapp.com/).
 * Then, you would be able to edit your url,  which is usually "jdbc:postgresql://host:port/database", as well as
 * user and password values.
 *
 *
 * @param embedded -- if [true] defaults to an embedded database for tests that runs locally in the same process.
 * In this case you don't have to provide any parameters in configuration file, and you don't have to run a process.
 *
 * @return [Connection] that represent connection to the database. Please, don't forget to close this connection when
 * your application shuts down by calling [Connection.close]
 * */
fun Application.connectToPostgres(embedded: Boolean): Connection {
    Class.forName("org.postgresql.Driver")
    if (embedded) {
        log.info("Using embedded H2 database for testing; replace this flag to use postgres")
        return DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "root", "")
    } else {
        val url = environment.config.property("postgres.url").getString()
        log.info("Connecting to postgres database at $url")
        val user = environment.config.property("postgres.user").getString()
        val password = environment.config.property("postgres.password").getString()

        return DriverManager.getConnection(url, user, password)
    }
}

/**
 * Establishes connection with a MongoDB database.
 *
 * The following configuration properties (in application.yaml/application.conf) can be specified:
 * * `db.mongo.user` username for your database
 * * `db.mongo.password` password for the user
 * * `db.mongo.host` host that will be used for the database connection
 * * `db.mongo.port` port that will be used for the database connection
 * * `db.mongo.maxPoolSize` maximum number of connections to a MongoDB server
 * * `db.mongo.database.name` name of the database
 *
 * IMPORTANT NOTE: in order to make MongoDB connection working, you have to start a MongoDB server first.
 * See the instructions here: https://www.mongodb.com/docs/manual/administration/install-community/
 * all the paramaters above
 *
 * @returns [MongoDatabase] instance
 * */

fun Application.connectToMongoDB(): MongoDatabase {
    val user = environment.config.tryGetString("db.mongo.user")
    val password = environment.config.tryGetString("db.mongo.password")
    val host = environment.config.tryGetString("db.mongo.host") ?: "127.0.0.1"
    val port = environment.config.tryGetString("db.mongo.port") ?: "27017"
    val maxPoolSize = environment.config.tryGetString("db.mongo.maxPoolSize")?.toInt() ?: 20
    val databaseName = environment.config.tryGetString("db.mongo.database.name") ?: "myDatabase"

    val credentials = user?.let { userVal -> password?.let{ passwordVal -> "$userVal:$passwordVal@" } }.orEmpty()
    val uri = "mongodb://$credentials$host:$port/?maxPoolSize=$maxPoolSize&w=majority"
    val mongoClient = MongoClients.create(/* connectionString = */ uri)
    val database = mongoClient.getDatabase(databaseName)

    monitor.subscribe(ApplicationStopped) {
        mongoClient.close()
    }
    return database
}

