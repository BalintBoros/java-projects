/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package labyrinthgame.model;

import gui.GameWindow;
import gui.graphical.GAbstractField;
import gui.graphical.GAbstractGameObject;
import gui.graphical.GDragon;
import gui.graphical.GEmptyField;
import gui.graphical.GPlayer;
import gui.graphical.GWallField;
import gui.graphical.MainGraphical;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import labyrinthgame.model.field.AbstractField;
import labyrinthgame.model.field.EmptyField;
import labyrinthgame.model.field.WallField;
import labyrinthgame.model.field.exception.MoveNotAllowedException;
import labyrinthgame.model.gameobject.AbstractGameObject;
import labyrinthgame.model.gameobject.Dragon;
import labyrinthgame.model.gameobject.Player;
import labyrinthgame.model.gameobject.exception.SetDirectionNotAllowedException;

/**
 *
 */
public class MainModel {
    List<List<AbstractField>> rows = new ArrayList<>();
    List<GAbstractField> guiFieldList = new ArrayList<>();
    List<AbstractGameObject> abstractGameObjectList = new ArrayList<>();
    private int level = 5;

    public int getLevel() {
        return level;
    }

    public void setLevel() {
        this.level = level+1;
    }

    public List<GAbstractField> getGuiFieldList() {
        return guiFieldList;
    }

    public void setDragonAndMove(){
        int rowsLength = rows.get(1).size();
        AbstractField abstractField = rows.get(1).get(rowsLength-2);
        GAbstractGameObject gDragon = new GDragon();
        AbstractGameObject dragon = new Dragon(abstractField, gDragon);
        try {
            abstractField.moveGameObjectToHere(dragon);
        } catch (MoveNotAllowedException ex) {
            ex.printStackTrace();
        }
        abstractGameObjectList.add(dragon);
    }
    
    public void setPlayerAndMove() {
        int rowsNum = rows.size();
        AbstractField abstractField = rows.get(rowsNum-2).get(1);
        
        GAbstractGameObject gPlayer = new GPlayer();
        AbstractGameObject player = new Player(abstractField, gPlayer);
        try {
            abstractField.moveGameObjectToHere(player);
        } catch (MoveNotAllowedException ex) {
            ex.printStackTrace();
        }
        abstractGameObjectList.add(player);
        
        /*
        abstractField = rows.get(1).get(28);
        GAbstractGameObject gDragon = new GDragon();
        AbstractGameObject dragon = new Dragon(abstractField, gDragon);
        try {
            abstractField.moveGameObjectToHere(dragon);
        } catch (MoveNotAllowedException ex) {
            ex.printStackTrace();
        }
        abstractGameObjectList.add(dragon);
        */
        //printField();
        
        /*for(int i = 0; i < 10; i++) {
            System.out.println("-----------STEP=" + i + "-----------");
            player.move();
            printField();
            if(i == 6) {
                try {
                    player.setMoveDirection(1);
                } catch (SetDirectionNotAllowedException ex) {
                    ex.printStackTrace();
                }
            }
        }*/
    }
    
    public void initNeighbors() {
        for(int i = 0; i < rows.size(); i++) {
            for(int j = 0; j < rows.get(i).size(); j++) {
                AbstractField currentField = rows.get(i).get(j);
                List<AbstractField> neighbors = new ArrayList<>();
                // direction UP (0)
                if(i == 0) {
                    neighbors.add(null);
                } else {
                    neighbors.add(rows.get(i - 1).get(j));
                }
                // direction RIGHT (1)
                if(j == rows.get(i).size() - 1) {
                    neighbors.add(null);
                } else {
                    neighbors.add(rows.get(i).get(j + 1));
                }
                // direction DOWN (2)
                if(i == rows.size() - 1) {
                    neighbors.add(null);
                } else {
                    neighbors.add(rows.get(i + 1).get(j));
                }
                // direction LEFT (3)
                if(j == 0) {
                    neighbors.add(null);
                } else {
                    neighbors.add(rows.get(i).get(j - 1));
                }
                
                currentField.setNeighbors(neighbors);
            }                       
        }
    }
    
