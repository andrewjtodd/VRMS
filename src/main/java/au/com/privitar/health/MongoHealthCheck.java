package au.com.privitar.health;

import au.com.privitar.Constants;
import com.codahale.metrics.health.HealthCheck;
import com.mongodb.DB;

/**
 * Created by andrewtodd on 6/06/2016.
 * Check for the database to see we can get to it.
 */
public class MongoHealthCheck extends HealthCheck {
    private DB db;

    public MongoHealthCheck(final DB db) {
        this.db = db;
    }

    @Override
    protected Result check() {
        try {
            if (db != null && db.getName().equals(Constants.DBNAME)) {
                return Result.healthy();
            }
        } catch (Exception e) {
            // unhealthy service.
        }
        return Result.unhealthy("Database access not OK");
    }
}
