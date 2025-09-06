package com.project

import com.project.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

// config for exposed UsersSchema
@Serializable
data class ExposedUser(val name: String, val password: String, val v: Int)

class UserService(database: Database) {
    object Users : Table() {
        val id = primaryKey
        val name = varchar("name", length = 50)
        val password =  varchar("password", length = 50)
        val v = integer("__v", "")

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
                it[this.v] = user.v
            }
        }
    }

    fun readAll(): Query {
        return Users.selectAll()
    }

    fun read(name: String?, password: String?): Query {
        val userNew = User(id = ObjectId(),name as String, password as String, __v = 0)
        val qn = { Users.select(Users.name).where(Users.name eq userNew.username.toString()) }
        return qn as Query
    }

    suspend fun update(user: User) {
        dbQuery {
            val qn = { Users.select(Users.name).where(Users.name eq user.username.toString()) }
            Users.update(1) { qn }
        }
    }

    fun delete(username: String?, ps: String?) {
        val userNew = User(id = ObjectId(),username as String, ps as String, __v = 0)
        Users.deleteWhere(1) { Users.name eq userNew.username.toString() }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}

