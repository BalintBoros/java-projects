/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Field;

/**
 *
 * @author fodor
 */
public class BasicTowerModel extends TowerModel {
    
    public BasicTowerModel(int x, int y){
        super(x,y,100);
        attackpower = 10;
        //int cost = 100;
        health = 100;
    }
    
}