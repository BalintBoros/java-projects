/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthgame.model.gameobject;

import gui.graphical.GAbstractGameObject;
import gui.graphical.GPlayer;
import labyrinthgame.model.field.AbstractField;
import labyrinthgame.model.gameobject.exception.SetDirectionNotAllowedException;

public class Player extends AbstractGameObject {     
    
    public Player(AbstractField abstractField, GAbstractGameObject gPlayer) {
        super(abstractField, gPlayer);
    }
    
    public void setMoveDirection(int moveDirection) throws SetDirectionNotAllowedException {
        this.moveDirection = moveDirection;
    }
    public void setDirection(int direction){
        this.direction = direction;
    }
    public boolean getDragon(){
        return false;
    }
    
}
