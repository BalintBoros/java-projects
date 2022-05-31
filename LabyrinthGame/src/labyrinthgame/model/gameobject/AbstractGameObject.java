/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthgame.model.gameobject;

import gui.graphical.GAbstractGameObject;
import labyrinthgame.model.field.AbstractField;
import labyrinthgame.model.field.exception.MoveNotAllowedException;
import labyrinthgame.model.gameobject.exception.SetDirectionNotAllowedException;

public abstract class AbstractGameObject {
    
    protected int moveDirection;
    private AbstractField currentField;


    private GAbstractGameObject gAbstractGameObject;
    private boolean moved;
    protected int direction;

    public int getDirection() {
        return direction;
    }

    public abstract void setDirection(int direction);

   
    
    
    public AbstractGameObject(AbstractField currentField, GAbstractGameObject gAbstractGameObject) {
        this.currentField = currentField;
        this.gAbstractGameObject = gAbstractGameObject;
    }
    
    public void move() {
        this.moved = false;
        AbstractField targetField = currentField.getNeighborByDirection(moveDirection);
        try {
            targetField.moveGameObjectToHere(this);
            currentField.removeGameObjectFromHere();
            currentField = targetField;
            this.moved = true;
        } catch (MoveNotAllowedException ex) {
            //return false;            
        }
        this.moved = currentField == targetField;
    }
    
    public abstract void setMoveDirection(int moveDirection) throws SetDirectionNotAllowedException;

    public GAbstractGameObject getgAbstractGameObject() {
        return gAbstractGameObject;
    }
    public abstract boolean getDragon();
    
     public boolean isMoved() {
        return moved;
    }
     
    public AbstractField getCurrentField() {
        return currentField;
    }
        
}
