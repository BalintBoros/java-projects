package view;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.WindowConstants;
import model.Position;
import thegame.GameEngine;

/**
 *
 * @author avoczf
 */
public class MainWindow extends JFrame {

    private Board board;
    private JFrame frame;
    private final JLabel currentPlayerLabel;
    private JLabel goldLabel;
    private JMenuItem basicTowerButton;
    private JMenuItem smallTowerButton;
    private JMenuItem strongTowerButton;
    private JMenuItem basicUnitButton;
    private JMenuItem fastUnitButton;
    private JMenuItem strongUnitButton;
    private JMenuItem towerDestroyerUnitButton;
    private JMenuItem MineButton;
    private JMenuItem sellTowerButton;
    private JMenuItem upgradeTowerButton;
    private JMenuItem healthBuffButton;
    private JMenuItem speedBuffButton;
    private JMenuItem attackBuffButton;
    private GameEngine g;
    private MouseAdapter m;
    private int playerNum = 1;

    public MainWindow() throws IOException {
        // frame = new JFrame();
        setTitle("TheGame");
        setSize(1500, 1500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 10));
        try {
            add(board = new Board(), BorderLayout.CENTER);
        } catch (IOException ex) {
        }

        JMenuBar menuBarUp = new JMenuBar();
        JMenuBar menuBarBottom = new JMenuBar();
        JMenu UnitMenu = new JMenu("Buy Unit");
        JMenu TowerMenu = new JMenu("Build Tower");
        JMenu BuffMenu = new JMenu("Buff");
        g = board.getGameEngine();

        basicTowerButton = new JMenuItem("Basic Tower");
        smallTowerButton = new JMenuItem("Small Tower");
        strongTowerButton = new JMenuItem("Strong Tower");

        basicUnitButton = new JMenuItem("Basic Unit");
        fastUnitButton = new JMenuItem("Fast Unit");
        strongUnitButton = new JMenuItem("Strong Unit");
        towerDestroyerUnitButton = new JMenuItem("TowerDestroyer Unit");

        MineButton = new JMenuItem("Build gold mine");
        sellTowerButton = new JMenuItem("Sell Tower");
        upgradeTowerButton = new JMenuItem("Upgrade tower");

        healthBuffButton = new JMenuItem("Health buff");
        speedBuffButton = new JMenuItem("Speed // Target count buff");
        attackBuffButton = new JMenuItem("Attack buff");

        currentPlayerLabel = new JLabel("Current player: " + g.playerToString(g.getCurrentPlayer()));
        goldLabel = new JLabel("Gold: " + g.getCurrentPlayer().getGold());

