package com.project

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import com.mongodb.reactivestreams.client.MongoClients
import com.project.data.User
import io.ktor.server.application.*
import io.ktor.server.config.tryGetString
import io.ktor.server.netty.EngineMain
import kotlinx.coroutines.runBlocking
import org.bson.Document
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.conversions.Bson
import org.bson.types.ObjectId

/*
Set up MongoDB instance with = Docker NOT yet
Connect with MongoDB ATLAS = done
Create Ktor project  = done
 Import & configure IntelliJ = done
 MongoDB async driver = done
 Prepare models = mostly done
 Connect Ktor with MongoDB
Save documents
Find documents by ID
Update documents
Delete documents
Find documents with sort & pagination
* */

fun main(args: Array<String>) {
    EngineMain.main(args)

}

@Suppress("unused")
fun Application.module() {
    val userS = User(ObjectId(), "Sol", "efafef", __v = 0);
    runBlocking {
    }
    configureSerialization()
    configureFrameworks()
    configureAdministration()
    configureTemplating()
    configureDatabases()
    configureHTTP()
    configureSecurity()
    configureRouting()
}

