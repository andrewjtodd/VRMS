package au.com.privitar.resource

import au.com.privitar.domain.Vendor
import com.codahale.metrics.annotation.Timed
import com.mongodb.DB
import com.mongodb.DBCollection
import org.mongojack.JacksonDBCollection

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * Created by andrewtodd on 16/07/2016.
 * Just returns the number of vendors.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Timed
@Path("vendors/count")
class VendorCountServiceResource extends ServiceBase {
    VendorCountServiceResource(DB database) {
        super(database)
    }

    @GET
    int getTotalVendors() {
        JacksonDBCollection<Vendor, String> jacksonDBCollection = getCollection();
        return jacksonDBCollection.find().toArray().size()
    }

    JacksonDBCollection<Vendor, String> getCollection() {
        DBCollection collection = getDatabase().getCollection(Vendor.COLLECTION_NAME);
        JacksonDBCollection<Vendor, String> jacksonDBCollection = JacksonDBCollection.wrap(collection, Vendor.class, String.class);
        return jacksonDBCollection;
    }
}
