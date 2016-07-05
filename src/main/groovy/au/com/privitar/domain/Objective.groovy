package au.com.privitar.domain

import org.mongojack.ObjectId

/**
 * Created by andrewtodd on 12/06/2016.
 * Class to hold objectives of a business.
 */
class Objective {
    @ObjectId
    String _id;
    String name;
    String description;
    Date date;
    boolean active;
}
