package model;

import java.util.ArrayList;

public class GameLevel {
    public final GameID        gameID;
    public final int           rows, cols;
    public final LevelItem[][] level;
    public Position            player = new Position(0, 0);
    public Position      entrance;
    public ArrayList<Position> rangers;
    private int                numBaskets, numBasketsCollected, numSteps;
    private static int         lives = 3;
    
    /**
     * Constructor, sets the initial values for every game level
     * @param gameLevelRows
     * @param gameID
     */
    public GameLevel(ArrayList<String> gameLevelRows, GameID gameID){
        this.gameID = gameID;
        int c = 0;
        for (String s : gameLevelRows) if (s.length() > c) c = s.length();
        rows = gameLevelRows.size();
        cols = c;
        level = new LevelItem[rows][cols];
        rangers = new ArrayList<>();
        numBaskets = 0;
        numBasketsCollected = 0;
        numSteps = 0;
        
        for (int i = 0; i < rows; i++){
            String s = gameLevelRows.get(i);
            for (int j = 0; j < s.length(); j++){
                switch (s.charAt(j)){
                    case '@': player = new Position(j, i);
                              entrance = new Position(j,i);
                              level[i][j] = LevelItem.EMPTY; break;
                    case 'B': level[i][j] = LevelItem.BASKET; 
                              numBaskets++;
                              break;
                    case 'T': level[i][j] = LevelItem.TREE; break;
                    case 'M': level[i][j] = LevelItem.MOUNTAIN; break;
                    case 'L': 
                        level[i][j] = LevelItem.HOR_LEFT_RANGER; 
                        rangers.add(new Position(j,i));
                        break;
                    case 'R': 
                        level[i][j] = LevelItem.HOR_RIGHT_RANGER; 
                        rangers.add(new Position(j,i));
                        break;
                    case 'U': 
                        level[i][j] = LevelItem.VER_UP_RANGER; 
                        rangers.add(new Position(j,i));
                        break;
                    case 'D': 
                        level[i][j] = LevelItem.VER_DOWN_RANGER; 
                        rangers.add(new Position(j,i));
                        break;
                    default:  level[i][j] = LevelItem.EMPTY; break;
                }
            }
            for (int j = s.length(); j < cols; j++){
                level[i][j] = LevelItem.EMPTY;
            }
        }
    }

    /**
     * Constructor loads the game level based on an existing game level instance
     * @param gl
     */
    public GameLevel(GameLevel gl) {
        gameID = gl.gameID;
        rows = gl.rows;
        cols = gl.cols;
        numBaskets = gl.numBaskets;
        numBasketsCollected = gl.numBasketsCollected;
        numSteps = gl.numSteps;
        level = new LevelItem[rows][cols];
        rangers = gl.rangers;
        player = new Position(gl.player.x, gl.player.y);
        entrance = new Position(gl.entrance.x, gl.entrance.y);
        lives = gl.lives;
        for (int i = 0; i < rows; i++){
            System.arraycopy(gl.level[i], 0, level[i], 0, cols);
        }
    }
    
    /**
     * move a ranger at specific position and updates its index in the rangers array
     * @param p position of the ranger on the grid
     * @param index of the ranger in the array
     */
    public void moveRanger(Position p, int index) {
    
        Position curr = p;
        Direction d = Direction.DOWN;
        boolean success = false;
        
        if (null != level[p.y][p.x]) switch (level[p.y][p.x]) {
            case HOR_RIGHT_RANGER: d = Direction.RIGHT; break;
            case HOR_LEFT_RANGER: d = Direction.LEFT; break;
            case VER_UP_RANGER: d = Direction.UP; break;
            case VER_DOWN_RANGER: d = Direction.DOWN; break;
            default: break;
        }
        
        Position next = curr.translate(d);
        
        if (isFree(next)) {
            level[next.y][next.x] = level[curr.y][curr.x];
            rangers.set(index, next);
            level[curr.y][curr.x] = LevelItem.EMPTY;
            success = true;
        } 
        
        if (!success) {
            if (null != level[p.y][p.x]) switch (level[p.y][p.x]) {
            case HOR_RIGHT_RANGER: level[curr.y][curr.x] = LevelItem.HOR_LEFT_RANGER; moveRanger(curr, index); break;
            case HOR_LEFT_RANGER: level[curr.y][curr.x] = LevelItem.HOR_RIGHT_RANGER; moveRanger(curr, index); break;
            case VER_UP_RANGER: level[curr.y][curr.x] = LevelItem.VER_DOWN_RANGER; moveRanger(curr, index); break;
            case VER_DOWN_RANGER: level[curr.y][curr.x] = LevelItem.VER_UP_RANGER; moveRanger(curr, index); break;
            default: break;
        }
        }
        
        checkAndRespawn();
    }
    
