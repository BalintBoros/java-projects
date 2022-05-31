/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thegame;

import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;
import model.Player;
import model.Field.AbstractFieldModel;
import model.Field.BaseModel;
import model.Field.MineModel;
import model.Field.TowerModel;
import model.Unit.UnitModel;
import thegame.ShortestPathBetweenCellsBFS;
import model.Field.GrassModel;
import model.Items;
import model.LevelItem;
import model.Position;
import static thegame.ShortestPathBetweenCellsBFS.shortestPath;
import thegame.ShortestPathBetweenCellsBFS.*;

/**
 *
 * @author fodor
 */
public class GameEngine {

    public GameEngine() {
        player1 = new Player();
        player2 = new Player();
        this.currentPlayer = player1;
        enemyPlayer = player2;
        table = new AbstractFieldModel[20][20];
        baseModel1 = new BaseModel();
        baseModel2 = new BaseModel();
        winner = "nobody";
    }
    private final Player player1;
    private Player player2;
    private Player currentPlayer;
    private Player enemyPlayer;
    private AbstractFieldModel table[][];
    private AbstractFieldModel[][] obstacles;
    private int path[][];
    private int tmpPath[][];
    private int base1[] = new int[2];
    private int base2[] = new int[2];
    private int pathLength = 0;
    ArrayList<Position> toRemoveTower = new ArrayList<>();
    ArrayList<Position> attackFrom = new ArrayList<>();
    ArrayList<Position> attackTo = new ArrayList<>();
    
    private BaseModel baseModel1;
    private BaseModel baseModel2;
    private String winner;
    
    //private Vector<Vector<AbstractFieldModel>> table = new Vector<Vector<AbstractFieldModel>>();


    public void step() {
        fillTable();
        int[] b1 = {base1[0], base1[1]};
        int[] b2 = {base2[0], base2[1]};
        ArrayList<UnitModel> toRemove = new ArrayList<>();
        findShortestPath(b1, b2);
        checkMines();
        int[] target = b1;
        currentPlayer.addCoin(25);
        // unit movement
        for (UnitModel unit : currentPlayer.getUnits()) {
            // System.out.println("unit.getBuffed() = " + unit.getBuffed());
            if (unit.getBuffed() > 0) {
                unit.setBuffed(unit.getBuffed() - 1);
            }
            if (unit.getBuffed() == 0) {
                unit.setBuffed(unit.getBuffed() - 1);
                String tmp = unit.getBuff_type();
                if (tmp == "health") {
                    unit.setHealth(unit.getHealth() / 2);
                }
                if (tmp == "target") {
                    unit.setSpeed(unit.getSpeed()/ 2);
                }
                if (tmp == "attack") {
                    unit.setAttackpower(unit.getAttackpower() / 2);
                }
            }
            //System.out.println("Step health: " + unit.getHealth() + " Speed: " + unit.getSpeed() + " attack: " + unit.getAttackpower());
            
            int speed = unit.getSpeed();
            int tmpLoc[] = new int[2];
            tmpLoc[0] = unit.getX();
            tmpLoc[1] = unit.getY();
            if (currentPlayer == player1) {
                target = b2;
            } else if (currentPlayer == player2) {
                target = b1;
            }

            customPath(tmpLoc, target);
            
            if (speed > 1) {
                if (tmpPath[1][0] == target[0] && tmpPath[1][1] == target[1]) {
                    unit.setX(tmpPath[1][0]);
                    unit.setY(tmpPath[1][1]);
                }
                else{
                    unit.setX(tmpPath[1 * speed][0]);
                    unit.setY(tmpPath[1 * speed][1]); 
                }
            }
            else{
                unit.setX(tmpPath[1 * speed][0]);
                unit.setY(tmpPath[1 * speed][1]);    
            }
            
                 

            if(unit.getX() == target[0] && unit.getY() == target[1]){
                if(currentPlayer == player1){
                    baseModel2.attack(unit);
                    //attackFrom.add(new Position(base1[0],base1[1]));
                    //attackTo.add(new Position(unit.getX(),unit.getY()));
                }else{
                    //attackFrom.add(new Position(base2[0],base2[1]));
                    //attackTo.add(new Position(unit.getX(),unit.getY()));
                    baseModel1.attack(unit);
                }
                toRemove.add(unit);
                
            }
            
        }
        currentPlayer.getUnits().removeAll(toRemove);
        findShortestPath(b1, b2);
    }

    
    // tower attack
    /**
     * A tornyok megtámadják a unitokat
     */
    public void towerAttack(){
        ArrayList<UnitModel> toRemoveUnit = new ArrayList<>();
        int counter = 0;
        for (TowerModel tower : currentPlayer.getTowers()) {
            for (UnitModel unit : enemyPlayer.getUnits()) {
            //for(int i=0; i < enemyPlayer.getUnits().size(); i++){
                if (tower.inRange(unit)) {
                    tower.attack(unit);
                    ++counter;
                    attackFrom.add(new Position(tower.getX(),tower.getY()));
                    attackTo.add(new Position(unit.getX(),unit.getY()));
                    if (unit.getHealth() <= 0) {
                        toRemoveUnit.add(unit);
                        currentPlayer.addCoin(10); 
                    }
                }
                if(counter == tower.getTowerTargets()) break;
            }
            if (tower.getBuffed() > 0) {
                tower.setBuffed(tower.getBuffed() - 1);
            }
            if (tower.getBuffed() == 0) {
                tower.setBuffed(tower.getBuffed() - 1);
                String tmp = tower.getBuff_type();
                if (tmp == "health") {
                    tower.setHealth(tower.getHealth() / 2);
                }
                if (tmp == "target") {
                    tower.setTowerTargets(tower.getTowerTargets() / 2);
                }
                if (tmp == "attack") {
                    tower.setAttackpower(tower.getAttackpower() / 2);
                }
            }
            // System.out.println("Step health: " + tower.getHealth() + " Targets: " + tower.getTowerTargets()+ " attack: " + tower.getAttackpower());
        }
        enemyPlayer.getUnits().removeAll(toRemoveUnit);
    }
    
