package model.Field;

import java.util.List;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import model.Unit.UnitModel;
import res.ResourceLoader;

public abstract class AbstractFieldModel 
{
    protected int x, y;
    protected int grass;

    public int getGrass() {
        return grass;
    }
    protected int player, buffed, health, attackpower;

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAttackpower(int attackpower) {
        this.attackpower = attackpower;
    }

    public void setBuffed(int buffed) {
        this.buffed = buffed;
    }

    public void setBuff_type(String buff_type) {
        this.buff_type = buff_type;
    }
    private String buff_type;
    //private final HashMap<String, HashMap<Integer, GameLevel>> gameLevels;
    
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPlayer() {
        return player;
    }

    public int getHealth() {
        return health;
    }

    public int getAttackpower() {
        return attackpower;
    }

    public int getBuffed() {
        return buffed;
    }

    /*public int getCost() {
        return cost;
    
    }*/

    public String getBuff_type() {
        return buff_type;
    }
    
    public abstract void attack(UnitModel unit);
    
    
    
}

