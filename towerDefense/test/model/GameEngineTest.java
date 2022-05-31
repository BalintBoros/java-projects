/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package thegame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author fodor
 */
public class GameEngineTest {
    
    public GameEngineTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void winnerTest() {
        GameEngine g = new GameEngine();
        assertEquals(g.getWinner(), "nobody");
    }
    
    @Test
    public void gold() {
        GameEngine g = new GameEngine();
        assertEquals(g.getPlayer1().getGold(), 1000);
        assertEquals(g.getPlayer2().getGold(), 1000);
        g.addCoin(500, g.getPlayer1());
        assertEquals(g.getPlayer1().getGold(), 1500);
    }
}
