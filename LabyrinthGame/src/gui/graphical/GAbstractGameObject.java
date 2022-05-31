/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.graphical;

import java.awt.Graphics2D;


public abstract class GAbstractGameObject {
 
    public abstract void draw(Graphics2D g2, int x, int y, int width, int height);
    
}
