/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Unit;
import model.Field.AbstractFieldModel;
import java.util.ArrayList;
import model.Player;

/**
 *
 * @author fodor
 */
public abstract class UnitModel {
    protected Player owner;
    private int x, y, printX, printY;
    private int health, buffed, attackpower, cost, speed;
    private String buff_type;
    protected String unitType;
    
    public UnitModel(int health, int attackpower, int cost, int speed, int x, int y){
        this.health = health;
        this.cost = cost;
        this.attackpower = attackpower;
        this.speed = speed;
        this.x = x;
        this.y = y;
        printX = x * 32;
        printY = y * 32;
        buffed = - 1;
    }
    
    
    public abstract int move();
    public abstract void attack(AbstractFieldModel obj);
    public ArrayList<Integer> shortestPath(){
        ArrayList<Integer> path = new ArrayList<Integer>(100);
        return path;
    };

    public Player getOwner() {
        return owner;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public int getBuffed() {
        return buffed;
    }
    
    public String getUnitType() {
        return unitType;
    }

    public int getAttackpower() {
        return attackpower;
    }

    public int getCost() {
        return cost;
    }

    public int getSpeed() {
        return speed;
    }
    
    public void setX(int x) {
        this.x = x;
        printX = x*32;
    }

    public void setY(int y) {
        this.y = y;
        printY = y*32;
    }
    
    public void setBuffed(int buffed) {
        this.buffed = buffed;
    }

    public void setAttackpower(int attackpower) {
        this.attackpower = attackpower;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setBuff_type(String buff_type) {
        this.buff_type = buff_type;
    }

    public String getBuff_type() {
        return buff_type;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    
    public void increasePrintX(){this.printX++;}
    public void increasePrintY(){this.printY++;}
    public void lowerPrintX(){this.printX--;}
    public void lowerPrintY(){this.printY--;}
    public int getPrintX(){return printX;}
    public int getPrintY(){return printY;}
    
}
