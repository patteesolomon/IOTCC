package com.project
import com.project.data.User

interface UserDataSource {
    suspend fun getUserByUsername(username: String): User?
    suspend fun insertUser(user: User, nameToF: String) : Boolean
    suspend fun findUser(user: User, nameToF: String) : Boolean
    suspend fun deleteUser(user: User, nameToF: String) : Boolean
    suspend fun updateUser(user: User, nameToF: String) : Boolean
}