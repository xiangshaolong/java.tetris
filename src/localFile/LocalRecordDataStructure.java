package localFile;

import java.io.Serializable;

/**
 * Serializable class for writing file
 */
public class LocalRecordDataStructure implements Serializable {
    private int highestScore = 0;

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }
}
