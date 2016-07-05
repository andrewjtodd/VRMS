package au.com.privitar;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

/**
 * Created by andrewtodd on 6/06/2016.
 * Configuration for the application.
 */
public class VRMSConfiguration extends Configuration {
    @JsonProperty
    public MongoConfiguration mongo = new MongoConfiguration();

    public MongoConfiguration getMongoConfig() {
        return mongo;
    }
}