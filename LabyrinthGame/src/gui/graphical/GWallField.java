/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.graphical;

import res.ResourceLoader;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import labyrinthgame.model.field.AbstractField;


public class GWallField  extends GAbstractField {
    
    
    public GWallField(int x, int y, AbstractField abstractField){
        super(x, y, abstractField);
    }
    @Override
    public void draw(Graphics2D g2, int rowCount, int columnCount) {
        //
        //try {
        //    final BufferedImage wall = ImageIO.read(new File("res/wall.png"));
        //    
        //} catch (IOException ex) {
        //    Logger.getLogger(GWallField.class.getName()).log(Level.SEVERE, null, ex);
        //}
        g2.setColor(Color.GRAY);
        g2.fillRect(x*(viewSizeX/columnCount), y*(viewSizeY/rowCount), viewSizeX/columnCount, viewSizeY/rowCount);
        //g2.drawImage(wall, x*(viewSizeX/columnCount), y*(viewSizeY/rowCount), viewSizeX/columnCount,viewSizeY/rowCount, null);
        //color = new Color(112,128,144);#                
    }
    
}
