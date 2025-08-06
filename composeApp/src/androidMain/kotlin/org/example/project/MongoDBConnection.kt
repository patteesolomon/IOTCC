package org.project.iotcc

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.bson.Document

object MongoDBConnection {
    val document = Document()
    private val username = "Kipedo000"
    private val password = "bhwYMTusgnMqoRxA"
    private val CONNECTION_STRING = "mongodb+srv://$username:$password@cluster0.rpmloin.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
    val mongoClient = getMongoClientcl()
    val database = mongoClient.getDatabase("Cluster0")
    val collection = this.database.getCollection("your_collection_name")

    fun getMongoClientcl(): MongoClient {
        return MongoClients.create(CONNECTION_STRING)
    }
}