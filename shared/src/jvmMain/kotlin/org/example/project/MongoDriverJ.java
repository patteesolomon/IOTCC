package org.example.project;
import static java.lang.System.console;
import static java.lang.System.getenv;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.kotlinx.BsonNamingStrategy;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.kotlinx.BsonConfiguration;
import org.bson.codecs.kotlinx.KotlinSerializerCodec;

import org.bson.Document;

public class MongoDriverJ {
    private String username = "default";
    private String password = "0";
    private String routeId = "";
    MongoDriverJ(){
        console().printf("loading mongo");
    }
    MongoDriverJ(String u, String p, String r){
        console().printf("loading mongo");
        username = u;
        password = p;
        routeId = r;
    }

    public void start() {
        password = getenv("keypwd");
        username = getenv("username");
        routeId = getenv("userrouteid");
        String connectionString = "mongodb+srv://"+ username +":"+password+"@cluster0.rpmloin.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase("admin");
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
            } catch (MongoException e) {
                console().printf("error: " + e);
            }
        }
    }
}
