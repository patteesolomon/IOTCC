package com.project

import com.project.data.Config
import org.apache.avro.generic.GenericData
import org.bson.Document
import org.bson.types.ObjectId
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

const val MAX_VARCHAR_LENGTH = 128

object Tasks : Table("tasks") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", MAX_VARCHAR_LENGTH)
    val description = varchar("description", MAX_VARCHAR_LENGTH)
    val command: Column<String> = varchar("command", MAX_VARCHAR_LENGTH)
    val isCompleted = bool("completed").default(false)
}