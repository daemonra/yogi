package model;

/**
 *
 * @author Daemon
 */
public enum LevelItem {

    /**
     * Ranger moving vertically downwards
     */
    VER_DOWN_RANGER('D'),

    /**
     * Ranger moving vertically upwards
     */
    VER_UP_RANGER('U'),

    /**
     * Ranger moving horizontally to the left
     */
    HOR_LEFT_RANGER('L'),

    /**
     * Ranger moving horizontally to the right
     */
    HOR_RIGHT_RANGER('R'),

    /**
     * Mountain obstacle
     */
    MOUNTAIN('M'),

    /**
     * Tree Obstacle
     */
    TREE('T'),

    /**
     * Basket Element
     */
    BASKET('B'),

    /**
     * Empty Grid
     */
    EMPTY(' ');
    LevelItem(char rep){ representation = rep; }

    /**
     * Representation of every element in the text files
     */
    public final char representation;
}
