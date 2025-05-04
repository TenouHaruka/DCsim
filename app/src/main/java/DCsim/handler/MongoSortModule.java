package app.src.main.java.DCsim.handler;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.*;
public class MongoSortModule {
    public static void main(String[] args) {
        // Replace the uri string with your MongoDB deployment's connection string
        String uri = "<connection string uri>";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");
            // Create a filter to find movies with a runtime less than 15 minutes
            Bson filter = lt("runtime", 15);
            // Retrieve and print the filtered documents
            for (Document doc : collection.find(filter)) {
                System.out.println(doc.toJson());
            }
        }
    }
}