package com.example.iotcc

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.runBlocking
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

val CONNECTION_STRING = "mongodb+srv://Kipedo000:bhwYMTusgnMqoRxA@cluster0.rpmloin.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"

    fun main(args: Array<String>){
        val database: MongoDatabase = getDatabase()
        runBlocking {
            addCommand(database)
        }
    }
    fun getDatabase(): MongoDatabase{
        val client = MongoClient.create(connectionString = CONNECTION_STRING)
        return client.getDatabase(databaseName = "commands")
    }

data class CommandInfo(
    @BsonId
    val id: ObjectId,
    val string: String
)

suspend fun addCommand(database: MongoDatabase){
    val info = CommandInfo(
        id = ObjectId(),
        string = "sudo apt full-upgrade",
    )
    val collection = database.getCollection<CommandInfo>("commands")
    collection.insertOne(info).also {
        println("Inserted Id -  ${it.insertedId}")
    }
}