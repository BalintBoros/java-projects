
package gui;

import gui.graphical.GAbstractField;
import gui.graphical.GEmptyField;
import gui.graphical.GWallField;
import gui.graphical.MainGraphical;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import static java.lang.System.console;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.concurrent.ThreadLocalRandom;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import labyrinthgame.datahandle.Database;
import labyrinthgame.datahandle.HighScore;
import labyrinthgame.model.MainModel;
import labyrinthgame.model.field.AbstractField;
import labyrinthgame.model.field.exception.MoveNotAllowedException;
import labyrinthgame.model.gameobject.AbstractGameObject;
import labyrinthgame.model.gameobject.exception.SetDirectionNotAllowedException;

public class GameWindow extends JFrame {
    private DrawArea drawArea;
    private MainModel mainModel;
    private int rowCount;
    private int columnCount;
    private Timer timer;
    private Timer timerT;
    private int n = 3;
    private int tmp;
    Duration timeElapsed;
    private String time;
    private Database db;
    private JLabel label;
    
    
   
    /*
    public int canDragonMove(int n){
        try{
                mainModel.moveDragon(n);
                return n;
        } catch (SetDirectionNotAllowedException ex){                    
                return n-1;//ThreadLocalRandom.current().nextInt(0, 3 + 1);
            }
    }*/
    
    public GameWindow(MainModel mainModel) {
        JPanel top = new JPanel();
        
        label = new JLabel();
        //updateLabelText();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainModel = mainModel;
        MainGraphical mainGraphical = new  MainGraphical();
        mainGraphical.setList(mainModel.getGuiFieldList());
        rowCount = mainModel.getRowCount();
        columnCount = mainModel.getColumnCount();
        drawArea = new DrawArea(mainModel.getGuiFieldList(),rowCount, columnCount);        
        drawArea.setPreferredSize(new Dimension(400, 425));
        //getContentPane().add(BorderLayout.CENTER, drawArea);
        //drawArea.paintComponent(grphcs);
        
       
        //JPanel mainPanel = new JPanel();
        top.add(label);
        drawArea.setLayout(new GridLayout(400, 425));
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(BorderLayout.CENTER, drawArea);
        
        setSize(400, 425);
        pack();
        setVisible(true);
        
        
        db = new Database();
        
        /*timerT = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae)  {
                Instant end = Instant.now();
                Duration timeElapsed = Duration.between(start, end);
                updateLabelText(formatDuration(timeElapsed));                
            }
        });
        timerT.start(); */
            
        
        reStartTimer();
        n = 3;
        tmp = 3;
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae)  {
                //canDragonMove(ThreadLocalRandom.current().nextInt(0, 3 + 1));
                //AbstractGameObject vmi = new Dragon();              
                
                if(tmp == n){
                    try{                        
                        mainModel.moveDragon(tmp);
                        if(mainModel.gameOver(tmp)){
                            
                            showGameOverMessage(getTime()); 
                            try{
                               timerT.stop();
                               mainModel.reStart(); 
                               
                               reStartTimer();
                            }catch ( MoveNotAllowedException ex){
                                ex.getMessage();
                            }                            
                            drawArea.repaint();
                        }
                        else if(mainModel.win()){
                            showWinningMessage(getTime()); 
                            db.storeToDatabase(mainModel.getLevel(), timeElapsed.getSeconds());
                            try{
                               //mainModel.setLevel();
                               //mainModel.newLevel();
                               timerT.stop();
                               mainModel.reStart();
                               reStartTimer();
                            }catch ( MoveNotAllowedException ex){
                                ex.getMessage();
                            }/*catch (IOException ex){
                                ex.getMessage();
                            }  */                          
                            drawArea.repaint();
                        }
                        if(mainModel.moVed()){
                            n--;
                            //System.out.println("MEGMOZDULT");
                        }
                        n++;  
                        //System.out.println("utana:" + n);
                    } catch (SetDirectionNotAllowedException ex){
                        ex.getMessage();
                    } 
                } else {
                    n = ThreadLocalRandom.current().nextInt(0, 3 + 1);
                    tmp = n;
                }
                               
                //AbstractGameObject abstractgameobject = new AbstractGameObject();
                drawArea.repaint();                
            }
        });
        timer.start();
        
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                super.keyPressed(ke); 
                //if (!game.isLevelLoaded()) return;
                int kk = ke.getKeyCode();
                int d = 0;
                switch (kk){
                    case KeyEvent.VK_LEFT:  d = 3; break;
                    case KeyEvent.VK_RIGHT: d = 1; break;
                    case KeyEvent.VK_UP:    d = 0; break;
                    case KeyEvent.VK_DOWN:  d = 2; break;
                    //case KeyEvent.VK_ESCAPE: game.loadGame(game.getGameID());
                }
                try {
                    //refreshGameStatLabel();
                    mainModel.moveElements(d);
                    if(mainModel.gameOver(tmp)){
                            showGameOverMessage(getTime());
                            try{
                               //mainModel.setLevel();
                               //mainModel.newLevel();
                               timerT.stop();
                               mainModel.reStart(); 
                               reStartTimer();
                            }catch ( MoveNotAllowedException ex){
                                ex.getMessage();
                            } 
                    }
                } catch (SetDirectionNotAllowedException ex) {
                    Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                drawArea.repaint();
                /*if (d != null && game.step(d)){
                    if (game.getLevelNumBoxes() == game.getLevelNumBoxesInPlace()){
                        JOptionPane.showMessageDialog(MainWindow.this, "Gratulálok! Nyertél!", "Gratulálok!", JOptionPane.INFORMATION_MESSAGE);
                    }
                } */
            }
        });

   
        
    }
    public void showGameOverMessage(String time) {
            JOptionPane.showMessageDialog(this,
                    "Játék vége, kikaptál! Időd: "+time  );
    }
    public void showWinningMessage(String time) {
            JOptionPane.showMessageDialog(this,
                    "Játék vége, nyertél! Időd: " +time );
    }
    /*public void TimerTime(){
        timeLabel = new JLabel( new Date().toString() );
        add( timeLabel );
 
        Timer timer = new Timer(1000, this);
        timer.setInitialDelay(1);
        timer.start();
    }*/
     private void updateLabelText(String time) {
         
        label.setText("Eltelt idő: " + time );
        
    }
    
    public ArrayList<HighScore> getHighScores() {
        return db.getHighScores();
    }
     
     public static String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
            "%d:%02d:%02d",
            absSeconds / 3600,
            (absSeconds % 3600) / 60,
            absSeconds % 60);
        return seconds < 0 ? "-" + positive : positive;
    }
     
    public void reStartTimer(){
        Instant start = Instant.now(); 
        timerT = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae)  {
                Instant end = Instant.now();
                timeElapsed = Duration.between(start, end);
                updateLabelText(formatDuration(timeElapsed));
                setTime(timeElapsed);
            }
        });
        timerT.start();
    }
    
    public void setTime(Duration timeElapsed){
        time = formatDuration(timeElapsed);
    }
    
    public String getTime(){
        return time;
    }
    
    
     
}
