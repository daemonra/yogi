package model;

import java.util.Objects;

/**
 *
 * @author Daemon
 */
public class GameID {

    /**
     * attribute for further development 
     */
    public final String difficulty;

    /**
     * number of the level
     */
    public final int    level;

    /**
     * Constructor assigns values
     * @param difficulty
     * @param level
     */
    public GameID(String difficulty, int level) {
        this.difficulty = difficulty;
        this.level = level;
    }

    /**
     * creates hash code for every game level
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.difficulty);
        hash = 29 * hash + this.level;
        return hash;
    }

    /**
     * check if two gameIDs are equal
     * @param obj
     * @return true in case of equality
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GameID other = (GameID) obj;
        if (this.level != other.level) {
            return false;
        }
        if (!Objects.equals(this.difficulty, other.difficulty)) {
            return false;
        }
        return true;
    }
    
    
}
