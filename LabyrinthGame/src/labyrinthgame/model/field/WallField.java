/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthgame.model.field;

import labyrinthgame.model.field.exception.MoveNotAllowedException;
import labyrinthgame.model.gameobject.AbstractGameObject;


public class WallField extends AbstractField {
    
    private static final String WALL_MARKER = "w";
    
    public WallField() {
        marker = WALL_MARKER;
    }
    
    public void moveGameObjectToHere(AbstractGameObject currentGameObject) throws MoveNotAllowedException {
        throw new MoveNotAllowedException();
    }
    
    public void removeGameObjectFromHere() throws MoveNotAllowedException {
        throw new MoveNotAllowedException();
    }
    
}
