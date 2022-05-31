/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthgame.model.gameobject;

import gui.graphical.GAbstractGameObject;
import labyrinthgame.model.field.AbstractField;
import labyrinthgame.model.gameobject.exception.SetDirectionNotAllowedException;

public class Dragon extends AbstractGameObject {
    
    
    public Dragon(AbstractField abstractField, GAbstractGameObject gDragon) {
        super(abstractField, gDragon);
    }
    
    
    public void setDirection(int direction){
        this.direction = direction;
    }
    
    public void setMoveDirection(int moveDirection) throws SetDirectionNotAllowedException {
        
        this.moveDirection = moveDirection;
        this.direction = moveDirection;
    }
    
    public boolean getDragon(){
        return true;
    }
    
    
}
