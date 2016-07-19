package au.com.privitar.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Created by andrewtodd on 13/06/2016.
 * A score against a vendor.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class HealthScore extends Score {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    Date scoreDate;

    HealthScore(Date scoreDate, float score) {
        this.scoreDate = scoreDate
        this.score = score
    }

    HealthScore() {
        super();
    }

    @Override
    public String toString() {
        return "HealthScore{" +
                "scoreDate=" + scoreDate +
                ", score=" + score +
                '}';
    }
}
