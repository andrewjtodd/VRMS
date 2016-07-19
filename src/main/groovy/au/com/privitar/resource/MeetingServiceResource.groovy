package au.com.privitar.resource

import au.com.privitar.domain.Meeting
import com.codahale.metrics.annotation.Timed
import com.mongodb.DB
import com.mongodb.DBCollection
import org.mongojack.DBQuery
import org.mongojack.JacksonDBCollection
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 * Created by andrewtodd on 9/06/2016.
 * Does things related to the meetings.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Timed
@Path("/vendors/{vendor_name}/meeting")
class MeetingServiceResource extends ServiceBase {
    private static final Logger logger = LoggerFactory.getLogger(MeetingServiceResource.class);

    public MeetingServiceResource(DB database) {
        super(database);
    }

    @GET
    @Path("/{meeting_name}")
    Meeting getMeeting(@PathParam("meeting_name") String name) {
        JacksonDBCollection<Meeting, String> jacksonDBCollection = getCollection();
        logger.printf("Looking for meeting with name %s\n", name)
        Meeting m = jacksonDBCollection.findOne(DBQuery.is('meetingName', name));

        logger.printf("Found %d meetings in the collection and returned: %s\n", [jacksonDBCollection.count(), m])

        return m;
    }

    JacksonDBCollection<Meeting, String> getCollection() {
        DBCollection collection = getDatabase().getCollection("meetings");
        return JacksonDBCollection.wrap(collection, Meeting.class, String.class);
    }
}
