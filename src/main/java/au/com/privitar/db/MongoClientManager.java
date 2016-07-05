package au.com.privitar.db;

import com.mongodb.MongoClient;
import io.dropwizard.lifecycle.Managed;

/**
 * Created by andrewtodd on 6/06/2016.
 * Client manager for mongo.
 */
public class MongoClientManager implements Managed {
    private final MongoClient mongoClient;

    public MongoClientManager(final MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {
        mongoClient.close();
    }
}