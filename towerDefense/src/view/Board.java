package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import static java.awt.SystemColor.text;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Field.BasicTowerModel;
import model.Field.MineModel;
import model.Field.SmallTowerModel;
import model.Field.StrongTowerModel;
import model.Field.TowerModel;
import model.Items;
import model.LevelItem;
import res.ResourceLoader;
import thegame.GameEngine;
import model.Player;
import model.Position;
import model.Unit.BasicUnitModel;
import model.Unit.FastUnitModel;
import model.Unit.StrongUnitModel;
import model.Unit.UnitModel;
import model.Field.AbstractFieldModel;
import model.Field.GrassModel;
import model.Field.LakeModel;
import model.Field.TreeModel;
import model.Unit.TowerDestroyerUnitModel;

/**
 *
 * @author avoczf
 */


public class Board extends JPanel{
    
    //private Game game;
    private final Image grass, base1, base2, lake, tower1, tower2, tower3, trees, mine, 
            b1unit, f1unit, s1unit, b2unit, f2unit, s2unit, t1unit, t2unit;
    ArrayList<String> gameLevelRows = new ArrayList<>();
    private final int tile_size = 32;
    private Position h_pos;
    private GameEngine gameEngine;
    private Items items = null;
    private int fromX, fromY;
    private String hooveringTxt;
    ArrayList<String> multiple = new ArrayList<>();
    ArrayList<Position> twos = new ArrayList<>();
    ArrayList<Position> neighbours = new ArrayList<>();
    ArrayList<UnitModel>  pOneUnitCopy = new ArrayList<>();
    ArrayList<UnitModel> pTwoUnitCopy = new ArrayList<>();
    ArrayList<Integer> fromXArr1 = new ArrayList<>(); 
    ArrayList<Integer> fromYArr1 = new ArrayList<>();
    ArrayList<Integer> fromXArr2 = new ArrayList<>(); 
    ArrayList<Integer> fromYArr2 = new ArrayList<>();
    private Timer t = new Timer(15, new MoveListener());;
    
    public void setArraysEmpty(){
        gameLevelRows.removeAll(gameLevelRows);
        multiple.removeAll(multiple);
        twos.removeAll(twos);
        neighbours.removeAll(neighbours);
        pOneUnitCopy.removeAll(pOneUnitCopy);
        pTwoUnitCopy.removeAll(pTwoUnitCopy);
        fromXArr1.removeAll(fromXArr1);
        fromYArr1.removeAll(fromYArr1);
        fromXArr2.removeAll(fromXArr2);
        fromYArr2.removeAll(fromYArr2);
    }
    
    public void copyUnits(){
        if(gameEngine.getCurrentPlayer()==gameEngine.getPlayer1()){
            System.out.println("Elso jatekos unitjai masolodtak!");
            pOneUnitCopy = gameEngine.getPlayer1().getUnits();
            fromXArr1.removeAll(fromXArr1);
            fromYArr1.removeAll(fromYArr1);
            for(int i = 0; i < pOneUnitCopy.size(); i++){
                System.out.println("From: "+ pOneUnitCopy.get(i).getPrintX()+" "+ pOneUnitCopy.get(i).getPrintY() );
                fromX = pOneUnitCopy.get(i).getPrintX();
                fromY = pOneUnitCopy.get(i).getPrintY();            
                fromXArr1.add(fromX);
                fromYArr1.add(fromY); 
            } 
        }else{
            System.out.println("Masodik jatekos unitjai masolodtak!");
            pTwoUnitCopy = gameEngine.getPlayer2().getUnits();
            fromXArr2.removeAll(fromXArr2);
            fromYArr2.removeAll(fromYArr2);
            for(int i = 0; i < pTwoUnitCopy.size(); i++){
                System.out.println("From: "+ pTwoUnitCopy.get(i).getPrintX()+" "+ pTwoUnitCopy.get(i).getPrintY() );
                fromX = pTwoUnitCopy.get(i).getPrintX();
                fromY = pTwoUnitCopy.get(i).getPrintY();            
                fromXArr2.add(fromX);
                fromYArr2.add(fromY); 
            } 
        }    
   }
    
