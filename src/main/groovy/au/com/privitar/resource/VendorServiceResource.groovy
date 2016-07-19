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
@Path("/vendor")
class VendorServiceResource extends ServiceBase {
    private static final Logger logger = LoggerFactory.getLogger(VendorServiceResource.class);
    VendorServiceResource(DB database) {
        super(database);
    }

    @POST
    Vendor addVendor(Vendor vendor) {
        logger.info("Call to save: " + vendor.name)
        JacksonDBCollection<Vendor, String> jacksonDBCollection = getCollection()

        WriteResult<Vendor, String> result = jacksonDBCollection.insert(vendor)
        checkDBWrite(result)
        Vendor v = (Vendor) result.getSavedObject();
        logger.info("Returning saved vendor: " + v.toString());
        return v
    }

    @GET
    @Path("{vendor_name}")
    Vendor getVendor(@PathParam("vendor_name") String name) {
        JacksonDBCollection<Vendor, String> jacksonDBCollection = getCollection();
        logger.printf("Looking for vendor with name %s\n", name)
        Vendor v = jacksonDBCollection.findOneById(name);

        logger.printf("Found %d vendors in the collection and returned: %s\n", [jacksonDBCollection.count(), v])

        return v;
    }

    @DELETE
    @Path("{vendor_name}")
    public void delete(@PathParam("vendor_name") String name) {
        getCollection().removeById(name);
    }

    JacksonDBCollection<Vendor, String> getCollection() {
        DBCollection collection = getDatabase().getCollection(Vendor.COLLECTION_NAME);
        JacksonDBCollection<Vendor, String> jacksonDBCollection = JacksonDBCollection.wrap(collection, Vendor.class, String.class);
        return jacksonDBCollection;
    }

    @PUT
    def update(Vendor vendor) {
        JacksonDBCollection<Vendor, String> jacksonDBCollection = getCollection();
        WriteResult<Vendor, String> writeResult = jacksonDBCollection.updateById(vendor.name, vendor);
        checkDBWrite(writeResult)
    }
}