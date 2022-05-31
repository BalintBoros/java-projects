/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.graphical;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 */
public class GDragon extends GAbstractGameObject {
       
    @Override
    public void draw(Graphics2D g2, int x, int y, int width, int height)  {
        g2.setColor(Color.CYAN);
        g2.fillRect(x, y, width, height);
    } 

}
