package com.project

import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

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

    suspend fun create(user: ExposedUser): ObjectId = dbQuery {
//        Users.insert {
//            it[this.name] = user.name
//            it[this.password] = user.password
//        }[id.]
        TODO()
    }

    suspend fun read(id: String?): ExposedUser? {
//        return dbQuery {
//            Users.selectAll()
//                .where { Users.id.equals(id) }
//                .map { ExposedUser(it[Users.name], it[Users.password]) }
//                .singleOrNull()
//        }
        TODO()
    }

    suspend fun update(id: String?, user: ExposedUser) {
//        dbQuery {
//            Users.update({ Users.id.equals(id) }) {
//                it[name] = user.name
//                it[password] = user.password
//            }
//        }
    }

    suspend fun delete(id: String?) {
//        dbQuery {
//            Users.deleteWhere{ Users.id.equals(id) }
//        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}

