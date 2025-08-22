package com.project

import com.google.common.hash.Hashing.sha256
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
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
        val filter = Filters.eq(username == User::username.name)
        val update = Updates.set(User::username.name, "Sol")
        val options = FindOneAndUpdateOptions().upsert(true)
        val result = users.findOneAndUpdate(/* filter = */ filter, /* update = */
            update, /* options = */
            options)
        val ps : String = sha256("HDesSHippugga70006").toString()
        val ustr = User(ObjectId(), username, password = ps, __v = 0)
        return ustr
    }

    override suspend fun insertUser(user: User): Boolean {
        val d = Document()
        d.values.addAll(arrayOf(user.id, user.username, user.password, user.__v))
        val result = users.insertOne(d)
        return result.wasAcknowledged()
    }
}
