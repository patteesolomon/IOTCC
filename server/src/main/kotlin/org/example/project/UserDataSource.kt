package com.project

import org.h2.engine.User

interface UserDataSource {
    suspend fun getUserByUsername(username: String): User?
    suspend fun insertUser(user: User) : Boolean
}