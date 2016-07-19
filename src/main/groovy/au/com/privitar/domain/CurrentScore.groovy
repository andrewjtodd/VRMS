package au.com.privitar.domain

/**
 * Created by andrewtodd on 16/07/2016.
 * Similar to healthscore but with 2 simple strings as we don't need the detailed date/time.
 */
class CurrentScore extends Score {
    String scoreDate;

    CurrentScore() {
        super()
    }

    @Override
    public String toString() {
        return "CurrentScore{" +
                "score=" + score +
                ", scoreDate='" + scoreDate + '\'' +
                '}';
    }
}