        JMenuItem newGame = new JMenuItem(new AbstractAction("New game") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //remove(board);
                /*remove(goldLabel);
                System.gc();*/
                board.readLevels();
                board.setArraysEmpty();
                
                if(g.getCurrentPlayer()==g.getPlayer2()){
                    g.nextPlayer(); 
                    updateCurrentPlayerLabel();
                }
                g.getPlayer1().setGold(1000);
                g.getPlayer2().setGold(1000);
                
                pack();
                g.getPlayer1().getMines().removeAll(g.getPlayer1().getMines());
                g.getPlayer1().getTowers().removeAll(g.getPlayer1().getTowers());
                g.getPlayer1().getUnits().removeAll(g.getPlayer1().getUnits());
        
                g.getPlayer2().getMines().removeAll(g.getPlayer2().getMines());
                g.getPlayer2().getTowers().removeAll(g.getPlayer2().getTowers());
                g.getPlayer2().getUnits().removeAll(g.getPlayer2().getUnits());
                //pack();
                g.getBaseModel1().setHealth(100);
                g.getBaseModel2().setHealth(100);
                board.refresh();
            }
        });

        JMenuItem endTurnButton = new JMenuItem(new AbstractAction("End turn") {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.endTurn();               
                updateCurrentPlayerLabel();
                updateGoldLabel();
                board.addMouseListener(m = new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            board.getCell(e.getX(), e.getY());
                        } else {
                            board.getCell(-1, -1);
                        }
                    }
                });

            }

        });

        basicUnitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.placeUnit("Basic");
                updateGoldLabel();
            }
        });

        fastUnitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.placeUnit("Fast");
                updateGoldLabel();
            }
        });

        strongUnitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.placeUnit("Strong");
                updateGoldLabel();
            }
        });
        
        towerDestroyerUnitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.placeUnit("TowerDestroyer");
                updateGoldLabel();
            }
        });
        
        healthBuffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.addMouseListener(m = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        board.buff(e.getX(), e.getY(), "health");
                        updateGoldLabel();
                        board.removeMouseListener(m);
                    }
                });
            }
        });

        speedBuffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.addMouseListener(m = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        board.buff(e.getX(), e.getY(), "target");
                        updateGoldLabel();
                        board.removeMouseListener(m);
                    }
                });
            }
        });

        attackBuffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.addMouseListener(m = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        board.buff(e.getX(), e.getY(), "attack");
                        updateGoldLabel();
                        board.removeMouseListener(m);
                    }
                });
            }
        });

        MineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.addMouseListener(m = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        board.placeMine(e.getX(), e.getY());
                        updateGoldLabel();
                        board.removeMouseListener(m);
                    }
                });
            }
        });

        basicTowerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.addMouseListener(m = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        board.placeTower(e.getX(), e.getY(), "Basic");
                        updateGoldLabel();
                        board.removeMouseListener(m);
                    }
                });
            }
        });

        smallTowerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.addMouseListener(m = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        board.placeTower(e.getX(), e.getY(), "Small");
                        updateGoldLabel();
                        board.removeMouseListener(m);
                    }
                });
            }
        });
        strongTowerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.addMouseListener(m = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        board.placeTower(e.getX(), e.getY(), "Strong");
                        updateGoldLabel();
                        board.removeMouseListener(m);
                    }
                });
            }
        });

        sellTowerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.addMouseListener(m = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        board.removeTower(e.getX(), e.getY());
                        updateGoldLabel();
                        board.removeMouseListener(m);
                    }
                });
            }
        });
        
        upgradeTowerButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                board.addMouseListener(m = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        board.upgradeTower(e.getX(), e.getY());
                        updateGoldLabel();
                        board.removeMouseListener(m);
                    }
                });
            }
        });
        
        

        board.repaint();
        board.refresh();

        // header
        menuBarUp.add(new JLabel("      "));
        menuBarUp.add(newGame);
        menuBarUp.add(goldLabel);
        menuBarUp.add(new JLabel("      "));
        menuBarUp.add(currentPlayerLabel);
        menuBarUp.add(new JLabel("   |   "));
        menuBarUp.add(endTurnButton);

        // footer
        menuBarBottom.add(new JLabel("      "));
        TowerMenu.add(basicTowerButton);
        TowerMenu.add(smallTowerButton);
        TowerMenu.add(strongTowerButton);
        menuBarBottom.add(TowerMenu);
        menuBarBottom.add(new JLabel("      "));
        UnitMenu.add(basicUnitButton);
        UnitMenu.add(fastUnitButton);
        UnitMenu.add(strongUnitButton);
        UnitMenu.add(towerDestroyerUnitButton);
        menuBarBottom.add(UnitMenu);
        menuBarBottom.add(new JLabel("      "));
        BuffMenu.add(healthBuffButton);
        BuffMenu.add(attackBuffButton);
        BuffMenu.add(speedBuffButton);
        menuBarBottom.add(BuffMenu);
        menuBarBottom.add(new JLabel("      "));
        menuBarBottom.add(MineButton);
        menuBarBottom.add(sellTowerButton);
        menuBarBottom.add(upgradeTowerButton);

        add(menuBarUp, BorderLayout.NORTH);
        add(menuBarBottom, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    private void updateGoldLabel() {
        goldLabel.setText("Gold: " + g.getCurrentPlayer().getGold());
    }

    private void updateCurrentPlayerLabel() {
        currentPlayerLabel.setText("Current player: " + g.playerToString(g.getCurrentPlayer()));
        System.out.println(g.playerToString(g.getCurrentPlayer()));
    }
   /* private void updateCurrentPlayerLabel() {
        if(g.getCurrentPlayer() == g.getPlayer1()){
            currentPlayerLabel.setText("Current player: " + g.playerToString(g.getPlayer1()));
            System.out.print("player1");
            playerNum=2;
        }else{
            currentPlayerLabel.setText("Current player: " + g.playerToString(g.getPlayer2()));
            playerNum=1;
        }            
    }*/

    public static void main(String[] args) {
        try {
            new MainWindow();
        } catch (IOException ex) {
        }
    }

}
