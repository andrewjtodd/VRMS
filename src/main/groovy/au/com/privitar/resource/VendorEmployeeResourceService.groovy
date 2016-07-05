package au.com.privitar.resource

import au.com.privitar.domain.VendorEmployee
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
 * Created by andrewtodd on 12/06/2016.
 * Service for requests to Vendor Staff
 */
@Path("/vendors/{vendor_name}/employees")
class VendorEmployeeResourceService extends ServiceBase {
    private static final Logger logger = LoggerFactory.getLogger(VendorEmployeeResourceService.class);

    VendorEmployeeResourceService(DB database) {
        super(database)
    }

    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    void addEmployee(VendorEmployee employee) {
        JacksonDBCollection<VendorEmployee, String> jacksonDBCollection = getCollection()
        WriteResult<VendorEmployee, String> result = jacksonDBCollection.insert(employee);
        checkDBWrite(result);
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    List<VendorEmployee> getEmployees() {
        JacksonDBCollection<VendorEmployee, String> jacksonDBCollection = getCollection();
        logger.info("Items in the collection are: " + jacksonDBCollection.count());
        return jacksonDBCollection.find().toArray()
    }

    @DELETE
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{employee_name}")
    public void delete(@PathParam("employee_name") String name) {
        getCollection().removeById(name);
    }

    JacksonDBCollection<VendorEmployee, String> getCollection() {
        DBCollection collection = getDatabase().getCollection("vendor_employees");
        JacksonDBCollection<VendorEmployee, String> jacksonDBCollection = JacksonDBCollection.wrap(collection, VendorEmployee.class, String.class);
        return jacksonDBCollection;
    }
}
