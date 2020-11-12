import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        MoneyField moneyField = new MoneyField();
        MoneyButton[] mbList = {new MoneyButton("Max", moneyField),
                                new MoneyButton("Mid", moneyField),
                                new MoneyButton("Min", moneyField)};
        for (MoneyButton mb : mbList) {
            bottomPanel.add(mb, BorderLayout.SOUTH);
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

        this.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    mf.setBetRatio(betRatio);
                }
            });

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
}

/**
 * Text field that displays & holds money amount
 */
class MoneyField extends JPanel {
    
    public double money;
    public double betRatio;
    public JLabel moneySign;
    public JTextField textField;

    public MoneyField(){
        this.money = 5.00;
        this.betRatio = 1;
        this.moneySign = new JLabel("$");
        this.textField = new JTextField(6);
        this.textField.setEditable(false);

        add(this.moneySign);
        add(this.textField);
        setMoney(this.money);
    }

    public void setMoney(double d){
        this.money = d;
        this.textField.setText(String.valueOf(this.money));
    }

    public double getMoney(){
        return this.money;
    }

    public void setBetRatio(double d){
        this.betRatio = d;
    }
}