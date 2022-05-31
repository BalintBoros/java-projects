/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.graphical;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 *
 */
public class GPlayer extends GAbstractGameObject {
       
    @Override
    public void draw(Graphics2D g2, int x, int y, int width, int height)  {
        //g2.setColor(Color.RED);
        //g2.fillRect(x, y, width, height);
        
        Area outter = new Area(new Rectangle(0,0, 400, 400));
        Ellipse2D.Double inner = new Ellipse2D.Double(x-3*width,y-3*height,width*7,height*7);
        outter.subtract(new Area(inner));
        
        g2.setColor(Color.BLACK);
        g2.fill(outter);
        
        g2.setColor(Color.RED);
        g2.fillRect(x, y, width, height);
    }

}
