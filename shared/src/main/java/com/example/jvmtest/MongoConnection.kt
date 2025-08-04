package com.example.jvmtest

import com.mongodb.MongoException
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import org.bson.BsonInt64
import org.bson.Document
import java.lang.System.getenv

class MongoConnection {
    suspend fun run(databaseName: String = "sample_restaurants", connectionEnvVariable: String = "MONGODB_URI"): MongoDatabase? {
        val password = System.getenv("keypwd")
        val username = System.getenv("username")
        var dbName : String = "Cluster0"
        var connectionVar : String = if (getenv(connectionEnvVariable) != null){
            getenv(connectionEnvVariable)
        } else {
            "mongodb+srv://$username:$password@cluster0.rpmloin.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
        }
        val client = MongoClient.create(connectionString = connectionVar)
        val database = client.getDatabase(databaseName = databaseName)

        return try {
            // Send a ping to confirm a successful connection
            val command = Document("ping", BsonInt64(1))
            database.runCommand(command)
            println("Pinged your deployment. You successfully connected to MongoDB!")
            database
        } catch (me: MongoException) {
            System.err.println(me)
            null
        }
    }
}