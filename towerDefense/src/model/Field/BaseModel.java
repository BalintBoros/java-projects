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
public class BaseModel extends AbstractFieldModel {
    private int health = 100;
    

    public int getHealth() {
        return health;
    }
    
    @Override
    public void attack(UnitModel unit){
        this.health -= unit.getAttackpower();
    }
}
