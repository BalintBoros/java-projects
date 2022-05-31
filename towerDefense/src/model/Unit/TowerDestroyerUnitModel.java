/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Unit;

import model.Field.AbstractFieldModel;
import model.Player;

/**
 *
 * @author deakn
 */
public class TowerDestroyerUnitModel extends UnitModel{
    
    public TowerDestroyerUnitModel(Player p, int x, int y){
        super(150, 10, 150, 1, x, y);
        owner = p;
        unitType = "TowerDestroyer Unit";
    }
    
    @Override
    public int move(){
        return 0;
    }
    
    @Override
    public void attack(AbstractFieldModel obj){
        obj.setHealth(obj.getHealth()-this.getAttackpower());
    }
}
