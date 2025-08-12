@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.iotreboot
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.runBlocking
import java.util.*

fun main (){
    runBlocking {
        val database = setupConnection("test", "MONGO_URI")
        addItems(database)
    }
}

suspend fun setupConnection(
    databaseName: String = "test",
    connectionEnvVariable: String = "MONGO_URI"
): MongoDatabase? {
    val connectString = if (System.getenv(connectionEnvVariable) != null) {
        System.getenv(connectionEnvVariable)
    } else {
        "mongodb+srv://Kipedo000:bhwYMTusgnMqoRxA@cluster0.rpmloin.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
    }
    val client = MongoClient.create(connectionString = connectString)
    val database = client.getDatabase(databaseName = databaseName)
    return database
}


suspend fun listAllCollection(database: MongoDatabase) {

    val count = database.listCollectionNames().count()
    println("Collection count $count")

    print("Collection in this database are ---------------> ")
    database.listCollectionNames().collect { print(" $it") }
}

suspend fun dropCollection(database: MongoDatabase) {
    database.getCollection<Objects>(collectionName = "collectionName").drop()
}