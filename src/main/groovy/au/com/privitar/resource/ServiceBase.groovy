package au.com.privitar.resource

import com.mongodb.DB
import org.mongojack.WriteResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by andrewtodd on 9/06/2016.
 * Class for other services to use generalised services for.
 */
abstract class ServiceBase {
    private static final Logger logger = LoggerFactory.getLogger(ServiceBase.class);
    DB database;

    ServiceBase(DB database) {
        this.database = database
    }

    /**
     Simple method that checks the results of a DB write, if all OK there is no error.
     */
    def checkDBWrite(WriteResult writeResult) {
        if (writeResult.error != null || writeResult.getSavedIds().size() == 0 || writeResult.error) throw new RuntimeException(writeResult.getError());
    }
}