    public void timerStart(){t.start();}
    
    public void timerStop() {      
        
        this.refresh();
        int fine = 0;
        if(gameEngine.getCurrentPlayer()==gameEngine.getPlayer2() && !gameEngine.getPlayer1().getUnits().isEmpty()){
            for(int i = 0; i < gameEngine.getPlayer1().getUnits().size(); i++){
                if(fromXArr1.get(i)==gameEngine.getPlayer1().getUnits().get(i).getPrintX() &&
                       fromYArr1.get(i)==gameEngine.getPlayer1().getUnits().get(i).getPrintY()){
                    fine++;
                }
            }  
        } else if(gameEngine.getCurrentPlayer()==gameEngine.getPlayer1() && !gameEngine.getPlayer2().getUnits().isEmpty()) {
            for(int i = 0; i < gameEngine.getPlayer2().getUnits().size(); i++){
                if(fromXArr2.get(i)==gameEngine.getPlayer2().getUnits().get(i).getPrintX() &&
                       fromYArr2.get(i)==gameEngine.getPlayer2().getUnits().get(i).getPrintY()){
                    fine++;
                }
            }
        }
            
        if ((fine == pTwoUnitCopy.size() && gameEngine.getCurrentPlayer() ==  gameEngine.getPlayer1()) || 
               (fine == pOneUnitCopy.size() && gameEngine.getCurrentPlayer() ==  gameEngine.getPlayer2()) ) {
            System.out.println("MEGALLT");
            t.stop();
            this.refresh();
        }
    }
    
    public void setUnitPaiter(){
            t.start();
            gameEngine.nextPlayer();
    }
    
