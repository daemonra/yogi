package model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import res.ResourceLoader;

/**
 *
 * @author Daemon
 */
public class Game {
    private final HashMap<String, HashMap<Integer, GameLevel>> gameLevels;
    private GameLevel gameLevel = null;

    /**
     * Constructor, creates the hash map of the game levels
     */
    public Game() {
        gameLevels = new HashMap<>();
        readLevels(1);
        
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if (gameLevel == null) {
                
                } else {
                    for (int i = 0; i < gameLevel.rangers.size(); i++) {
                        gameLevel.moveRanger(gameLevel.rangers.get(i), i);
                    }
                }
               
            }
        }, 0, 1000);
    }

    // ------------------------------------------------------------------------
    // The 'interesting' part :)
    // ------------------------------------------------------------------------

    /**
     * Creates a new game level based on gameID
     * @param gameID
     */

    public void loadGame(GameID gameID){
        gameLevel = new GameLevel(gameLevels.get(gameID.difficulty).get(gameID.level));
    }
    
    /**
     * Prints the level
     */
    public void printGameLevel(){ gameLevel.printLevel(); }
    
    /**
     * move the player or collects the baskets while doing so
     * @param d
     * @return true if successful
     */
    public boolean step(Direction d){
        return (gameLevel.movePlayer(d) || gameLevel.collectBasket(d));
    }
    
    // ------------------------------------------------------------------------
    // Getter methods
    // ------------------------------------------------------------------------
    
    public Collection<String> getDifficulties(){ return gameLevels.keySet(); }
    
    public Collection<Integer> getLevelsOfDifficulty(String difficulty){
        if (!gameLevels.containsKey(difficulty)) return null;
        return gameLevels.get(difficulty).keySet();
    }
    
    /**
     * checks if the level is already loaded
     * @return true if loaded
     */
    public boolean isLevelLoaded(){ return gameLevel != null; }
    public int getLevelRows(){ return gameLevel.rows; }
    public int getLevelCols(){ return gameLevel.cols; }
    public LevelItem getItem(int row, int col){ return gameLevel.level[row][col]; }
    public GameID getGameID(){ return (gameLevel != null) ? gameLevel.gameID : null; }
    public int getLevelNumBaskets(){ return (gameLevel != null) ? gameLevel.getNumBaskets() : 0; }
    public int getLevelNumBasketsCollected(){ return (gameLevel != null) ? gameLevel.getNumBasketsCollected(): 0; }
    public int getNumSteps(){ return (gameLevel != null) ? gameLevel.getNumSteps(): 0; }
    public int getLives(){ return (gameLevel != null) ? gameLevel.getLives(): 0; }

    /**
     * places the player at the entrance temporarily 
     */
    public void putAtEntrance() { gameLevel.putAtEntrance(); }

    public Position getPlayerPos(){ // MAKE IT ~IMMUTABLE
        return new Position(gameLevel.player.x, gameLevel.player.y); 
    }
    
    // ------------------------------------------------------------------------
    // Utility methods to load game levels from res/levels.txt resource file.
    // ------------------------------------------------------------------------

    private void readLevels(int counter){
        //ClassLoader cl = getClass().getClassLoader();
        InputStream is;// = cl.getResourceAsStream("res/levels.txt");
        is = ResourceLoader.loadResource("res/level"+counter+".txt");
        //System.out.println("df");

        try (Scanner sc = new Scanner(is)){
            String line = readNextLine(sc);
            ArrayList<String> gameLevelRows = new ArrayList<>();

            while (!line.isEmpty()){
                GameID id = readGameID(line);
                if (id == null) return;

                // System.out.println(id.difficulty + " " + id.id);

                gameLevelRows.clear();
                line = readNextLine(sc);
                while (!line.isEmpty() && line.trim().charAt(0) != ';'){
                    gameLevelRows.add(line);                    
                    line = readNextLine(sc);
                }
                addNewGameLevel(new GameLevel(gameLevelRows, id));
                if (counter < 10) readLevels(counter+1);
            }
            //if (is != null) is.close();
        } catch (Exception e){
            //System.out.println("Ajaj");
        }

        
    }
    
    private void addNewGameLevel(GameLevel gameLevel){
        HashMap<Integer, GameLevel> levelsOfDifficulty;
        if (gameLevels.containsKey(gameLevel.gameID.difficulty)){
            levelsOfDifficulty = gameLevels.get(gameLevel.gameID.difficulty);
            levelsOfDifficulty.put(gameLevel.gameID.level, gameLevel);
        } else {
            levelsOfDifficulty = new HashMap<>();
            levelsOfDifficulty.put(gameLevel.gameID.level, gameLevel);
            gameLevels.put(gameLevel.gameID.difficulty, levelsOfDifficulty);
        }
    }
  
    private String readNextLine(Scanner sc){
        String line = "";
        while (sc.hasNextLine() && line.trim().isEmpty()){
            line = sc.nextLine();
        }
        return line;
    }
    
    private GameID readGameID(String line){
        line = line.trim();
        if (line.isEmpty() || line.charAt(0) != ';') return null;
        Scanner s = new Scanner(line);
        s.next(); // skip ';'
        if (!s.hasNext()) return null;
        String difficulty = s.next().toUpperCase();
        if (!s.hasNextInt()) return null;
        int id = s.nextInt();
        return new GameID(difficulty, id);
    }    
}
