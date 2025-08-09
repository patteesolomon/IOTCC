package com.example.iotcc

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.runBlocking
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory

val CONNECTION_STRING = System.getenv("MONGO_URI")

    fun main(args: Array<String>){
        val database: MongoDatabase = getDatabase()
        val loggerParent = LoggerFactory.getLogger("parent")
        val loggerChild = LoggerFactory.getLogger("parent.child")
        runBlocking { addCommand(database) }
    }
    fun getDatabase(): MongoDatabase{
        val client = MongoClient.create(connectionString = CONNECTION_STRING)
        return client.getDatabase(databaseName = "test")
    }

data class CommandInfo(
    @BsonId
    val id: ObjectId,
    val command: String
)

suspend fun addCommand(database: MongoDatabase){
    val info = CommandInfo(
        id = ObjectId(),
        command = "sudo apt full-upgrade",
    )
    val collection = database.getCollection<CommandInfo>("commands")
    collection.insertOne(info).also {
        println("Inserted Id - ${it.insertedId}")
    }
}