    public class MoveListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
           if(gameEngine.getCurrentPlayer()==gameEngine.getPlayer2()){
                for(int i = 0; i < gameEngine.getPlayer1().getUnits().size(); i++){
                     if(fromXArr1.get(i)<gameEngine.getPlayer1().getUnits().get(i).getPrintX()) fromXArr1.set(i, fromXArr1.get(i)+1);  
                     else if(fromXArr1.get(i)>gameEngine.getPlayer1().getUnits().get(i).getPrintX()) fromXArr1.set(i, fromXArr1.get(i)-1);
                     else if(fromYArr1.get(i)<gameEngine.getPlayer1().getUnits().get(i).getPrintY()) fromYArr1.set(i, fromYArr1.get(i)+1);  
                     else if(fromYArr1.get(i)>gameEngine.getPlayer1().getUnits().get(i).getPrintY()) fromYArr1.set(i, fromYArr1.get(i)-1);
                }
           } else if(gameEngine.getCurrentPlayer()==gameEngine.getPlayer1()){
                for(int i = 0; i < gameEngine.getPlayer2().getUnits().size(); i++){
                     if(fromXArr2.get(i)<gameEngine.getPlayer2().getUnits().get(i).getPrintX()) fromXArr2.set(i, fromXArr2.get(i)+1);  
                     else if(fromXArr2.get(i)>gameEngine.getPlayer2().getUnits().get(i).getPrintX()) fromXArr2.set(i, fromXArr2.get(i)-1);
                     else if(fromYArr2.get(i)<gameEngine.getPlayer2().getUnits().get(i).getPrintY()) fromYArr2.set(i, fromYArr2.get(i)+1);  
                     else if(fromYArr2.get(i)>gameEngine.getPlayer2().getUnits().get(i).getPrintY()) fromYArr2.set(i, fromYArr2.get(i)-1);
                }
           }
            timerStop();            
        }
    }
    
    
    public GameEngine getGameEngine() {
        return gameEngine;
    }    
    
    public Board() throws IOException{
        grass = ResourceLoader.loadImage("res/grass.png");
        base1 = ResourceLoader.loadImage("res/castle1.png");
        base2 = ResourceLoader.loadImage("res/castle2.png");
        lake = ResourceLoader.loadImage("res/lake.png");
        tower1 = ResourceLoader.loadImage("res/tower1.png");
        tower2 = ResourceLoader.loadImage("res/tower2.png");
        tower3 = ResourceLoader.loadImage("res/tower3.png");
        trees = ResourceLoader.loadImage("res/trees.png");
        mine = ResourceLoader.loadImage("res/mine.png");
        b2unit = ResourceLoader.loadImage("res/b2unit.png");
        f2unit = ResourceLoader.loadImage("res/f2unit.png");  
        s2unit = ResourceLoader.loadImage("res/s2unit.png"); 
        b1unit = ResourceLoader.loadImage("res/b1unit.png");
        f1unit = ResourceLoader.loadImage("res/f1unit.png");  
        s1unit = ResourceLoader.loadImage("res/s1unit.png");
        t1unit = ResourceLoader.loadImage("res/t1unit.png");
        t2unit = ResourceLoader.loadImage("res/t2unit.png");
        gameEngine = new GameEngine();
        readLevels();
    }
    
    public boolean refresh(){
        //if (!game.isLevelLoaded()) return false;
        Dimension dim = new Dimension(20 * 32, 20 * 32);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setSize(dim);
        repaint();
        return true;
    }
    
    private String readNextLine(Scanner sc){
        String line = "";
        while (sc.hasNextLine() && line.trim().isEmpty()){
            line = sc.nextLine();
        }
        return line;
    }
        
    public boolean towerInRange(Player p, int x, int y){
        for(int n = 0; n<p.getTowers().size(); ++n){
            for(int i = x-2; i <= x+2; ++i){
                for(int j = y-2; j <= y+2; ++j){
                    if(p.getTowers().get(n).getX() == i && p.getTowers().get(n).getY() == j)
                        return true;
                }
            }     
        }            

        if(p == gameEngine.getPlayer1()){
            for(int i = x-2; i <= x+2; ++i){
                for(int j = y-2; j <= y+2; ++j){
                    if(gameEngine.getBaseM1()[0] == i && gameEngine.getBaseM1()[1] == j)
                        return true;
                } 
            }     
        }
        if(p == gameEngine.getPlayer2()){
            for(int i = x-2; i <= x+2; ++i){
                for(int j = y-2; j <= y+2; ++j){
                    if(gameEngine.getBaseM2()[0] == i && gameEngine.getBaseM2()[1] == j)
                        return true;
                }
            }     
        }
        return false;
    }
    
    /**
     * Lehelyez egy paraméterül kapott típusú torony itemet a paraméterül kapott x és y koordinátákra
     * @param x
     * @param y
     * @param type 
     */
    public void placeTower(int x, int y, String type){
        x = x / 32;
        y = y / 32;
        Position tmpPos = new Position(x,y);
        if(items.getItem(x, y) == LevelItem.GRASS && gameEngine.checkPath(x, y) && (towerInRange(gameEngine.getCurrentPlayer(),x,y))){//
            if(items.getItem(x, y) == LevelItem.GRASS && gameEngine.checkPath(x, y)){
              if (type == "Basic" && gameEngine.getCurrentPlayer().getGold() >= 100) {
                  items.setItem(LevelItem.TOWER2, x, y);
                  gameEngine.getCurrentPlayer().buyTower(new BasicTowerModel(x, y));
              }
              if (type == "Small" && gameEngine.getCurrentPlayer().getGold() >= 80) {
                  items.setItem(LevelItem.TOWER1, x, y);
                  gameEngine.getCurrentPlayer().buyTower(new SmallTowerModel(x, y));
              }
              if (type == "Strong" && gameEngine.getCurrentPlayer().getGold() >= 150) {
                  items.setItem(LevelItem.TOWER3, x, y);
                  gameEngine.getCurrentPlayer().buyTower(new StrongTowerModel(x, y));
              }
          }  
        } 
        else{
            showMessage("Tower cannot be placed be here!");
        }
        this.refresh();
    }
    
    /**
     * Megnézi, hogy a saját tornyodat próbálod-e fejleszteni
     * @param x
     * @param y 
     */
    public void upgradeTower(int x, int y){
        x = x / 32;
        y = y / 32;
        
        if(items.getItem(x, y) == LevelItem.TOWER1){
            for(TowerModel tower : gameEngine.getCurrentPlayer().getTowers()){
                if(tower.getX() == x && tower.getY() == y){
                    gameEngine.upgrade(tower, "Small"); 
                }
            }
        }else if(items.getItem(x, y) == LevelItem.TOWER2){
            for(TowerModel tower : gameEngine.getCurrentPlayer().getTowers()){
                if(tower.getX() == x && tower.getY() == y){
                    gameEngine.upgrade(tower, "Basic");  
                }
            }
        }else if(items.getItem(x, y) == LevelItem.TOWER3){
            for(TowerModel tower : gameEngine.getCurrentPlayer().getTowers()){
                if(tower.getX() == x && tower.getY() == y){
                    gameEngine.upgrade(tower, "Strong"); 
                }
            }
        }
        this.refresh();
    }
    
    public void buff(int x, int y, String type){
        x = x / 32;
        y = y / 32;
        if (gameEngine.getCurrentPlayer().getGold() >= 100) {
            gameEngine.buff(x, y, type);
        }
    }
    
    /**
     * lecsekkolja, hogy a kapott koordinátákon van-e több unit, 
     * ha van, kirajzolja melyik játékosé és a unit fajtáját
     * @param _x
     * @param _y 
     */
    public void getCell(int _x, int _y){
        String tmp;
        _x = _x / 32;
        _y = _y / 32;
        h_pos = new Position(_x,_y);
        ArrayList<UnitModel> pOneunits = new ArrayList<>();
        pOneunits = gameEngine.getPlayer1().getUnits();
        ArrayList<UnitModel> pTwounits = new ArrayList<>();
        pTwounits = gameEngine.getPlayer2().getUnits();
        multiple.clear(); 
        hooveringTxt = "";
        int counter = 0;
        Position tmpPoint = new Position(_x, _y);
        if((_x < 0 || _y < 0) && twos.contains(tmpPoint)){
        } else {
            for(int i = 0 ; i < pOneunits.size(); ++i){
                if(pOneunits.get(i).getX() == _x && pOneunits.get(i).getY() == _y){
                   tmp = "\nP1: " + pOneunits.get(i).getUnitType()+ System.lineSeparator();
                   multiple.add(tmp); 
                   hooveringTxt += tmp;
                   //counter++;
                }                    
            }
            for(int i = 0 ; i < pTwounits.size(); ++i){
                if(pTwounits.get(i).getX() == _x && pTwounits.get(i).getY() == _y){
                   tmp = "\nP2: " + pTwounits.get(i).getUnitType()+System.lineSeparator();
                   multiple.add(tmp);
                   hooveringTxt += tmp;
                   //counter++;
                }                    
            }
            
        }      
        this.refresh();
    }
    
    /**
     * Levesz egy tornyot a paraméterül kapott koordinátákról
     * @param x
     * @param y 
     */
    public void removeTower(int x, int y){
        x = x / 32;
        y = y / 32;
        TowerModel toRemove = null;
        
        if(items.getItem(x, y) == LevelItem.TOWER1){
            for(TowerModel tower : gameEngine.getCurrentPlayer().getTowers()){
                if(tower.getX() == x && tower.getY() == y){
                    items.setItem(LevelItem.GRASS, x, y);
                    toRemove = tower;
                }
            }
        }else if(items.getItem(x, y) == LevelItem.TOWER2){
            for(TowerModel tower : gameEngine.getCurrentPlayer().getTowers()){
                if(tower.getX() == x && tower.getY() == y){
                    items.setItem(LevelItem.GRASS, x, y);
                     toRemove = tower;
                }
            }
        }else if(items.getItem(x, y) == LevelItem.TOWER3){
            for(TowerModel tower : gameEngine.getCurrentPlayer().getTowers()){
                if(tower.getX() == x && tower.getY() == y){
                    items.setItem(LevelItem.GRASS, x, y);
                     toRemove = tower;
                }
            }
        }
        if(toRemove != null)
            gameEngine.getCurrentPlayer().sellTower(toRemove);
        this.refresh();
    }
        
    public void placeMine(int x, int y){
        x = x / 32;
        y = y / 32;
        if(items.getItem(x, y) == LevelItem.GRASS && gameEngine.checkPath(x, y)){
            if (gameEngine.getCurrentPlayer().getGold() >= 500) {
                items.setItem(LevelItem.MINE, x, y);
                gameEngine.getCurrentPlayer().buyMine(new MineModel(x,y));
            }
        }        
        this.refresh();
    }
    
    
    public void endTurn(){
        gameEngine.makeAttackFromEmpty();
        gameEngine.makeAttackToEmpty();
        gameEngine.towerAttack();
        gameEngine.unitAttack();
        
        copyUnits();
        gameEngine.step(); 
        if(gameEngine.isGameOver()){
            showGameOverMessage(gameEngine.getWinner());
        }
        checkForTwoUnits();
        setUnitPaiter();
        this.refresh();
        if(!gameEngine.getAttackFroms().isEmpty()){
            System.out.println("tamadas: " + gameEngine.getAttackFroms().get(0).x + 
                    " " + gameEngine.getAttackFroms().get(0).y);
        }    
        setGrass();
        copyUnits();
        gameEngine.makeDeletedTowerListEmpty();
        
    }
    
    public void setGrass(){
        if(!gameEngine.getDeletedTowers().isEmpty()){
            for(int i=0; i<gameEngine.getDeletedTowers().size(); i++){
                items.setItem(LevelItem.GRASS, gameEngine.getDeletedTowers().get(i).x,gameEngine.getDeletedTowers().get(i).y ); 
            }
        }
    }    
    
    public void checkForTwoUnits(){
        
        ArrayList<UnitModel> pOneunits = new ArrayList<>();
        pOneunits = gameEngine.getPlayer1().getUnits();
        ArrayList<UnitModel> pTwounits = new ArrayList<>();
        pTwounits = gameEngine.getPlayer2().getUnits();
        twos.removeAll(twos);
        for (int i = 0; i < pOneunits.size(); i++){
           for (int j = 0;j < pOneunits.size(); j++) {
                if (pOneunits.get(i).getX() == pOneunits.get(j).getX() && pOneunits.get(i).getY() == pOneunits.get(j).getY() && i != j) {
                    Position p = new Position(pOneunits.get(i).getX(),pOneunits.get(i).getY());
                    twos.add(p);
                }
            }            
        }
        
        for (int i = 0; i < pTwounits.size(); i++){
            for (int j = 0;j < pTwounits.size(); j++) {
                if (pTwounits.get(i).getX() == pTwounits.get(j).getX() && pTwounits.get(i).getY() == pTwounits.get(j).getY() && i != j) {
                    Position p = new Position(pTwounits.get(i).getX(),pTwounits.get(i).getY());
                    twos.add(p);
                }
            }            
        }
        
        for (int i = 0; i < pOneunits.size(); i++){
            for (int j = 0;j < pTwounits.size(); j++) {
                 if (pOneunits.get(i).getX() == pTwounits.get(j).getX() && pOneunits.get(i).getY() == pTwounits.get(j).getY()) {
                     Position p = new Position(pOneunits.get(i).getX(),pOneunits.get(i).getY());
                     twos.add(p);
                 }
             }            
        }
    } 
    
    public Image unitType(int x,int y){
        String str;
        for (int i = 0; i < gameEngine.getPlayer1().getUnits().size(); i++){
            if(gameEngine.getPlayer1().getUnits().get(i).getX() == x &&
                 gameEngine.getPlayer1().getUnits().get(i).getY() == y ){
                str = gameEngine.getPlayer1().getUnits().get(i).getUnitType();
                switch(str){
                    case "Basic Unit":
                        return b1unit;                        
                    case "Fast Unit":
                        return f1unit;
                    case "Strong Unit":
                        return s1unit;
                    case "TowerDestroyer Unit":
                        return t1unit;
                }                
            }       
        }   
        for (int i = 0; i < gameEngine.getPlayer2().getUnits().size(); i++){
            if(gameEngine.getPlayer2().getUnits().get(i).getX() == x &&
                 gameEngine.getPlayer2().getUnits().get(i).getY() == y ){
                str = gameEngine.getPlayer2().getUnits().get(i).getUnitType();
                switch(str){
                    case "Basic Unit":
                        return b2unit;                        
                    case "Fast Unit":
                        return f2unit;
                    case "Strong Unit":
                        return s2unit;
                    case "TowerDestroyer Unit":
                        return t2unit;
                }                
                
            }
        } 
        return b1unit;
        
    }
    /**
     * Lehelyez egy unitot a legrövidebb út első mezőjére
     * @param type A unit típusa
     */
    public void placeUnit(String type){
        if(gameEngine.getCurrentPlayer() == gameEngine.getPlayer1()){
            if(type == "Basic" && gameEngine.getCurrentPlayer().getGold() >= 60){
                items.setItem(LevelItem.B1UNIT, items.getPlayerOneBasex(), items.getPlayerOneBasey()-1);
                gameEngine.getCurrentPlayer().buyUnit(new BasicUnitModel(gameEngine.getCurrentPlayer(), gameEngine.placeUnitX(), gameEngine.placeUnitY()));
                System.out.println("place unit x= " + gameEngine.placeUnitX() + " y= " + gameEngine.placeUnitY());
            }
            if(type == "Fast" && gameEngine.getCurrentPlayer().getGold() >= 80){
                items.setItem(LevelItem.F1UNIT, items.getPlayerOneBasex(), items.getPlayerOneBasey()-1);
                gameEngine.getCurrentPlayer().buyUnit(new FastUnitModel(gameEngine.getCurrentPlayer(),gameEngine.placeUnitX(), gameEngine.placeUnitY()));
            }
            if(type == "Strong" && gameEngine.getCurrentPlayer().getGold() >= 100){
                    items.setItem(LevelItem.S1UNIT, items.getPlayerOneBasex(), items.getPlayerOneBasey()-1);
                    gameEngine.getCurrentPlayer().buyUnit(new StrongUnitModel(gameEngine.getCurrentPlayer(),gameEngine.placeUnitX(), gameEngine.placeUnitY()));
            }
            if(type == "TowerDestroyer" && gameEngine.getCurrentPlayer().getGold() >= 150){
                    items.setItem(LevelItem.S1UNIT, items.getPlayerOneBasex(), items.getPlayerOneBasey()-1);
                    gameEngine.getCurrentPlayer().buyUnit(new TowerDestroyerUnitModel(gameEngine.getCurrentPlayer(),gameEngine.placeUnitX(), gameEngine.placeUnitY()));
            }
        }else{
            if(type == "Basic" && gameEngine.getCurrentPlayer().getGold() >= 60){
                items.setItem(LevelItem.B2UNIT, items.getPlayerTwoBasex(), items.getPlayerTwoBasey()-1);
                gameEngine.getCurrentPlayer().buyUnit(new BasicUnitModel(gameEngine.getCurrentPlayer(), gameEngine.placeUnitX(), gameEngine.placeUnitY()));
                System.out.println("p2 place unit x= " + gameEngine.placeUnitX() + " y= " + gameEngine.placeUnitY());
            }
            if(type == "Fast" && gameEngine.getCurrentPlayer().getGold() >= 80){
                items.setItem(LevelItem.F2UNIT, items.getPlayerTwoBasex(), items.getPlayerTwoBasey()-1);
                gameEngine.getCurrentPlayer().buyUnit(new FastUnitModel(gameEngine.getCurrentPlayer(), gameEngine.placeUnitX(), gameEngine.placeUnitY()));
            }
            if(type == "Strong" && gameEngine.getCurrentPlayer().getGold() >= 100){
                items.setItem(LevelItem.S2UNIT, items.getPlayerTwoBasex(), items.getPlayerTwoBasey()-1);
                gameEngine.getCurrentPlayer().buyUnit(new StrongUnitModel(gameEngine.getCurrentPlayer(), gameEngine.placeUnitX(), gameEngine.placeUnitY()));
            }
            if(type == "TowerDestroyer" && gameEngine.getCurrentPlayer().getGold() >= 150){
                    items.setItem(LevelItem.S1UNIT, items.getPlayerTwoBasex(), items.getPlayerTwoBasey()-1);
                    gameEngine.getCurrentPlayer().buyUnit(new TowerDestroyerUnitModel(gameEngine.getCurrentPlayer(),gameEngine.placeUnitX(), gameEngine.placeUnitY()));
            }
        }
        
        copyUnits();
        this.refresh();
        //repaint();
    }    
    
    /**
     * pálya beolvasása txt fileból
     */
    public void readLevels(){
        InputStream is;// = cl.getResourceAsStream("res/levels.txt");
        is = ResourceLoader.loadResource("res/levels.txt");
        
        try (Scanner sc = new Scanner(is)){
            String line = readNextLine(sc);                 
            
            while (!line.isEmpty()){
                                
                gameLevelRows.clear();
                line = readNextLine(sc);
                while (!line.isEmpty() && line.trim().charAt(0) != ';'){
                    gameLevelRows.add(line);                    
                    line = readNextLine(sc);
                }               
                
            }
            
        } catch (Exception e){
            System.out.println("Ajaj");
        }
        items = new Items(gameLevelRows);
        
        AbstractFieldModel matrix[][] = new AbstractFieldModel[20][20];
        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 20; j++) {
                if (items.getItem(i, j) == LevelItem.TREES) {
                    matrix[i][j] = new TreeModel();
                }
                else if (items.getItem(i, j) == LevelItem.LAKE) {
                    matrix[i][j] = new LakeModel();
                }
                else if (items.getItem(i, j) == LevelItem.BASE1) {
                    gameEngine.getBase1(i, j);
                    matrix[i][j] = new GrassModel();
                }
                else if (items.getItem(i, j) == LevelItem.BASE2) {
                    gameEngine.getBase2(i, j);
                    matrix[i][j] = new GrassModel();
                }
                else{
                    matrix[i][j] = new GrassModel();
                }
                
            }
        }
        gameEngine.getObstacles(matrix);
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        
        Graphics2D gr = (Graphics2D)g;
        int w = 20;
        int h = 20;
        for(int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++){
                gr.drawImage(grass, x * 32, y * 32, 32, 32, null);
            }
        }
        
        
        ArrayList<UnitModel> pOneunits = gameEngine.getPlayer1().getUnits();
        ArrayList<UnitModel> pTwounits = gameEngine.getPlayer2().getUnits();
                
        for (int y = 0; y < h; y++){
            for (int x = 0; x < w; x++){
               LevelItem li = items.getItem(x, y);
               Image img = null;
               switch(li) {
                   case GRASS: img = grass; break;
                   case BASE1: img = base1; break;
                   case BASE2: img = base2; break;
                   case TOWER1: img = tower1; break;
                   case TOWER2: img = tower2; break;
                   case TOWER3: img = tower3; break;
                   case LAKE: img = lake; break;
                   case TREES: img = trees; break;
                   case MINE: img = mine; break;
                   case B1UNIT: img = grass; break;
                   case S1UNIT: img = grass; break;
                   case F1UNIT: img = grass; break;
                   case B2UNIT: img = grass; break;
                   case S2UNIT: img = grass; break;
                   case F2UNIT: img = grass; break;
                   case T1UNIT: img = grass; break;
                   case T2UNIT: img = grass; break;
               }
               if (img == null) continue;
            gr.drawImage(img, x * 32, y * 32, 32, 32, null);
            }
        }      
        
        
        g.setColor(Color.DARK_GRAY);
        if(twos.size()>0){
           for(int i = 0; i < twos.size(); i++){
                String counter = " +";
                gr.drawString(counter, twos.get(i).x*32, twos.get(i).y*32);
            } 
        }
        Color bgColor = new Color(102,102,10);
        Color textColor = Color.WHITE;
        FontMetrics fm = g.getFontMetrics();
        
        for(int i = 0; i < gameEngine.getPlayer1().getTowers().size(); i++){
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(gameEngine.getPlayer1().getTowers().get(i).getX() *32,
            gameEngine.getPlayer1().getTowers().get(i).getY()*32,32,5);
            g.setColor(Color.RED);
            g.fillRect(gameEngine.getPlayer1().getTowers().get(i).getX() *32 + 1,
            gameEngine.getPlayer1().getTowers().get(i).getY()*32+1,gameEngine.getPlayer1().getTowers().get(i).getHealth()/3,3);     
        }
        
        int[] base1 = gameEngine.getBaseM1();
        int[] base2 = gameEngine.getBaseM2();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(base1[0]*32,base1[1]*32,32,5);
        g.setColor(Color.RED);
        g.fillRect(base1[0]*32+1,base1[1]*32+1,gameEngine.getBaseModel1().getHealth()/3,3);     
        
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(base2[0]*32,base2[1]*32,32,5);
        g.setColor(Color.BLUE);
        g.fillRect(base2[0]*32+1,base2[1]*32+1,gameEngine.getBaseModel2().getHealth()/3,3); 
        
        for(int i = 0; i < gameEngine.getPlayer2().getTowers().size(); i++){
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(gameEngine.getPlayer2().getTowers().get(i).getX() *32,
            gameEngine.getPlayer2().getTowers().get(i).getY()*32,32,5);
            g.setColor(Color.BLUE);
            g.fillRect(gameEngine.getPlayer2().getTowers().get(i).getX() *32 + 1,
            gameEngine.getPlayer2().getTowers().get(i).getY()*32+1,gameEngine.getPlayer2().getTowers().get(i).getHealth()/3,3);     
        }        
        
        for(int i = 0; i < multiple.size(); i++){
            Rectangle2D rect = fm.getStringBounds(multiple.get(i), g);
            g.setColor(bgColor);
            g.fillRect(h_pos.x*32,
            h_pos.y*32+i * (int) rect.getHeight() - fm.getAscent(),
            (int) rect.getWidth(),
            (int) rect.getHeight());
            g.setColor(textColor);
            gr.drawString(multiple.get(i), h_pos.x*32, h_pos.y*32+i * (int) rect.getHeight());
        }
        
        for(int i = 0; i < pOneunits.size(); i++){
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(pOneunits.get(i).getX() *32,
            pOneunits.get(i).getY()*32,32,5);
            g.setColor(Color.RED);
            g.fillRect(pOneunits.get(i).getX() *32 +1,
            pOneunits.get(i).getY()*32+1,pOneunits.get(i).getHealth()/3,3);     
        }
        

        
        for(int i = 0; i < pTwounits.size(); i++){
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(pTwounits.get(i).getX() *32,
            pTwounits.get(i).getY()*32,32,5);
            g.setColor(Color.BLUE);
            g.fillRect(pTwounits.get(i).getX() *32 +1,
            pTwounits.get(i).getY()*32+1,pTwounits.get(i).getHealth()/3,3);                         
        }
        
        
 
        if(!gameEngine.getPlayer1().getUnits().isEmpty()){            
            for(int i = 0; i < gameEngine.getPlayer1().getUnits().size(); i++){                
                Image img = unitType(gameEngine.getPlayer1().getUnits().get(i).getX(), gameEngine.getPlayer1().getUnits().get(i).getY());
                gr.drawImage(img, fromXArr1.get(i), fromYArr1.get(i), 32, 32, null);
            }
        } 
        if (!gameEngine.getPlayer2().getUnits().isEmpty()) {
            for(int i = 0; i < gameEngine.getPlayer2().getUnits().size(); i++){                
                Image img = unitType(gameEngine.getPlayer2().getUnits().get(i).getX(), gameEngine.getPlayer2().getUnits().get(i).getY());
                gr.drawImage(img, fromXArr2.get(i), fromYArr2.get(i), 32, 32, null);
            }
        }
        
        if(!gameEngine.getAttackFroms().isEmpty()){
            g.setColor(Color.black);
            for(int i=0; i<gameEngine.getAttackFroms().size(); i++){
                g.drawLine(gameEngine.getAttackFroms().get(i).x*32+16, gameEngine.getAttackFroms().get(i).y*32+16, 
                        gameEngine.getAttackTos().get(i).x*32+16, gameEngine.getAttackTos().get(i).y*32+16);
            }
        } 
        
        
    }
    private void showGameOverMessage(String winner) {
            JOptionPane.showMessageDialog(null,
                    "Game over. Winner: " + winner);
    }
    private void showMessage(String msg) {
            JOptionPane.showMessageDialog(null,
                     msg);
    }
        
}

