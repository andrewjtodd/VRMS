package au.com.privitar.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Created by andrewtodd on 6/06/2016.
 * The vendor domain obj.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class Vendor extends Company {
    public static final String COLLECTION_NAME = "vendors";
    List<HealthScore> scores = null;
    CurrentScore currentScore = null;

    CurrentScore calculateScore(List<Meeting> meetings, List<Person> people) {
        CurrentScore score = new CurrentScore()
        score.scoreDate = "19/07/2016"
        score.score = 76.5
        return score
    }
}
