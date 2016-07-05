package au.com.privitar.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.Id

/**
 * Created by andrewtodd on 12/06/2016.
 * A company, essentially the client who is interested in the outcome of the relationship health.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class Company {
    @Id
    String name;
    Location location;
    List<Objective> objectives;


    @Override
    public String toString() {
        return "Company{" +
                " name='" + name + '\'' +
                ", location=" + location +
                ", objectives=" + objectives +
                '}';
    }
}
