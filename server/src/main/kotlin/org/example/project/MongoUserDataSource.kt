package com.project

import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.project.data.User

class MongoUserDataSource(
    db: MongoDatabase
):
    UserDataSource {
    override suspend fun getUserByUsername(username: String): User? {
        TODO("Not yet implemented")
    }

    override suspend fun insertUser(user: User): Boolean {
        TODO("Not yet implemented")
    }
}