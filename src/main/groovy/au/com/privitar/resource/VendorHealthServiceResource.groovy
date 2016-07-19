package au.com.privitar.resource

import au.com.privitar.domain.CurrentScore
import au.com.privitar.domain.Meeting
import au.com.privitar.domain.Person
import au.com.privitar.domain.Vendor
import com.codahale.metrics.annotation.Timed
import com.mongodb.DB
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 * Created by andrewtodd on 19/07/2016.
 * This is the service resource for vendor health. as there is a single endpoint to get the response, it will also
 * provide an aggregated view of meetings and staff in the result.
 */


@Path("/vendors/{vendor_name}/health")
@Timed
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class VendorHealthServiceResource extends ServiceBase {
    private static final Logger logger = LoggerFactory.getLogger(VendorHealthServiceResource.class);
    private VendorServiceResource vendorServiceResource;
    private VendorEmployeeResourceService vendorEmployeeResourceService;
    private MeetingListServiceResource meetingListServiceResource


    VendorHealthServiceResource(DB database) {
        super(database)

        // get an instance of the vendorServiceResource.
        vendorServiceResource = new VendorServiceResource(database)
        vendorEmployeeResourceService = new VendorEmployeeResourceService(database)
        meetingListServiceResource = new MeetingListServiceResource(database)

    }

    @GET
    Vendor getVendorHealth(@PathParam("vendor_name") String name) {

        logger.printf("------\nCalculating vendor health\n--------\n");

        Vendor v = vendorServiceResource.getVendor(name)
        List<Meeting> meetings = meetingListServiceResource.getMeetings(name)
        List<Person> people = vendorEmployeeResourceService.getEmployees(name)
        CurrentScore score = v.calculateScore(meetings, people)
        v.currentScore = score
        return v
    }
}
