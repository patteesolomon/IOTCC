package com.project

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineStart
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.Serializable
import org.h2.engine.User
import org.jetbrains.exposed.sql.idParam

class MongoUserDataSource(
    db: MongoDatabase
):
    UserDataSource {
        val users = db.getCollection<User>("users")
    suspend fun getAllUsers(): List<User> {
        return users.find().toList()
    }

    override suspend fun getUserByUsername(username: String): User? {
        TODO("Not yet implemented")
    }

    override suspend fun insertUser(user: User): Boolean {
        return users.insertOne(user).wasAcknowledged()
    }
}