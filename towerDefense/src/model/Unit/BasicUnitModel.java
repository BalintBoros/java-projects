/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Unit;
import model.Field.AbstractFieldModel;
import model.Player;

/**
 *
 * @author fodor
 */
public class BasicUnitModel extends UnitModel {
   
    public BasicUnitModel(Player p, int x, int y){
        super(100, 10, 60, 1, x, y);
        owner = p;
        unitType = "Basic Unit";
    }
    
    @Override
    public int move(){
        return 0;
    }
    
    @Override
    public void attack(AbstractFieldModel obj){
        
    }
}
