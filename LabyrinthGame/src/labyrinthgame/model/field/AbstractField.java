/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthgame.model.field;

import java.util.List;
import labyrinthgame.model.field.exception.MoveNotAllowedException;
import labyrinthgame.model.gameobject.AbstractGameObject;

public abstract class AbstractField {
    
    private static final String GAME_OBJECT_MARKER = "O";
    
    protected String marker;
    protected AbstractGameObject currentGameObject;

    protected List<AbstractField> neighbors;
    
    public abstract void moveGameObjectToHere(AbstractGameObject currentGameObject) throws MoveNotAllowedException;
    
    public abstract void removeGameObjectFromHere() throws MoveNotAllowedException;
    
    public String getMarker() {
        if(currentGameObject != null) {
            return GAME_OBJECT_MARKER;
        } else {
            return marker;
        }
    }
    
    public void setNeighbors(List<AbstractField> neighbors) {
        this.neighbors = neighbors;
    }
    
    public AbstractField getNeighborByDirection(int direction) {
        return neighbors.get(direction);
    }       
    
    public List<AbstractField> getNeighbors() {
        return neighbors;
    }

    public AbstractGameObject getCurrentGameObject() {
        return currentGameObject;
    }
    
}
