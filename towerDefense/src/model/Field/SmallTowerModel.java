package model.Field;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import model.Field.TowerModel;

/**
 *
 * @author fodor
 */
public class SmallTowerModel extends TowerModel {
    public SmallTowerModel(int x, int y) {
        super(x,y,80);
        health = 50;
        attackpower = 10;
    }

    
}
