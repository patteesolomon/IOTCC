package com.example.iotcc

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.runBlocking
import org.bson.BsonType
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.codecs.pojo.annotations.BsonRepresentation
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory
import java.lang.System.getenv

val CONNECTION_STRING = getenv("MONGO_URI")

    fun main(args: Array<String>){
        val database = getDatabase()
        runBlocking {
            println(database)
        }
    }
    fun getDatabase(): MongoDatabase{
        val client = MongoClient.create(connectionString = CONNECTION_STRING)
        return client.getDatabase(databaseName = "test")
    }

data class CommandInfo(
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    @BsonProperty("type")
    val command: String
)

suspend fun addCommand(database: MongoDatabase){
    val info = CommandInfo(
        command = "sudo apt full-upgrade",
    )
    val collection = database.getCollection<CommandInfo>("commands")
    collection.insertOne(info).also {
        println("Inserted Id - ${it.insertedId}")
    }
}