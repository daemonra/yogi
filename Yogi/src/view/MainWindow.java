package view;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import model.Direction;
import model.Game;
import databases.HighScores;
import model.GameID;
import databases.HighScore;

/**
 *
 * @author Daemon
 */
public class MainWindow extends JFrame{
    private final Game game;
    private Board board;
    private final JLabel gameStatLabel; 
    private Direction currDir;
    private Boolean ended = false;
    private int time = 0, currLevel = 1, score = 0;
    
    /**
     * Constructor, specifies the frame properities and starts the game
     * @throws IOException
     */
    public MainWindow() throws IOException{
        game = new Game();
        
        setTitle("Yogi Bear");
        setSize(600, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        URL url = MainWindow.class.getClassLoader().getResource("res/bear.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(url));
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Game");
        JMenu menuGameScale = new JMenu("Zoom");
        createGameLevelMenuItems(menuGame);
        createScaleMenuItems(menuGameScale, 1.0, 2.0, 0.5);

        JMenuItem menuGameExit = new JMenuItem(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        JMenuItem menuGameRestart = new JMenuItem(new AbstractAction("Restart") {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        
        JMenuItem menuHighScore = new JMenuItem(new AbstractAction("HighScores") {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTableData();
            }
        });

        menuGame.add(menuGameScale);
        menuGame.addSeparator();
        menuGame.add(menuHighScore);
        menuGame.addSeparator();
        menuGame.add(menuGameRestart);
        menuGame.add(menuGameExit);
        menuBar.add(menuGame);
        setJMenuBar(menuBar);
        
        setLayout(new BorderLayout(0, 10));
        gameStatLabel = new JLabel("label");

        add(gameStatLabel, BorderLayout.NORTH);
        try { add(board = new Board(game), BorderLayout.CENTER); } catch (IOException ex) {}
        
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                super.keyPressed(ke); 
                if (!game.isLevelLoaded()) return;
                int kk = ke.getKeyCode();
                Direction d = null;
                switch (kk){
                    case KeyEvent.VK_A:  currDir = d = Direction.LEFT; break;
                    case KeyEvent.VK_D: currDir = d = Direction.RIGHT; break;
                    case KeyEvent.VK_W:    currDir = d = Direction.UP; break;
                    case KeyEvent.VK_S:  currDir = d = Direction.DOWN; break;
                    case KeyEvent.VK_ESCAPE: restartGame();
                }
                refreshGameStatLabel();
                board.repaint();
                
                // into TIMER!!!
                if (currDir  != null && game.step(currDir )){
                    if (game.getLevelNumBaskets() == game.getLevelNumBasketsCollected()){
                        if (currLevel < 10) {
                            game.putAtEntrance();
                            JOptionPane.showMessageDialog(MainWindow.this, "Level " + currLevel + " is done successfully, Play the next one!", "Congrats", JOptionPane.INFORMATION_MESSAGE);
                            score+=game.getLevelNumBasketsCollected();
                            //System.out.println(score);
                            game.loadGame(new GameID("LEVEL", ++currLevel));
                            board.refresh();
                            pack();
                            
                        } else {
                            ended = true;
                            game.putAtEntrance();
                            String name = JOptionPane.showInputDialog(MainWindow.this, "Congratulations, You Won! Enter Your Name:", "Yogi");
                            try {
                                HighScores highScores = new HighScores(10);
                                highScores.putHighScore(name, (score+game.getLevelNumBasketsCollected()));
                            } 
                            catch (SQLException ex) {
                                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.exit(0);
                        }
                    }
                    checkEnd();
                } 
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                super.keyReleased(ke); //To change body of generated methods, choose Tools | Templates.
                Direction d = null;
                int kk = ke.getKeyCode();
                
                switch (kk){
                    case KeyEvent.VK_A:  d = Direction.LEFT; break;
                    case KeyEvent.VK_D: d = Direction.RIGHT; break;
                    case KeyEvent.VK_W:    d = Direction.UP; break;
                    case KeyEvent.VK_S:  d = Direction.DOWN; break;
                }
                
                if (currDir == d) currDir = null;
            }
            
            
        });

        setResizable(false);
        setLocationRelativeTo(null);
        game.loadGame(new GameID("LEVEL", currLevel));
        board.refresh();
        pack();
        refreshGameStatLabel();
        setVisible(true);
        
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                refreshGameStatLabel();
                board.repaint();
                checkEnd();
            }
        }, 0, 100);        
        
