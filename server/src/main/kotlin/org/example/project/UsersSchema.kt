package com.project

import com.project.data.User
import io.ktor.client.plugins.UserAgent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.selects.select
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID
import kotlin.collections.map

// config for exposed UsersSchema
@Serializable
data class ExposedUser(val name: String, val password: String)

class UserService(database: Database) {
    object Users : Table() {
        val id = primaryKey
        val name = varchar("name", length = 50)
        val password =  varchar("password", length = 50)
        val __v = integer("__v", "")

        override val primaryKey: PrimaryKey?
            get() = id
    }

    init {
        transaction(database) {
            SchemaUtils.create(Users)
        }
    }

    suspend fun create(user: ExposedUser) {
        dbQuery {
            Users.upsert {
                it[this.name] = user.name
                it[this.password] = user.password
                it[this.__v] = 0
            }
        }
    }

    suspend fun readAll(): Query {
        var allUsers = Users.selectAll()
        return allUsers
    }

    suspend fun read(id: ObjectId, name: String, password: String): Query {
        val userNew = User(id, name, password, 0)
        val qn = { Users.select(Users.name).where(Users.name eq userNew.username) }
        return qn as Query
    }

    suspend fun update(id: ObjectId?, user: ExposedUser) {
        dbQuery {
            Users.update({ Users.name like id.toString()}) {
                it[name] = user.name
                it[password] = user.password
            }
        }
    }

    suspend fun delete(id: ObjectId?) {
        dbQuery {
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}

