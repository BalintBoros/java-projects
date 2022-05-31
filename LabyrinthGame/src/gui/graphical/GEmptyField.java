/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.graphical;

import java.awt.Color;
import java.awt.Graphics2D;
import labyrinthgame.model.field.AbstractField;


public class GEmptyField extends GAbstractField{
    
    public GEmptyField(int x, int y, AbstractField abstractField)
    {
        super(x, y, abstractField);
    }
    
    @Override
    public void draw(Graphics2D g2, int rowCount, int columnCount)  {
        if(abstractField.getCurrentGameObject() != null) {
           abstractField.getCurrentGameObject().getgAbstractGameObject().draw(g2, x*(viewSizeX/columnCount), y*(viewSizeY/rowCount), viewSizeX/columnCount, viewSizeY/rowCount);
        } else {
            g2.setColor(Color.WHITE);
            g2.fillRect(x*(viewSizeX/columnCount), y*(viewSizeY/rowCount), viewSizeX/columnCount, viewSizeY/rowCount);
        }        
    }
}