        Timer t2 = new Timer();
        t2.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!ended) {
                    time++;
                }
            }
        }, 0, 1000);
    }
    
    /**
     * restarts Game
     */
    public void restartGame() {
           
        try {
            new MainWindow();
        } catch (IOException ex) {}
        this.dispose();
    }
    
    /**
     * Creates a frame, to put the table that we obtain form database in it
     */
    public void showTableData() {

        JFrame tableFrame = new JFrame("Database Search Result");
        tableFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        tableFrame.setLayout(new BorderLayout()); 
        String columnNames [] = {"Name", "Score"};
        
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        
        JTable table = new JTable();
        table.setModel(model); 
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
        ArrayList<HighScore> hs = new ArrayList<>();
        
        try {
            HighScores highScores = new HighScores(10);
            hs = highScores.getHighScores();
        } 
        catch (SQLException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (!hs.isEmpty()) {
        
            for (HighScore h : hs) {
                model.addRow(new Object[]{h.getName(), h.getScore()});
            }
            
        } else {
            JOptionPane.showMessageDialog(null, "No Record Found","Error", JOptionPane.ERROR_MESSAGE);
        }
        
        tableFrame.add(scroll);
        tableFrame.setVisible(true);
        tableFrame.setSize(400,300);
    }

    /**
     * checks if the game has reached its end or not, if so it prompts the user to put the name for the high score table
     */
    public void checkEnd() {
        
        if (game.getLives() == 0 && !ended){
            ended = true;
            //End
//            JOptionPane.showMessageDialog(MainWindow.this, "Yogi bear has died!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            String name = JOptionPane.showInputDialog(MainWindow.this, "Game Over! Enter Your Name:", "Yogi");
            try {
                HighScores highScores = new HighScores(10);
                highScores.putHighScore(name, (score+game.getLevelNumBasketsCollected()));
            } 
            catch (SQLException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }  
            System.exit(0);
        }
    }
    
    private void refreshGameStatLabel(){
        String s = "Lives: " + game.getLives();
        s += ", Score: " + (score + game.getLevelNumBasketsCollected());
        String timeStat = "";
        if (time >= 60) {
            timeStat += time/60 + "m ";
            timeStat += time%60 + "s";
        } else {
            timeStat += time%60 + "s";
        }
        s += ", Time: " + timeStat;
        gameStatLabel.setText(s);
    }
    
    private void createGameLevelMenuItems(JMenu menu){
        for (String s : game.getDifficulties()){
            JMenu difficultyMenu = new JMenu("Level");
            menu.add(difficultyMenu);
            for (Integer i : game.getLevelsOfDifficulty(s)){
                JMenuItem item = new JMenuItem(new AbstractAction("Level-" + i) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currLevel = i;
                        game.loadGame(new GameID(s, i));
                        board.refresh();
                        pack();
                    }
                });
                difficultyMenu.add(item);
            }
        }
    }
    
    private void createScaleMenuItems(JMenu menu, double from, double to, double by){
        while (from <= to){
            final double scale = from;
            JMenuItem item = new JMenuItem(new AbstractAction(from + "x") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (board.setScale(scale)) pack();
                }
            });
            menu.add(item);
            
            if (from == to) break;
            from += by;
            if (from > to) from = to;
        }
    }
    
    /**
     * main function, creates MainWindow instance
     * @param args
     */
    public static void main(String[] args) {
        try {
            new MainWindow();
        } catch (IOException ex) {}
    }    
}
