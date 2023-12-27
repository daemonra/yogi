/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databases;

/**
 *
 * @author Daemon
 */
public class HighScore {
    
    private final String name;
    private final int score;

    /**
     * Constructor, assign values to private variables
     * @param name 
     * @param score
     */
    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    /**
     * Textual representation modification
     * @return the textual representation
     */
    @Override
    public String toString() {
        return "HighScore{" + "name=" + name + ", score=" + score + '}';
    }
    

}
