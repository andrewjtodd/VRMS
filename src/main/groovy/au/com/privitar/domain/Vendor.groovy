package au.com.privitar.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Created by andrewtodd on 6/06/2016.
 * The vendor domain obj.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class Vendor extends Company {
    public static final String COLLECTION_NAME = "vendors";
    List<HealthScore> scores = new ArrayList<>();
    HealthScore score = new HealthScore();
}
