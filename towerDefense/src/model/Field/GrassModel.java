package model.Field;

import model.Unit.UnitModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author fodor
 */
public class GrassModel extends AbstractFieldModel{
    int grass;
    
    public GrassModel() {
        grass = 1;
    }

    public int getGrass() {
        return grass;
    }
    
    
    
    @Override
    public void attack(UnitModel unit){}
}

