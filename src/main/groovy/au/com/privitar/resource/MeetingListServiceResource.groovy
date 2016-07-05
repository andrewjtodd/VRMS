package au.com.privitar.resource

import au.com.privitar.domain.Meeting
import org.mongojack.JacksonDBCollection

import javax.ws.rs.GET

/**
 * Created by andrewtodd on 5/07/2016.
 * For a list of meetings and other 'bulk' items.
 */
class MeetingListServiceResource {

    @GET
    public List<Meeting> getMeetings() {
        JacksonDBCollection<Meeting, String> jacksonDBCollection = getCollection();
        logger.info("Items in the collection are: " + jacksonDBCollection.count());
        return jacksonDBCollection.find().toArray();
    }

}
