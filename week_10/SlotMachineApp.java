import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class SlotMachineApp{
    public static void main(String[] args){
        SlotMachineFrame smf = new SlotMachineFrame("Vegas");
        smf.setVisible(true);

        
    }
}

/**
 * Slot machine frame that holds the interior panels
 */
class SlotMachineFrame extends JFrame{

    public SlotMachineFrame(String name){
        // Frame set up
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((int)(screenSize.getWidth() / 2) - 400,
                       (int)(screenSize.getHeight() / 2) - 300,
                        800,
                        600);
        this.setTitle(name);
        
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());


        // Menu bar
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem loadTilesM = new JMenuItem("Load Tiles");
        JMenuItem saveTilesM = new JMenuItem("Save Tiles");
        JMenuItem printM = new JMenuItem("Print");
        JMenuItem restartM = new JMenuItem("Restart");
        JMenuItem exitM = new JMenuItem("Exit");
        JMenuItem[] fileMenuItems = {loadTilesM,
                                     saveTilesM,
                                     printM,
                                     restartM,
                                     exitM};
        for(JMenuItem fmi: fileMenuItems){
            fileMenu.add(fmi);
        }

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutM = new JMenuItem("About");
        helpMenu.add(aboutM);
    
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        this.setJMenuBar(menuBar);
        // Tile Panel
        TilePanel tilePanel = new TilePanel(new Tile(),
                                            new Tile(),
                                            new Tile(),
                                            new Tile());

        // South portion
        JPanel bottomPanel = new JPanel();

        MoneyField moneyField = new MoneyField(tilePanel);
        MoneyButton[] mbList = {new MoneyButton("Max", moneyField),
                                new MoneyButton("Mid", moneyField),
                                new MoneyButton("Min", moneyField)};
        for (MoneyButton mb : mbList) {
            bottomPanel.add(mb, BorderLayout.SOUTH);
            mb.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        moneyField.setBetRatio(mb.getBetRatio());
                        moneyField.gamble();
                        System.out.println(moneyField.getMoney());
                        if(moneyField.getMoney() == 0){
                            for(MoneyButton disMB : mbList){
                                disMB.disableMB();
                            }
                        }
                    }
                });
        }
        bottomPanel.add(moneyField, BorderLayout.SOUTH);

        // Action listeners
        // Main menu Action Listeners
        loadTilesM.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                TileReader tr = new TileReader();
                if(!tr.read().isEmpty()){
                    tilePanel.setTiles(tr.read());
                }
            }
        });

        saveTilesM.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                TileWriter tw = new TileWriter(tilePanel.getTiles());
            }
        });

        printM.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                for(Tile t : tilePanel.getTiles()){
                    System.out.println(t.toString());
                }
            }
        });

        restartM.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                moneyField.setMoney(5.00);
                for(MoneyButton mb: mbList){
                    mb.enableMB();
                }
            }
        });

        exitM.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(1);
            }
        });

        // Help menu Action Listeners
        aboutM.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(null, "David Kovalev");
            }
        });


        contentPane.add(tilePanel, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

    }
}


/**
 * Button to set bet amount
 */
class MoneyButton extends JButton {
    
    public String name;
    public MoneyField mf;
    public double betRatio;

    public MoneyButton(String s, MoneyField mf){
        this.name = s;
        this.mf = mf;
        this.betRatio = getBetRatio();

        this.setText(s);
    }

    public String getName(){
        return this.name;
    }

    public double getBetRatio(){
        if(this.name.equals("Max")){
            return 1;
        }
        else if(this.name.equals("Mid")){
            return 0.5;
        }
        else{
            return 0.1;
        }
    }

    public void enableMB(){
        this.setEnabled(true);
    }

    public void disableMB(){
        this.setEnabled(false);
    }
}

/**
 * Text field that displays & holds money amount
 */
class MoneyField extends JPanel {
    
    public TilePanel tp;
    public double money;
    public double betRatio;
    public JLabel moneySign;
    public JTextField textField;

    public MoneyField(TilePanel tp){
        this.tp = tp;

        this.money = 5.00;
        this.betRatio = 1;
        this.moneySign = new JLabel("$");
        this.textField = new JTextField(6);
        this.textField.setEditable(false);

        add(this.moneySign);
        add(this.textField);
        setMoney(this.money);
    }

    // gambles the money user wagered
    public void gamble(){
        tp.randomizeTiles();
        ArrayList<Tile> tiles = tp.getTiles();
        Tile t1 = tiles.get(0);
        double wager = (double) Math.round(getMoney()
                                           * this.betRatio 
                                           * 100)
                                           / 100;

        if(wager < 0.01){
            wager = 0.01;
        }

        boolean colors = true;
        boolean shapes = true;
        for(Tile t2: tiles){
            if(!t1.getShape().equals(t2.getShape())){
                shapes = false;
            }
            if(!t1.getTileColor().equals(t2.getTileColor())){
                colors = false;
            }
        }

        // TileChecker
        if(colors){
            if(shapes){
                if(this.betRatio == 1){
                    wager *= -100;
                }
                else if(this.betRatio == 0.5){
                    wager *= -50;
                }
                else if(this.betRatio == 0.1){
                    wager *= -10;
                }
            }
            else{
                if(this.betRatio == 1){
                    wager *= -25;
                }
                else if(this.betRatio == 0.5){
                    wager *= -10;
                }
                else if(this.betRatio == 0.1){
                    wager *= -5;
                }
            }
        }


        setMoney(getMoney() - wager);
        if(getMoney() <= 0){
            setMoney(0);
        }
    }

    public void setMoney(double d){
        this.money = (double) Math.round(d * 100)
                              / 100;

        String visible = String.format("%.2f", this.money);
        this.textField.setText(visible);
    }

    public double getMoney(){
        return this.money;
    }

    public void setBetRatio(double d){
        this.betRatio = d;
    }
}