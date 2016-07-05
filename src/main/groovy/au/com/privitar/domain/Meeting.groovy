package au.com.privitar.domain

import com.fasterxml.jackson.annotation.JsonFormat
import org.mongojack.ObjectId

/**
 * Created by andrewtodd on 7/06/2016.
 * A meeting related to a vendor.
 */
class Meeting {
    @ObjectId
    String _id
    String vendorName
    String meetingName
    String description
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    Date meetingDate
    Location location
    List<MeetingAttendee> meetingAttendees
    HealthScore score
}
