package com.example.iotcc

import com.google.android.gms.dtdi.analytics.Results
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCompressor
import com.mongodb.client.model.Accumulators
import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Filters.lt
import com.mongodb.client.model.Projections
import com.mongodb.client.model.Sorts
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.bson.BsonType
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.codecs.pojo.annotations.BsonRepresentation
import org.bson.types.ObjectId
import java.lang.System.getenv

val CONNECTION_STRING = getenv("MONGO_URI")
    fun main(args: Array<String>){
        val database = getDatabase()
        runBlocking {
            getAllCommands(database)
        }
    }
    fun getDatabase(): MongoDatabase{
        val settings = MongoClientSettings.builder()
        .applyConnectionString(ConnectionString(CONNECTION_STRING))
        .compressorList(
            listOf(
                MongoCompressor.createSnappyCompressor(),
                MongoCompressor.createZlibCompressor(),
                MongoCompressor.createZstdCompressor())
        )
        .build()
        val client = MongoClient.create(settings)
        return client.getDatabase(databaseName = "test")
    }

data class CommandInfo(
    @BsonId val id: ObjectId,
    val command: String
)

suspend fun getAllCommands (database: MongoDatabase) {
    val collection = database.getCollection<CommandInfo>(
        collectionName = "commands"
    )
    val projectionFields= Projections.fields(
        Projections.include(CommandInfo::command.name),
        Projections.excludeId()
    )
    val resultsFlow = collection.withDocumentClass<Results>()
        .find(eq(CommandInfo::command.name, "sudo apt update -y"))
        .projection(projectionFields)
        .sort(Sorts.descending(CommandInfo::command.name))
        .firstOrNull()
    if (resultsFlow == null) {
        println("No results found.");
    } else {
        println(resultsFlow)
    }

}

suspend fun addCommand(database: MongoDatabase){
    val info = CommandInfo(
        id = ObjectId.get(),
        command = "sudo apt full-upgrade"
    )
    val collection = database.getCollection<CommandInfo>("commands")
    collection.insertOne(info).also {
        println("Inserted Id - ${it.insertedId}")
    }
}