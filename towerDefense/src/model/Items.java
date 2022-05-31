
package model;

import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author avoczf
 */
/*
enum LevelItem {
    GRASS('*'), CASTLE1('€'), CASTLE2('$'), LAKE('O'), TOWER1('#'), TOWER2('&'), TREES('T');
    LevelItem(char rep){ representation = rep; }
    public final char representation;    
}*/

public class Items {
    public LevelItem[][] game;
    public final int rows, cols;
    Random rand = new Random();
    private int playerOneBasex, playerOneBasey, playerTwoBasex, playerTwoBasey;
    
    /**
     * letároljuk a LevelItemeket a string tömbből
     * @param gameLevelRows 
     */
    public Items(ArrayList<String> gameLevelRows)
    {
        int c = 0;
        for (String s : gameLevelRows) if (s.length() > c) c = s.length();
        rows = gameLevelRows.size();
        cols = c;
        game = new LevelItem[rows][cols];
        
        for (int i = 0; i < rows; i++){
            String s = gameLevelRows.get(i);
            for (int j = 0; j < s.length(); j++){
                switch (s.charAt(j)){
                    
                    case '*': game[i][j] = LevelItem.GRASS; break;
                    case '€': game[i][j] = LevelItem.BASE1; break;
                    case '$': game[i][j] = LevelItem.BASE2; break;
                    case 'O': game[i][j] = LevelItem.LAKE; break;
                    case 'T': game[i][j] = LevelItem.TREES; break;
                    case '#': game[i][j] = LevelItem.TOWER1; break;
                    case '&': game[i][j] = LevelItem.TOWER2; break;
                    case '@': game[i][j] = LevelItem.TOWER3; break;
                    case 'M': game[i][j] = LevelItem.MINE; break;
                   case 'Q': game[i][j] = LevelItem.B1UNIT; break;
                    case 'W': game[i][j] = LevelItem.F1UNIT; break;
                    case 'E': game[i][j] = LevelItem.S1UNIT; break;
                    case 'A': game[i][j] = LevelItem.B2UNIT; break;
                    case 'S': game[i][j] = LevelItem.F2UNIT; break;
                    case 'D': game[i][j] = LevelItem.S2UNIT; break;
                    default:  game[i][j] = LevelItem.GRASS; break;
                    
                }
            }
            /*for (int j = s.length(); j < cols; j++){
                game[i][j] = LevelItem.EMPTY;
            }*/
        }
        boardGenerator();
                
    }
        
    public void treeGenerator(){
        boolean done = false;
        while(!done){
            int tmp_x= rand.nextInt(cols-1) + 1;
            int tmp_y= rand.nextInt(rows-1) + 1;
            if (game[tmp_y][tmp_x] == LevelItem.GRASS) {
                game[tmp_y][tmp_x] = LevelItem.TREES; done = true;
            }   
        }        
    }
    
    public void lakeGenerator(){
        boolean done = false;
        while(!done){
            int tmp_x= rand.nextInt(cols-1) + 1;
            int tmp_y= rand.nextInt(rows-1) + 1;
            if (game[tmp_y][tmp_x] == LevelItem.GRASS) {
                game[tmp_y][tmp_x] = LevelItem.LAKE; done = true;
            }   
        } 
    }
    
    public void baseOneGenerator(){
        boolean done = false;
        while(!done){
            playerOneBasex = rand.nextInt(cols-1) + 1;
            playerOneBasey = rand.nextInt((rows/2 - 5) + 1) + 1;
            if (game[playerOneBasey][playerOneBasex] == LevelItem.GRASS) {
                game[playerOneBasey][playerOneBasex] = LevelItem.BASE1; done = true;
            }   
        } 
    }
    
    public void baseTwoGenerator(){
        boolean done = false;
        while(!done){
            playerTwoBasex = rand.nextInt(cols-1) + 1;
            playerTwoBasey = rand.nextInt((rows- 1 - (rows/2+5)) + 1) + (rows/2+5);
            if (game[playerTwoBasey][playerTwoBasex] == LevelItem.GRASS ) {
                game[playerTwoBasey][playerTwoBasex] = LevelItem.BASE2; done = true;
            }   
        } 
    }
    
    /*public void mineGenerator(){
         boolean done = false;
        while(!done){
             int tmp_x= rand.nextInt(cols-1) + 1;
            int tmp_y= rand.nextInt(rows-1) + 1;
            if (isFree(tmp_x,tmp_y)) {
                game[tmp_y][tmp_x] = LevelItem.MINE; done = true;
            }   
        }
    }*/
    
    public void boardGenerator(){
        baseOneGenerator();
        baseTwoGenerator();
        for(int i = 0; i < 8; ++i){
            treeGenerator();
            lakeGenerator();            
        }
    }
    /**
     * visszatér a paraméterül adott koordinátákon 
     * található LevelItem objektummal
     * @param x
     * @param y
     * @return LevelItem
     */
    public LevelItem getItem (int x, int y){
        return game[y][x];
    }
    
    public void setItem (LevelItem li, int x, int y){
        game[y][x] = li;
    }
    
    public boolean isFree(int x, int y){
        
        LevelItem li = game[y][x];
        return (li == LevelItem.GRASS);
    }
    
    public int getPlayerOneBasex() {
        return playerOneBasex;
    }

    public int getPlayerOneBasey() {
        return playerOneBasey;
    }

    public int getPlayerTwoBasex() {
        return playerTwoBasex;
    }

    public int getPlayerTwoBasey() {
        return playerTwoBasey;
    }
    
    

        
}
