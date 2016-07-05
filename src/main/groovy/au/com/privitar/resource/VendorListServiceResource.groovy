package au.com.privitar.resource

import au.com.privitar.domain.Vendor
import com.codahale.metrics.annotation.Timed
import com.mongodb.DB
import com.mongodb.DBCollection
import org.mongojack.JacksonDBCollection
import org.mongojack.WriteResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 * Created by andrewtodd on 9/06/2016.
 * Does things related to the vendor.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Timed
@Path("/vendors")
class VendorListServiceResource extends ServiceBase {
    private static final Logger logger = LoggerFactory.getLogger(VendorListServiceResource.class);
    VendorListServiceResource(DB database) {
        super(database);
    }

    @GET
    List<Vendor> getVendors() {
        JacksonDBCollection<Vendor, String> jacksonDBCollection = getCollection();
        logger.info("Items in the collection are: " + jacksonDBCollection.count());
        return jacksonDBCollection.find().toArray()
    }

    JacksonDBCollection<Vendor, String> getCollection() {
        DBCollection collection = getDatabase().getCollection("vendors");
        JacksonDBCollection<Vendor, String> jacksonDBCollection = JacksonDBCollection.wrap(collection, Vendor.class, String.class);
        return jacksonDBCollection;
    }
}