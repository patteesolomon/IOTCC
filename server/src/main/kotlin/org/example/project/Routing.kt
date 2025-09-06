package com.project

import com.mongodb.client.*
import com.project.data.User
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.types.ObjectId

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/iotDevices")
        {
        }

        get("/robotics")
        {
            // get all robots here
            testInsertDoc(connectToMongoDB(), User(ObjectId(), "Sol", "ION", 0))
        }

        post("/users"){

        }

        post("/configSet")
        {
            // finish config and post to settings here
        }

        put("/newIOT"){
            // new IOT controllable
        }
        put("/user/:id"){
//            updateDoc()
        }
        delete("/iotDevices/:id"){
            // deletes the iot dev
        }
    }
}

// TODO: YOU HAVE NO CALLING CLASS
// SEND DATA FROM THE FRONTEND HERE!
suspend fun updateDoc(mdb: MongoDatabase, user :User){
    val userUpdate = MongoUserDataSource(mdb)
    // Update'
    userUpdate.updateUser(user)
}

suspend fun insertDoc(mdb : MongoDatabase, user : User) {
    val userInserter = MongoUserDataSource(mdb)
    userInserter.insertUser(
        user
    )
}

suspend fun testInsertDoc(mdb: MongoDatabase, user: User){
    insertDoc(mdb, user)
}