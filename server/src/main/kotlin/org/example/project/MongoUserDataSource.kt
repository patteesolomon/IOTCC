package com.project

import com.google.common.hash.Hashing.sha256
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.Updates
import com.project.data.User
import org.apache.commons.codec.digest.DigestUtils.sha256
import org.bson.Document
import org.bson.types.ObjectId

class MongoUserDataSource(
    db: MongoDatabase
):
    UserDataSource {
       var users: MongoCollection<Document?> = db.getCollection("users")
    override suspend fun getUserByUsername(username: String): User? {
        val filter = eq(username == User::username.name)
        val update = Updates.set(User::username.name, "Sol")
        val options = FindOneAndUpdateOptions().upsert(true)
        users.findOneAndUpdate(/* filter = */ filter, /* update = */
            update, /* options = */
            options)
        val ps : String = sha256("HDesSHippugga70006").toString()
        val ustr = User(ObjectId(), username, password = ps, __v = 0)
        return ustr
    }


    // create
    override suspend fun insertUser(user: User, nameToF: String): Boolean {
        val d = Document()
        d.values.addAll(arrayOf(user.id, user.username, user.password, user.__v))
        val result = users.insertOne(d)
        return result.wasAcknowledged()
    }

    // get
    // need to make more overrides
    override suspend fun findUser(user: User, nameToF: String): Boolean {
        val results = users.find(eq(user::username.name, nameToF))

        results.forEach { result ->
            println(result)
        }
        var rBool = if (results != null) true else false
        return rBool
    }

    // delete
    override suspend fun deleteUser(user: User, nameToF: String): Boolean{
        val results = users.findOneAndDelete(eq(user::username.name, nameToF))
        results?.forEach { result ->
            println(result)
        }
        var rBool = if (results != null) true else false
        return rBool
    }

    // update
    override suspend fun updateUser(user: User, nameToF: String): Boolean {
        val d = Document()
        d.values.addAll(arrayOf(user.id, user.username, user.password, user.__v))
        val results = users.findOneAndUpdate(eq(user::username.name, nameToF), d)
        results?.forEach { result ->
            println(result)
        }
        var rBool = if (results != null) true else false
        return rBool
    }
}
