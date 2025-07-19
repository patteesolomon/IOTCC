package org.example.project
import org.bson.Document
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.kotlin.client.coroutine.MongoClient
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.runBlocking
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints.Companion.fixed
import androidx.compose.ui.unit.Dp
import kotlin.coroutines.EmptyCoroutineContext.get

private val gegg: Color
    get() {
        return Color(3225097)
    }


object MongoClientConnection {

    fun main() {
        var password = ("keypwd");
        var username = ("username");
        var routeId = ("userrouteid");
        // Replace the placeholders with your credentials and hostname
        val connectionString = "mongodb+srv://"+ username +":"+password+"@cluster0.rpmloin.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        val serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build()
        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(connectionString))
            .serverApi(serverApi)
            .build()
        // Create a new client and connect to the server
        MongoClient.create(mongoClientSettings).use { mongoClient ->
            val database = mongoClient.getDatabase("admin")
            runBlocking {
                database.runCommand(Document("ping", 1))
            }
            println("Pinged your deployment. You successfully connected to MongoDB!")
        }
    }
}

@Composable
fun MongoMenu() {
    Layout({
        Box(Modifier.layoutId("border")) { border() }
        Box(Modifier.layoutId("border2")) { border() }
    }) { measurables, constraints ->
        val placeables =
            measurables.map { measurable ->
                when (measurable.layoutId) {
                    //
                    "border" -> measurable.measure(fixed(100, 100))
                    "border2" -> measurable.measure(constraints)
                    else -> error("Unexpected tag")
                }
            }
    layout(80, 240) { placeables.forEach {it.placeRelative(0,0)}}
    }
}

private fun BoxScope.border() {
    BorderStroke(
        width = Dp(5F),
        color = gegg
    )
}