    public void createRow(String line) {
        if(line != null && !line.isBlank()) {
            List<AbstractField> row = new ArrayList<>();
            char[] chars = line.toCharArray();
            for (char ch: chars) {
                if(ch == 'x') {
                    WallField wallField = new WallField();                    
                    GAbstractField gWallField = new GWallField(row.size(), rows.size(), wallField);
                    row.add(wallField);   
                    guiFieldList.add(gWallField);
                } else if(ch == ' ') {
                    EmptyField emptyField = new EmptyField();
                    GAbstractField gEmptyField = new GEmptyField(row.size(), rows.size(), emptyField);
                    row.add(emptyField);
                    //guiFieldList.add(gWallField);
                    guiFieldList.add(gEmptyField);
                } else {
                    throw new RuntimeException("Unknown char: >>" + ch + "<<");
                }
            }
            rows.add(row);
        }
    }
            
    public String readLevel() throws FileNotFoundException, IOException {
        String lvl = "level0" + String.valueOf(getLevel()) + ".txt";
        BufferedReader br = new BufferedReader(new FileReader(lvl));
        StringBuilder sb = new StringBuilder();
        try {
            String line = null;
            do {
                line = br.readLine();
                if(line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    createRow(line);
               }
            } while (line != null);             
        } finally {
            br.close();
        }
        
        return sb.toString();
    }
    
    public void printField() {
        for(List<AbstractField> row : rows) {
            for(AbstractField field : row) {
                System.out.print(field.getMarker());
            }
            System.out.println();
        }
    }
    
    //MainGraphical mg = new MainGraphical()
    //MainGraphical mainGraphical = new  MainGraphical();
    //mainGraphical.addFields();
    public void initModel() throws Exception {       
        readLevel();
        initNeighbors();
        setPlayerAndMove();
        setDragonAndMove();
        //mainGraphical.GPrintField(rows);
    }
    
    public int getRowCount() {
        return rows.size();
    }
    
    public int getColumnCount() {
        if(rows.size() > 0) {
            return rows.get(0).size();
        } else {
            return 0;
        }
    }
    
    public void moveElements(int direction) throws SetDirectionNotAllowedException {
        for(AbstractGameObject abstractGameObject : abstractGameObjectList) {
            if(!abstractGameObject.getDragon()){
                abstractGameObject.setMoveDirection(direction);
                abstractGameObject.move();
                
            }            
        }
    }
    
    public void moveDragon (int direction) throws SetDirectionNotAllowedException {
        for(AbstractGameObject abstractGameObject : abstractGameObjectList) {
            if(abstractGameObject.getDragon()){
                abstractGameObject.setMoveDirection(direction);
                abstractGameObject.move();
                
                
            }            
        }
    }
    public boolean moVed(){
        for(AbstractGameObject abstractGameObject : abstractGameObjectList) {
            if(abstractGameObject.getDragon()){
                return abstractGameObject.isMoved();                  
                
            }
        }
        return false;
    }
    
    public boolean gameOver(int direction){
        //AbstractGameObject abstractGameObject = abstractGameObjectList[0]; 
        
        AbstractGameObject abstractGameObject1 = abstractGameObjectList.get(0);
        AbstractField currentField1 = abstractGameObject1.getCurrentField();
        AbstractGameObject abstractGameObject2 = abstractGameObjectList.get(1);
        AbstractField currentField2 = abstractGameObject2.getCurrentField();
        
        if(currentField1.getNeighbors().contains(currentField2)){
            return true;
        }
        return false;
    }
    
    public boolean win(){
        int rowsLength = rows.size();
        AbstractField finish = rows.get(1).get(rowsLength-1);
        for(AbstractGameObject abstractGameObject : abstractGameObjectList) {
            if(!abstractGameObject.getDragon()){
                AbstractField playerField = abstractGameObject.getCurrentField();
                if(playerField.getNeighbors().contains(finish)) return true;
            }
        }
        return false;
    }
    
    public void reStart() throws MoveNotAllowedException {
         for(AbstractGameObject abstractGameObject : abstractGameObjectList){
             AbstractField currenfield = abstractGameObject.getCurrentField();
             currenfield.removeGameObjectFromHere();
        }        
        abstractGameObjectList.clear();
        setPlayerAndMove();
        setDragonAndMove();
        
    }
    
    public void newLevel()throws MoveNotAllowedException, IOException {
        for(AbstractGameObject abstractGameObject : abstractGameObjectList){
             AbstractField currenfield = abstractGameObject.getCurrentField();
             currenfield.removeGameObjectFromHere();
        }
        abstractGameObjectList.clear();
        readLevel();
        initNeighbors();
        setPlayerAndMove();
        setDragonAndMove();
    }
    
    public AbstractField getPlayerField(){
        for(AbstractGameObject abstractGameObject : abstractGameObjectList) {
            if(!abstractGameObject.getDragon()){
                return abstractGameObject.getCurrentField();
            }
        }
        return null;
    }
}
