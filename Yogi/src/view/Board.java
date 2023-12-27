package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.swing.JPanel;
import model.Game;
import model.LevelItem;
import model.Position;
import res.ResourceLoader;

/**
 *
 * @author Daemon
 */
public class Board extends JPanel {
    private Game game;
    private final Image basket, ranger, tree, player, mountain, empty;
    private double scale;
    private int scaled_size;
    private final int tile_size = 32;
    
    /**
     * Constructor, specifies the graphics details to use later
     * @param g Game instance passed from MainWindow class
     * @throws IOException
     */
    public Board(Game g) throws IOException{
        game = g;
        scale = 1.0;
        scaled_size = (int)(scale * tile_size);
        basket = ResourceLoader.loadImage("res/basket.png");
        ranger = ResourceLoader.loadImage("res/ranger.png");
        tree = ResourceLoader.loadImage("res/tree.png");
        player = ResourceLoader.loadImage("res/bear.png");
        mountain = ResourceLoader.loadImage("res/mountain.png");
        empty = ResourceLoader.loadImage("res/empty.png");
    }
    
    /**
     * scale size according to specified tile_size
     * @param scale
     * @return true if successful
     */
    public boolean setScale(double scale){
        this.scale = scale;
        scaled_size = (int)(scale * tile_size);
        return refresh();
    }
    
    /**
     * refreshes the board size
     * @return true if successful
     */
    public boolean refresh(){
        if (!game.isLevelLoaded()) return false;
        Dimension dim = new Dimension(game.getLevelCols() * scaled_size, game.getLevelRows() * scaled_size);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setSize(dim);
        repaint();
        return true;
    }
    
    /**
     * JPanel original method override
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (!game.isLevelLoaded()) return;
        Graphics2D gr = (Graphics2D)g;
        int w = game.getLevelCols();
        int h = game.getLevelRows();
        Position p = game.getPlayerPos();
        for (int y = 0; y < h; y++){
            for (int x = 0; x < w; x++){
                Image img = null;
                LevelItem li = game.getItem(y, x);
                switch (li){
                    case BASKET: img = basket; break;
                    case HOR_RIGHT_RANGER:
                    case HOR_LEFT_RANGER:
                    case VER_DOWN_RANGER:
                    case VER_UP_RANGER: 
                        img = ranger; break;
                    case TREE: img = tree; break;
                    case MOUNTAIN: img = mountain; break;
                    case EMPTY: img = empty; break;
                }
                if (p.x == x && p.y == y) img = player;
                if (img == null) continue;
                gr.drawImage(img, x * scaled_size, y * scaled_size, scaled_size, scaled_size, null);
            }
        }
    }
    
}
