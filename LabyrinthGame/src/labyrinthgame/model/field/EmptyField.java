/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthgame.model.field;
 
import labyrinthgame.model.field.exception.MoveNotAllowedException;
import labyrinthgame.model.gameobject.AbstractGameObject;

public class EmptyField extends AbstractField {
    
    private static final String EMPTY_MARKER = " ";
    
    public EmptyField() {
        marker = EMPTY_MARKER;
    }
    
    public void moveGameObjectToHere(AbstractGameObject currentGameObject) throws MoveNotAllowedException {
        this.currentGameObject = currentGameObject;
    }
    
    public void removeGameObjectFromHere() throws MoveNotAllowedException {
        this.currentGameObject = null;
    }
    
}

