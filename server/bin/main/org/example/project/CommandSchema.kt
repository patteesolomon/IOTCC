package org.example.project

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.bson.Document
import org.bson.types.ObjectId

@Serializable
data class Command(
    val Name: String,
    val Code: String
) {
    fun toDocument(): Document = Document.parse(Json.encodeToString(this))

    companion object {
        private val json = Json { ignoreUnknownKeys = true }

        fun fromDocument(document: Document): Command = json.decodeFromString(document.toJson())
    }
}

class CommandService(private val database: MongoDatabase) {
    var collection: MongoCollection<Document>

    init {
        database.createCollection("Commands")
        collection = database.getCollection("Commands")
    }

    // Create new Command
    suspend fun create(Command: Command): String = withContext(Dispatchers.IO) {
        val doc = Command.toDocument()
        collection.insertOne(doc)
        doc["_id"].toString()
    }

    // Read a Command
    suspend fun read(id: String): Command? = withContext(Dispatchers.IO) {
        collection.find(Filters.eq("_id", ObjectId(id))).first()?.let(Command::fromDocument)
    }

    // Update a Command
    suspend fun update(id: String, Command: Command): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndReplace(Filters.eq("_id", ObjectId(id)), Command.toDocument())
    }

    // Delete a Command
    suspend fun delete(id: String): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndDelete(Filters.eq("_id", ObjectId(id)))
    }
}

