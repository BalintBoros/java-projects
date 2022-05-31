/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.ArrayList;
import model.Field.MineModel;
import model.Field.TowerModel;
import model.Unit.UnitModel;

/**
 * Osztály a játékosok modellezésére
 * @author fodor
 */
public class Player {
    private int gold;
    private ArrayList<TowerModel> towers;
    private ArrayList<UnitModel> units;
    private ArrayList<MineModel> mines;
    
    public Player(){
        gold = 1000;
        units = new ArrayList<>();
        towers = new ArrayList<>();
        mines = new ArrayList<>();
    }
    
    /**
     * Kiveszi a paraméterül kapott tornyot a listából és hozzáadja a játékos aranyához a torony árának a felét
     * @param tower 
     */
    public void sellTower(TowerModel tower){
        setGold(gold+tower.getCost()/2);
        towers.remove(tower);
    };
    public void upgradeTower(TowerModel tower){};
    
    public void buffUnit(UnitModel unit, String buffType){
        if (buffType == "health") {
            unit.setHealth(unit.getHealth() * 2);
            unit.setBuffed(3);
            unit.setBuff_type(buffType);
        }
        else if (buffType == "target") {
            unit.setSpeed(unit.getSpeed() * 2);
            unit.setBuff_type(buffType);
            unit.setBuffed(3);
        }
        else if (buffType == "attack") {
            unit.setAttackpower(unit.getAttackpower() * 2);
            unit.setBuffed(3);
            unit.setBuff_type(buffType);
        }
        // System.out.println("Buffed unit: " + unit + " type: " + buffType + " buffed: " + unit.getBuffed());
    };
    
    public void buffTower(TowerModel tower, String buffType){
        if (buffType == "health") {
            tower.setHealth(tower.getHealth() * 2);
            tower.setBuff_type(buffType);
            tower.setBuffed(3);
        }
        else if (buffType == "targets") {
            tower.setTowerTargets(tower.getTowerTargets() * 2);
            tower.setBuff_type(buffType);
            tower.setBuffed(3);
        }
        else if (buffType == "attack") {
            tower.setAttackpower(tower.getAttackpower() * 2);
            tower.setBuff_type(buffType);
            tower.setBuffed(3);
        }
        // System.out.println("Buffed tower: " + tower + " type: " + buffType + " buffed: " + tower.getBuffed());
    };
    
    public void buyMine(MineModel m){
        if(m.getCost() <= gold){
            System.out.println(m.getCost());
            setGold(gold-m.getCost());
            mines.add(m);
        }
    }
    
    public void buyUnit(UnitModel unit){
        if(unit.getCost() <= gold){
            setGold(gold-unit.getCost());
            units.add(unit);
        }
    };
    /**
     * Beteszi a paraméterül kapott tornyot a listába és levonja a vételárat a játékos aranyából
     * @param tower 
     */
    public void buyTower(TowerModel tower){
        if(tower.getCost() <= gold){
            System.out.println(tower.getCost());
            setGold(gold-tower.getCost());
            towers.add(tower);
        }
    };

    public void setGold(int gold) {
        this.gold = gold;
    }
    public void addCoin(int amount){
        gold += amount;
    };
    public void removeCoin(int amount){
        gold -= amount;
    };

    public int getGold() {
        return gold;
    }

    public ArrayList<TowerModel> getTowers() {
        return towers;
    }

    public ArrayList<UnitModel> getUnits() {
        return units;
    }
    
    public ArrayList<MineModel> getMines() {
        return mines;
    }
    
    

}
