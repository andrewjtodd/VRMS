package au.com.privitar.resource

import au.com.privitar.domain.VendorEmployee
import com.codahale.metrics.annotation.Timed
import com.mongodb.DB
import com.mongodb.DBCollection
import org.mongojack.DBQuery
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
@Path("/vendors/{vendor_name}/people")
@Timed
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class VendorEmployeeResourceService extends ServiceBase {
    private static final Logger logger = LoggerFactory.getLogger(VendorEmployeeResourceService.class);

    VendorEmployeeResourceService(DB database) {
        super(database)
    }

    @POST
    VendorEmployee addEmployee(VendorEmployee employee) {
        JacksonDBCollection<VendorEmployee, String> jacksonDBCollection = getCollection()
        WriteResult<VendorEmployee, String> result = jacksonDBCollection.insert(employee);
        checkDBWrite(result);
        VendorEmployee v = (VendorEmployee) result.getSavedObject();
        logger.info("Returning saved vendor employee: " + v.toString());
        return v
    }

    @PUT
    void saveEmployee(VendorEmployee employee) {
        logger.printf("Trying to update employee %s\n", employee)
        JacksonDBCollection<VendorEmployee, String> jacksonDBCollection = getCollection()
        WriteResult<VendorEmployee, String> result = jacksonDBCollection.updateById(employee.name, employee);
        checkDBWrite(result);
    }

    @GET
    List<VendorEmployee> getEmployees(@PathParam("vendor_name") String name) {
        JacksonDBCollection<VendorEmployee, String> jacksonDBCollection = getCollection();
        logger.printf("Looking for employees for vendor %s\n", name)
        List<VendorEmployee> employees = jacksonDBCollection.find(DBQuery.is('vendorName', name)).toArray()
        logger.info("Items in the collection are: " + jacksonDBCollection.count())
        return employees
    }

    @DELETE
    @Path("/{employee_name}")
    public void delete(@PathParam("employee_name") String name) {
        getCollection().removeById(name);
    }

    JacksonDBCollection<VendorEmployee, String> getCollection() {
        DBCollection collection = getDatabase().getCollection(VendorEmployee.COLLECTION_NAME);
        JacksonDBCollection<VendorEmployee, String> jacksonDBCollection = JacksonDBCollection.wrap(collection, VendorEmployee.class, String.class);
        return jacksonDBCollection;
    }
}
