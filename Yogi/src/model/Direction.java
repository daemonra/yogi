package model;

/**
 *
 * @author Daemon
 */
public enum Direction {

    /**
     * Moving direction downwards
     */
    DOWN(0, 1),

    /**
     * Moving direction to the left
     */
    LEFT(-1, 0),

    /**
     * Moving direction upwards
     */
    UP(0, -1),

    /**
     * Moving direction to the right
     */
    RIGHT(1, 0);
    
    Direction(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * coordinate for x-Axis
     */
    public final int x,

    /**
     * coordinate for y-Axis
     */
    y;
}