    /**
     * checks if the player is in the area of the rangers
     * @return true if the player is too close to a ranger
     */
    public boolean playerCloseToRanger() {
        
        for (Position r : rangers) {
            
            Position toLeft = r.translate(Direction.LEFT);
            Position toRight = r.translate(Direction.RIGHT);
            Position toUp = r.translate(Direction.UP);
            Position toDown = r.translate(Direction.DOWN);
            
            if (player.equals(toLeft) || player.equals(toRight) || player.equals(toUp) || player.equals(toDown) || player.equals(r)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * decrease number of lives and returns player to the entrance of the level
     */
    public void checkAndRespawn() {
        
        if(playerCloseToRanger()) {
            
            if (lives>-1) {
                lives--;
                player = entrance;
            }
        }
    }
    
    /**
     * places the player at the entrance temporarily 
     */
    public void putAtEntrance() {
        player = entrance;
    }
 
    /**
     * check if a position is valid, fits in the grid
     * @param p
     * @return true if yes
     */
    public boolean isValidPosition(Position p){
        return (p.x >= 0 && p.y >= 0 && p.x < cols && p.y < rows);
    }
    
    /**
     * checks if a specific position free, which means it is empty on the grid
     * @param p
     * @return true if so
     */
    public boolean isFree(Position p){
        if (!isValidPosition(p)) return false;
        LevelItem li = level[p.y][p.x];
        return (li == LevelItem.EMPTY);
    }
    
    /**
     * checks if a specific position contains a basket
     * @param p
     * @return
     */
    public boolean isBasket(Position p) {
        if (!isValidPosition(p)) return false;
        LevelItem li = level[p.y][p.x];
        return (li == LevelItem.BASKET);
    }
    
    /**
     * move the player on the grid based on a given direction
     * @param d
     * @return true if successful
     */
    public boolean movePlayer(Direction d){
        Position curr = player;
        Position next = curr.translate(d);
        if (numBasketsCollected < numBaskets && isFree(next)) {
            player = next;
            numSteps++;
            checkAndRespawn();
            return true;
        } 
        return false;
    }
    
    /**
     * collect a specific basket when player moves to the grid block which contains this basket
     * @param d
     * @return true if successful
     */
    public boolean collectBasket(Direction d) {
        
        Position curr = player;
        Position next = curr.translate(d);
        
        if (numBasketsCollected < numBaskets && isBasket(next)) { 
            player = next;
            if (!playerCloseToRanger()) {
                level[next.y][next.x] = LevelItem.EMPTY;
                numBasketsCollected++;
            }
            checkAndRespawn();
            
            numSteps++;
            return true;
        } 
        return false;
    }
    
    /**
     * prints the level textual representation to the consol
     */
    public void printLevel(){
        int x = player.x, y = player.y;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                if (i == y && j == x)
                    System.out.print('@');
                else 
                    System.out.print(level[i][j].representation);
            }
            System.out.println("");
        }
    }

    public int getNumBaskets() {
        return numBaskets;
    }
    
    public int getNumBasketsCollected() {
        return numBasketsCollected;
    }
    
    public int getNumSteps() {
        return numSteps;
    }    

    public int getLives() {
        return lives;
    }
}
