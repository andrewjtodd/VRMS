package au.com.privitar.services

import au.com.privitar.domain.HealthScore
import au.com.privitar.domain.Vendor
import au.com.privitar.resource.ServiceBase
import au.com.privitar.resource.VendorServiceResource
import com.codahale.metrics.annotation.Timed
import com.mongodb.DB
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * Created by andrewtodd on 12/06/2016.
 * This class calculates the health scores for meetings as required.
 * This can be through a specific call to recalculate, or dynamically as meeting
 * scores are entered by meeting participants.
 */
@Path("/vendors/{vendor_name}")
class HealthCalculatorService extends ServiceBase {
    private static Logger logger = LoggerFactory.getLogger(HealthCalculatorService.class);

    HealthCalculatorService(DB database) {
        super(database)
    }

    @POST
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/calculate/{score}")
    Vendor calculateVendorHealth(@PathParam("vendor_name") String name, @PathParam("score") String score) {
        logger.info("Calculating score for " + name);

        float lscore = Float.valueOf(score);
        lscore = lscore * Math.random()

        VendorServiceResource vendorServiceResource = new VendorServiceResource(database);
        Vendor v = vendorServiceResource.getVendor(name);
        // Get the meetings associated to the vendor.
        HealthScore hs = new HealthScore(new Date(), lscore)
        v.getScores().add(hs);
        vendorServiceResource.update(v);

        return v;
    }
}
