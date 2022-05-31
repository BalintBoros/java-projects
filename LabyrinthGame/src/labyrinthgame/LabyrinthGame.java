/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthgame;

import labyrinthgame.model.MainModel;
import gui.GameWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import labyrinthgame.datahandle.HighScoreWindow;

public class LabyrinthGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        MainModel mainModel = new MainModel();
        mainModel.initModel();
        GameWindow gw = new GameWindow(mainModel);
        
        JMenuBar menuBar = new JMenuBar();
        gw.setJMenuBar(menuBar);

        JMenu gameMenu = new JMenu("Game Menu");
        menuBar.add(gameMenu);

        JMenuItem highScores = new JMenuItem("High Scores");
        gameMenu.add(highScores);

        highScores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HighScoreWindow(gw.getHighScores(), gw);
            }
        });
        
    }
    
    
    
}
