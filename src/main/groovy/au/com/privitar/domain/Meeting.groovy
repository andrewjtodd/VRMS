package au.com.privitar.domain

import com.fasterxml.jackson.annotation.JsonFormat
import org.mongojack.ObjectId

/**
 * Created by andrewtodd on 7/06/2016.
 * A meeting related to a vendor.
 */
class Meeting {
    public static final String COLLECTION_NAME = "meetings";
    @ObjectId
    String _id
    String vendorName
    String name
    String description
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    Date meetingDate
    Location location
    List<MeetingAttendee> meetingAttendees
    List<HealthScore> scores
}
