
package gui.graphical;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import labyrinthgame.model.field.AbstractField;

public class MainGraphical extends JPanel{
    
    protected int viewSizeX;
    protected int viewSizeY;
    protected int fieldCountX;
    protected int fieldCountY;
    
    private Color color;
    
    protected List<GAbstractField> guiFieldList;
    
    public void addFields(){
        /*guiFieldList = new ArrayList<>();
        for(int x = 0; x<11; x++)
        {
            for(int y = 0; y<11; y++)
            {
                if((x%2==0 && y%2==0) || (x%2 == 1 && y%2 == 1))
                {
                    GAbstractField vmi = new GEmptyField(x,y);
                    vmi.setFieldCountX(11);
                    vmi.setFieldCountY(11);
                    guiFieldList.add(vmi);
                  
                } else {
                    GAbstractField vmi = new GWallField(x,y);
                    vmi.setFieldCountX(11);
                    vmi.setFieldCountY(11);
                    guiFieldList.add(vmi);
                }               
            } 
        }
        */
    }
    /*
    public void GPrintField(List<List<AbstractField>> rows){
        guiFieldList = new ArrayList<>();
        int length = rows.size();
        for(List<AbstractField> row : rows) {
            int idxI = rows.indexOf(row);
            for(AbstractField field : row) {
                int idxJ = row.indexOf(field);
                if(field.getMarker()==" ")
                {
                    GAbstractField vmi = new GEmptyField(idxI,idxJ);
                    vmi.setFieldCountX(length);
                    vmi.setFieldCountY(length);
                    guiFieldList.add(vmi);
                } else if(field.getMarker()=="w") {
                    GAbstractField vmi = new GWallField(idxI,idxJ);
                    vmi.setFieldCountX(length);
                    vmi.setFieldCountY(length);
                    guiFieldList.add(vmi);
                }
            }
            
        }
    }*/
    
    public void initGraphical() {
  
    }
    
    public List<GAbstractField> getList()
    {
        return guiFieldList;
    }
    
    public void setList(List<GAbstractField> guiFieldList)
    {
        this.guiFieldList = guiFieldList;
    }
    
}
