package au.com.privitar.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Created by andrewtodd on 13/06/2016.
 * A score against a vendor.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class HealthScore {
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    Date scoreDate;
    float score;

    HealthScore() {
        super()
    }

    HealthScore(Date scoreDate, float score) {
        this.scoreDate = scoreDate
        this.score = score
    }

    @Override
    public String toString() {
        return "HealthScore{" +
                "scoreDate=" + scoreDate +
                ", score=" + score +
                '}';
    }
}
