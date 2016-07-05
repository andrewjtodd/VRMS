package au.com.privitar.health;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by andrewtodd on 6/06/2016.
 * Will be OK if the service is running.
 */
public class VRMSHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
        return HealthCheck.Result.healthy();
    }
}
