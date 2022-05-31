package gui;

import gui.graphical.GAbstractField;
import gui.graphical.GAbstractGameObject;
import gui.graphical.GDragon;
import gui.graphical.GPlayer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import labyrinthgame.model.MainModel;
import labyrinthgame.model.field.AbstractField;
import labyrinthgame.model.gameobject.AbstractGameObject;
import labyrinthgame.model.gameobject.Dragon;
import labyrinthgame.model.gameobject.Player;

/**
 *
 * @author Asus
 */
public class DrawArea extends JPanel{
    
    private List<GAbstractField> fields;
    private Color background = Color.WHITE;
    private int rowCount;
    private int columnCount;
    
    
    public DrawArea(List<GAbstractField> FIELDs, int rowCount, int columnCount){
        this.fields = FIELDs;     
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }
    
    @Override
    public void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        setBackground(background);
        for (GAbstractField field : fields) {
            field.draw(g2, rowCount, columnCount);
        }
        MainModel mainModel = new MainModel();
        //GAbstractField playerField = mainModel.getPlayerField();
        
        //AbstractField abstractField = mainModel.getPlayerField();;
        //GAbstractGameObject gPlayer = new GPlayer();
        //AbstractGameObject player = new Player(abstractField, gPlayer);
        //GAbstractField field = new 
    
    }
    
    
    
}
