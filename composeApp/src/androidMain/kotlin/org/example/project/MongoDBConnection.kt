package org.project.iotcc
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.runBlocking
import io.github.cdimascio.dotenv.dotenv
import org.bson.Document
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.kotlin.client.coroutine.MongoClient
import kotlinx.coroutines.runBlocking
class MongoDBConnection {
    fun main() {
        val dotenv = dotenv {
            directory = "./"
            ignoreIfMissing = true
            ignoreIfMalformed = true
        }
        val username = dotenv["USERNAME"]
        val password = dotenv ["KEYPWD" ]
        val RID = dotenv ["USERROUTEID" ]
        val CONNECTION_STRING = "mongodb+srv://$username:$password@cluster0.rpmloin.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
        val serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build()
        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(CONNECTION_STRING))
            .serverApi(serverApi)
            .build()
        // Create a new client and connect to the server
        MongoClient.create(mongoClientSettings).use { mongoClient ->
            val database = mongoClient.getDatabase("admin")
            runBlocking {
                database.runCommand(Document("ping", 1))
            }
            println("Pinged your deployment. You successfully connected to MongoDB!")
        }
    }
}