    public ArrayList<Position> getAttackTos(){return attackTo; }
    public ArrayList<Position> getAttackFroms(){return attackFrom; }
    public ArrayList<Position> getDeletedTowers(){return toRemoveTower; }
    public void makeDeletedTowerListEmpty(){toRemoveTower.removeAll(toRemoveTower);}

    public void makeAttackFromEmpty(){attackFrom.removeAll(attackFrom);}
    public void makeAttackToEmpty(){attackTo.removeAll(attackTo);}
    

    /**
     * TowerDestroyer unitok megtámadják a tornyokat, ha elég közel vannak
     */

    public void unitAttack(){
        for(UnitModel unit : currentPlayer.getUnits()){
            if(unit.getUnitType() == "TowerDestroyer Unit"){
                for(TowerModel tower : enemyPlayer.getTowers()){
                    if(tower.inRange(unit)){
                        unit.attack(tower);
                        attackFrom.add(new Position(unit.getX(),unit.getY()));
                        attackTo.add(new Position(tower.getX(),tower.getY()));
                        if(tower.getHealth() <= 0){
                            toRemoveTower.add(new Position(tower.getX(), tower.getY()));
                            enemyPlayer.getTowers().remove(tower);
                            currentPlayer.addCoin(50);
                        }  
                        break;
                    }
                }           
            }
        }
        //currentPlayer.getTowers().removeAll(toRemoveTower);
    }
    
    
    public int placeUnitX(){
        fillTable();
        int[] b1 = {base1[0], base1[1]};
        int[] b2 = {base2[0], base2[1]};
        findShortestPath(b1, b2);
        if (currentPlayer == player1) {
            return path[1][0];
        }
        else{
            return path[pathLength - 2][0];
        }
    }
    
    public int placeUnitY(){
        fillTable();
        int[] b1 = {base1[0], base1[1]};
        int[] b2 = {base2[0], base2[1]};
        findShortestPath(b1, b2);
        if (currentPlayer == player1) {
            return path[1][1];
        }
        else{
            return path[pathLength - 2][1];
        }
    }

    public void findShortestPath(int[] start, int[] end) {
        int[][] matrix = convertTable();
        path = cellToInt(shortestPath(matrix, start, end));
    }
    
    public void customPath(int[] start, int[] end){
        int[][] matrix = convertTable();
        tmpPath = cellToInt(shortestPath(matrix, start, end));
    }

    public Boolean checkPath(int x, int y) {
        fillTable();
        int[] b1 = {base1[0], base1[1]};
        int[] b2 = {base2[0], base2[1]};

        int[][] matrix = convertTable();
        matrix[x][y] = 0;
        if (ShortestPathBetweenCellsBFS.testPath(matrix, b1, b2)) {
            System.out.println("TRUE");
            return true;
        } else {
            System.out.println("FALSE");
            return false;
        }
    }

    public void newGame() {};
    
    /**
     * Léptetés a következő játékosra
     */
    public void nextPlayer() {
        if (player1 == currentPlayer) {
            currentPlayer = player2;
            enemyPlayer = player1;
        } else {
            currentPlayer = player1;
            enemyPlayer = player2;
        }
    };
    
    public int[] getBaseM1(){return base1;}
    public int[] getBaseM2(){return base2;}
    
