/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.graphical;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import labyrinthgame.model.field.AbstractField;


public abstract class GAbstractField {
    
    protected int x;
    protected int y;
    
    protected int viewSizeX = 400;
    protected int viewSizeY = 400;

    AbstractField abstractField;
            
    public GAbstractField(int x, int y, AbstractField abstractField){    
        this.x = x;
        this.y = y;
        this.abstractField = abstractField;
    }
    
    public abstract void draw(Graphics2D g2, int rowCount, int columnCount);
    
}
