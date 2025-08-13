package com.project 

import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import java.sql.Connection
import java.sql.Statement

@Serializable
data class Command(val name: String, val commandS: String)
class CommandService(private val connection: Connection) {
    companion object {
        private const val CREATE_TABLE_COMMANDS = "CREATE TABLE COMMANDS (ID SERIAL PRIMARY KEY, NAME VARCHAR(255), commandS INT);"
        private const val SELECT_COMMAND_BY_ID = "SELECT name, commandS FROM COMMANDS WHERE id = ?"
        private const val INSERT_COMMAND = "INSERT INTO COMMANDS (name, commandS) VALUES (?, ?)"
        private const val UPDATE_COMMAND = "UPDATE COMMANDS SET name = ?, commandS = ? WHERE id = ?"
        private const val DELETE_COMMAND = "DELETE FROM COMMANDS WHERE id = ?" 
    }

    init {
        val statement = connection.createStatement()
        statement.executeUpdate(CREATE_TABLE_COMMANDS)
    }

    private var newCommandId = 0

    // Create new Command
    suspend fun create(Command: Command): Int = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(INSERT_COMMAND, Statement.RETURN_GENERATED_KEYS)
        statement.setString(1, Command.name)
        statement.setString(2, Command.commandS)
        statement.executeUpdate()

        val generatedKeys = statement.generatedKeys
        if (generatedKeys.next()) {
            return@withContext generatedKeys.getInt(1)
        } else {
            throw Exception("Unable to retrieve the id of the newly inserted Command")
        }
    }

    // Read a Command
    suspend fun read(id: Int): Command = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(SELECT_COMMAND_BY_ID)
        statement.setInt(1, id)
        val resultSet = statement.executeQuery()

        if (resultSet.next()) {
            val name = resultSet.getString("name")
            val commandS = resultSet.getString("commandS")
            return@withContext Command(name, commandS)
        } else {
            throw Exception("Record not found")
        }
    }

    // Update a Command
    suspend fun update(id: Int, Command: Command) = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(UPDATE_COMMAND)
        statement.setString(1, Command.name)
        statement.setString(2, Command.commandS)
        statement.setInt(3, id)
        statement.executeUpdate()
    }

    // Delete a Command
    suspend fun delete(id: Int) = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(DELETE_COMMAND)
        statement.setInt(1, id)
        statement.executeUpdate()
    }
}

