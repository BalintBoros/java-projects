/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.Unit.BasicUnitModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author deakn
 */
public class PlayerTest {
    
    
    @Test
    public void addCoinTest(){
        int amount = 10;
        Player p = new Player();
        p.addCoin(amount);
        assertEquals(1010,p.getGold());
    }
    
    @Test
    public void buyUnitTest(){
        Player p = new Player();
        BasicUnitModel unit = new BasicUnitModel(p, 0,0);
        p.buyUnit(unit);
        assertEquals(unit, p.getUnits().get(0));
        assertEquals(940, p.getGold());
    }
    
    
    
}
