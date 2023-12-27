package model;

/**
 *
 * @author Daemon
 */
public class Position {

    /**
     * coordinate for x-Axis
     */
    public int x,

    /**
     * coordinate for y-Axis
     */
    y;

    /**
     * Constructor assigns values
     * @param x
     * @param y
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }    
    
    /**
     * modify position according to a given direction
     * @param d
     * @return the new position instance
     */
    public Position translate(Direction d){
        return new Position(x + d.x, y + d.y);
    }
    
    /**
     * check if two positions are equal
     * @param p
     * @return true in case of equality
     */
    public boolean equals(Position p) {
        return (p.y == this.y && p.x == this.x);
    }
}
