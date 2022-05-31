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
public class MineModel extends AbstractFieldModel {
    private int cost = 500;
    
    public MineModel(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int getCost() {
        return cost;
    }
    
    @Override
    public void attack(UnitModel unit){}
    public void income(){
        //player.gold += 100;
    }
}
