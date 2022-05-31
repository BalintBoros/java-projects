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
public class FastUnitModel extends UnitModel {
    
    public FastUnitModel(Player p, int x, int y){
        super(80, 8, 80, 2, x, y);
        owner = p;
        unitType = "Fast Unit";
    }
    
    @Override
    public int move(){
        return 0;
    }
    
    @Override
    public void attack(AbstractFieldModel obj){
        
    }
}
