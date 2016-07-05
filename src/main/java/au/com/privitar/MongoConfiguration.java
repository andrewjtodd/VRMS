package au.com.privitar;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MongoConfiguration {
    @NotNull
    public String host;

    @NotNull
    @Min(6000)
    @Max(10000)
    public int port;

    @NotNull
    public String db;

    @NotNull
    public String user;

    @NotNull
    public String password;
}
