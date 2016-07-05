package au.com.privitar.domain

import org.mongojack.ObjectId

/**
 * Created by andrewtodd on 13/06/2016.
 */
class MeetingAttendee {
    @ObjectId
    String _id
    VendorEmployee vendorEmployee
    Employee employee
    HealthScore score
}