    public BaseModel getBaseModel1(){return baseModel1;}
    public BaseModel getBaseModel2(){return baseModel2;}
    
    public void buff(int x, int y, String buffType){
        // System.out.println("thegame.GameEngine.buff() bufftype = " +  buffType);
        currentPlayer.setGold(currentPlayer.getGold() - 100);
        for (TowerModel t : currentPlayer.getTowers()) {
            if (t.getX() == x && t.getY() == y) {
                currentPlayer.buffTower(t, buffType);
            }
        }       
        for (UnitModel u : currentPlayer.getUnits()) {
            if (u.getX() == x && u.getY() == y) {
                currentPlayer.buffUnit(u, buffType);
            }
        }
    }
    /**
     * Torony fejlesztés: a Basic torony plusz sebzést kap, a Small torony több célpontot támad, a Strong torony több életerőt kap
     * @param tower A fejlesztendő torony
     * @param fieldType A fejlesztendő torony típusa
     */
    public void upgrade(TowerModel tower, String fieldType){
            if(!tower.isUpgraded()){
                if(fieldType == "Basic"){
                    tower.setAttackpower(tower.getAttackpower()+5);
                    tower.setUpgraded(true);
                    System.out.println(tower.getAttackpower());
                }else if(fieldType == "Small"){
                    tower.setTowerTargets(tower.getTowerTargets()*2);
                    tower.setUpgraded(true);
                }else if(fieldType == "Strong"){
                    tower.setHealth(tower.getHealth()+50);
                    tower.setUpgraded(true);
                }     
            }
    }
    
    /**
     * this method should be used in the end of the round and
     * adds coins to the player, based on their number of mines 
     */
    public void checkMines() {
        int numOfMines = getCurrentPlayer().getMines().size();
        addCoin(numOfMines * 50, getCurrentPlayer());
    }

    public Boolean isGameOver() {
        if(baseModel1.getHealth() <= 0){
            winner = "player2";
            return true;
        }else if(baseModel2.getHealth() <= 0){
            winner = "player1";
            return true;
        }
        System.out.println(baseModel2.getHealth());
        return false;
    };
    
    public void addCoin(int ammount, Player player) {
        player.addCoin(ammount);
    };
    
    public void fillTable() {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                table[i][j] = new GrassModel();
            }
        }
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                table[i][j] = obstacles[i][j];
            }
        }
        for (MineModel m : player1.getMines()) {
            int x = m.getX();
            int y = m.getY();
            table[x][y] = m;
        }
        for (MineModel m : player2.getMines()) {
            int x = m.getX();
            int y = m.getY();
            table[x][y] = m;
        }
        for (TowerModel t : player1.getTowers()) {
            int x = t.getX();
            int y = t.getY();
            table[x][y] = t;
        }
        for (TowerModel t : player2.getTowers()) {
            int x = t.getX();
            int y = t.getY();
            table[x][y] = t;
        }
        for (MineModel m : player1.getMines()) {
            int x = m.getX();
            int y = m.getY();
            table[x][y] = m;
        }
        for (MineModel m : player1.getMines()) {
            int x = m.getX();
            int y = m.getY();
            table[x][y] = m;
        }

    }

    public void readLevel() {    };
    
    public void draw() {    };
    
    public int[][] cellToInt(LinkedList<Cell> p) {
        int[][] tmp = new int[1000][2];
        int i;
        for (i = 0; i < p.size(); i++) {
            Cell get = p.get(i);
            tmp[i][0] = get.getX();
            tmp[i][1] = get.getY();
            // System.out.println("thegame.GameEngine.cellToInt() i = " + i);
            // System.out.println("tmp["+i+"][0] = " + tmp[i][0] + " ||| tmp["+i+"][1] = " + tmp[i][1] );
        }
        pathLength = i;
        return tmp;
    }

    public void getObstacles(AbstractFieldModel[][] matrix) {
        obstacles = matrix;
    }

    public void getBase1(int b1x, int b1y) {
        base1[0] = b1x;
        base1[1] = b1y;
    }

    public void getBase2(int b2x, int b2y) {
        base2[0] = b2x;
        base2[1] = b2y;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public String playerToString(Player p) {
        if (player1 == p) {
            return "Player1";
        }
        if (player2 == p) {
            return "Player2";
        } else {
            return "ERROR";
        }
    }

    public AbstractFieldModel[][] getTable() {
        return table;
    }

    public boolean isGrass(AbstractFieldModel a) {
        if (a.getGrass() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public String getWinner() {
        return winner;
    }

    public int[][] convertTable() {
        int[][] res = new int[20][20];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                if (isGrass(table[i][j])) {
                    res[i][j] = 1;
                } else {
                    res[i][j] = 0;
                }
            }
        }
        return res;
    }

}
