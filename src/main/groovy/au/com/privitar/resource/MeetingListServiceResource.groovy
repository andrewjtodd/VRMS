package au.com.privitar.resource

import au.com.privitar.domain.Meeting
import au.com.privitar.domain.Vendor
import com.codahale.metrics.annotation.Timed
import com.mongodb.DB
import com.mongodb.DBCollection
import org.mongojack.DBQuery
import org.mongojack.JacksonDBCollection
import org.mongojack.WriteResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 * Created by andrewtodd on 5/07/2016.
 * For a list of meetings and other 'bulk' items.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Timed
@Path("/vendors/{vendor_name}/meetings")
class MeetingListServiceResource extends ServiceBase {
    private static final Logger logger = LoggerFactory.getLogger(MeetingListServiceResource.class);

    MeetingListServiceResource(DB database) {
        super(database)
    }

    @GET
    public List<Meeting> getMeetings(@PathParam("vendor_name") String name) {
        JacksonDBCollection<Meeting, String> jacksonDBCollection = getCollection();
        logger.printf("Looking for meetings for vendor %s\n", name)
        List<Meeting> meetings = jacksonDBCollection.find(DBQuery.is('vendorName', name)).toArray()
        logger.info("Items in the collection are: " + jacksonDBCollection.count())

        return meetings
    }

    @POST
    public Meeting addMeeting(@PathParam("vendor_name") String name, Meeting meeting) {
        logger.printf("Call to save %s\n", meeting.toString())
        JacksonDBCollection<Meeting, String> jacksonDBCollection = getCollection();

        WriteResult<Meeting, String> result = jacksonDBCollection.insert(meeting)
        checkDBWrite(result)
        Meeting m = (Meeting) result.getSavedObject();
        logger.info("Returning saved meeting: " + m.toString());
        return m
    }

    JacksonDBCollection<Vendor, String> getCollection() {
        DBCollection collection = getDatabase().getCollection(Meeting.COLLECTION_NAME);
        JacksonDBCollection<Vendor, String> jacksonDBCollection = JacksonDBCollection.wrap(collection, Meeting.class, String.class);
        return jacksonDBCollection;
    }
}