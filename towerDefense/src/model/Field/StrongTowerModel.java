/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Field;

/**
 *
 * @author fodor
 */
public class StrongTowerModel extends TowerModel {
    public StrongTowerModel(int x, int y){
        super(x,y, 150);
        //int cost = 150;
        attackpower = 20;
        health = 100;
    }
}
