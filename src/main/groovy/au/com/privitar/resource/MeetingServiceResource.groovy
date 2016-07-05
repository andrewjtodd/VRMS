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
 * Created by andrewtodd on 9/06/2016.
 * Does things related to the meetings.
 */
@Path("/vendor/{vendor_name}/meeting")
@Timed
class MeetingServiceResource extends ServiceBase {
    private static final Logger logger = LoggerFactory.getLogger(MeetingServiceResource.class);

    public MeetingServiceResource(DB database) {
        super(database);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Meeting addMeeting(Meeting meeting) {
        logger.printf("Call to save %s\n", meeting.toString())
        JacksonDBCollection<Meeting, String> jacksonDBCollection = getCollection();
        
        WriteResult<Meeting, String> result = jacksonDBCollection.insert(meeting)
        checkDBWrite(result)
        Meeting m = (Meeting) result.getSavedObject();
        logger.info("Returning saved meeting: " + m.toString());
        return m
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Meeting> getMeetings(@PathParam("vendor_name") String name) {
        JacksonDBCollection<Meeting, String> jacksonDBCollection = getCollection();
        logger.printf("Looking for meetings for vendor %s\n", name)
        List<Meeting> meetings = jacksonDBCollection.find(DBQuery.is('vendorName', name)).toArray()
        logger.info("Items in the collection are: " + jacksonDBCollection.count())

        return meetings
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{meeting_name}")
    Meeting getMeeting(@PathParam("meeting_name") String name) {
        JacksonDBCollection<Meeting, String> jacksonDBCollection = getCollection();
        logger.printf("Looking for meeting with name %s\n", name)
        Meeting m = jacksonDBCollection.findOne(DBQuery.is('meetingName', name));

        logger.printf("Found %d meetings in the collection and returned: %s\n", [jacksonDBCollection.count(), m])

        return m;
    }

//    @POST
//    @Path("/{meeting_name}")
//    public void rateMeeting(@PathParam("meeting_name") String id, Meeting meetingName) {
//        logger.debug("Rating the meeting %s with vendor %s.", meetingName, id);
//    }

    JacksonDBCollection<Meeting, String> getCollection() {
        DBCollection collection = getDatabase().getCollection("meetings");
        return JacksonDBCollection.wrap(collection, Meeting.class, String.class);
    }
}
