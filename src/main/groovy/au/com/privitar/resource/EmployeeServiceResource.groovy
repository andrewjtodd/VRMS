package au.com.privitar.resource

import au.com.privitar.domain.Employee
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
 * Handles requests for an employee.
 */
@Path("/companies/{company_name}/employees")
class EmployeeServiceResource extends ServiceBase {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceResource.class);

    EmployeeServiceResource(DB database) {
        super(database)
    }

    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    void addEmployee(Employee employee) {
        JacksonDBCollection<Employee, String> jacksonDBCollection = getCollection()

        WriteResult<Employee, String> result = jacksonDBCollection.insert(employee);
        checkDBWrite(result);
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    List<Employee> getEmployees() {
        JacksonDBCollection<Employee, String> jacksonDBCollection = getCollection();
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

    JacksonDBCollection<Employee, String> getCollection() {
        DBCollection collection = getDatabase().getCollection("employees");
        JacksonDBCollection<Employee, String> jacksonDBCollection = JacksonDBCollection.wrap(collection, Employee.class, String.class);
        return jacksonDBCollection;
    }
}
