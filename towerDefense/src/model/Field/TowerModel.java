/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Field;

import model.Unit.UnitModel;

/**
 *
 * @author fodor
 */
public class TowerModel extends AbstractFieldModel {
    protected int level = 1;
    private int cost;
    private int towerTargets;
    private boolean upgraded;
    
    public TowerModel(int x, int y){
        this.x = x;
        this.y = y;
        towerTargets = 3;
        upgraded = false;
        buffed = -1;
    }
    
    public TowerModel(int x, int y, int cost){
        this.x = x;
        this.y = y;
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public int getTowerTargets() {
        return towerTargets;
    }

    public boolean isUpgraded() {
        return upgraded;
    }

    public void setUpgraded(boolean upgraded) {
        this.upgraded = upgraded;
    }

    public void setTowerTargets(int towerTargets) {
        this.towerTargets = towerTargets;
    }
    /**
     * Ellenőrzi, hogy a torony látótávolságán belül van-e a paraméterül kapott unit és ha igen megsebzi
     * @param unit A megtámadandó unit
     * @return True, ha látótávolságon belül van a unit és False, ha nem
     */
    public boolean inRange(UnitModel unit){
        for(int i = this.x-2; i < this.x+2; ++i){
            for(int j = this.y-2; j < this.y+2; ++j){
                if(unit.getX() == i && unit.getY() == j){
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void attack(UnitModel unit){
        unit.setHealth(unit.getHealth()-attackpower);
    }
}
