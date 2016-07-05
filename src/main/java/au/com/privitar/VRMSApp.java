package au.com.privitar;

import au.com.privitar.db.MongoClientManager;
import au.com.privitar.health.MongoHealthCheck;
import au.com.privitar.health.VRMSHealthCheck;
import au.com.privitar.resource.EmployeeServiceResource;
import au.com.privitar.resource.MeetingServiceResource;
import au.com.privitar.resource.VendorEmployeeResourceService;
import au.com.privitar.resource.VendorServiceResource;
import au.com.privitar.resource.VendorListServiceResource;
import au.com.privitar.services.HealthCalculatorService;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * Created by andrewtodd on 6/06/2016.
 * Main class for the VRMS application (single service at this stage).
 */
public class VRMSApp extends Application<VRMSConfiguration> {
    private final Logger logger = LoggerFactory.getLogger(VRMSApp.class);

    public static void main(String[] args) throws Exception {
        new VRMSApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<VRMSConfiguration> bootstrap) {

    }

    @Override
    public void run(VRMSConfiguration configuration, Environment environment) throws Exception {
        logger.info("Running DropWizard -- VRMS application");

        final MongoClient mongoClient = new MongoClient();
        final DB database = mongoClient.getDB(Constants.DBNAME);

        MongoClientManager mongoClientManager = new MongoClientManager(mongoClient);
        environment.lifecycle().manage(mongoClientManager);

        final VRMSHealthCheck healthCheck = new VRMSHealthCheck();
        final MongoHealthCheck mongoCheck = new MongoHealthCheck(database);
        environment.healthChecks().register("vrms", healthCheck);
        environment.healthChecks().register("mongo", mongoCheck);
        addServiceResources(environment, database);
        environment.jersey().setUrlPattern("/api/*");

        FilterRegistration.Dynamic cors = environment.servlets().addFilter("crossOriginRequests", CrossOriginFilter.class);
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");
    }

    private void addServiceResources(Environment environment, DB database) {
        // more resources that do the end points here.
        VendorServiceResource vendorServiceResource = new VendorServiceResource(database);
        VendorListServiceResource vendorListServiceResource = new VendorListServiceResource(database);
        MeetingServiceResource meetingServiceResource = new MeetingServiceResource(database);
        EmployeeServiceResource employeeServiceResource = new EmployeeServiceResource(database);
        VendorEmployeeResourceService vendorEmployeeResourceService = new VendorEmployeeResourceService(database);
        HealthCalculatorService healthCalculatorService = new HealthCalculatorService(database);


        environment.jersey().register(vendorEmployeeResourceService);
        environment.jersey().register(vendorServiceResource);
        environment.jersey().register(vendorListServiceResource);
        environment.jersey().register(meetingServiceResource);
        environment.jersey().register(employeeServiceResource);
        environment.jersey().register(healthCalculatorService);
    }
}
