import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
 

public class PumpkinMaker {
    public static void main(String[] args){
        // Initialize components
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Pumpkin Maker");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameX = (int)((screenSize.getWidth() / 2) - 400);
        int frameY = (int)((screenSize.getHeight() / 2) - 240);
        frame.setBounds(frameX,
                        frameY,
                        800,
                        480);
        
        // Text fields & Labels
        JTextField left = new JTextField(4);
        JLabel leftLabel = new JLabel("Left:");
        JTextField top = new JTextField(4);
        JLabel topLabel = new JLabel("Top:");
        JTextField width = new JTextField(4);
        JLabel widthLabel = new JLabel("Width:");
        JTextField height = new JTextField(4);
        JLabel heightLabel = new JLabel("Height:");
        JTextField eye = new JTextField(1);
        JLabel eyeLabel = new JLabel("Eye (C S T):");
        JTextField nose = new JTextField(1);
        JLabel noseLabel = new JLabel("Nose (C S T):");
        JTextField mouth = new JTextField(1);
        JLabel mouthLabel = new JLabel("Mouth (O R):");

        JTextField[] numberFields = {left, top, width, height};
        JTextField[] cstFields = {eye, nose};

        JButton draw = new JButton("Draw");
        
        Container contentPane = frame.getContentPane();
        DrawingArea pumpkinArea = new DrawingArea();
        JPanel southPanel = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(pumpkinArea, BorderLayout.CENTER);
        contentPane.add(southPanel, BorderLayout.SOUTH);

        draw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                boolean errored = false;

                // Validate inputs
                for(JTextField jtf: numberFields){
                    if(!validateInput(jtf.getText(), "number")){
                        JOptionPane.showMessageDialog(frame,
                        String.format("%s is not a valid input",
                                      jtf.getText()),
                        "Input warning",
                        JOptionPane.WARNING_MESSAGE);

                        errored = true;
                    }
                }

                for(JTextField jtf: cstFields){
                    if(!validateInput(jtf.getText(), "CST")){
                        JOptionPane.showMessageDialog(frame,
                        String.format("%s is not a valid input",
                                      jtf.getText()),
                        "Input warning",
                        JOptionPane.WARNING_MESSAGE);

                        errored = true;
                    }
                }

                if(!validateInput(mouth.getText(), "mouth")){
                    JOptionPane.showMessageDialog(frame,
                    String.format("%s is not a valid input",
                                  mouth.getText()),
                    "Input warning",
                    JOptionPane.WARNING_MESSAGE);
                    
                    errored = true;
                }

                if(!errored){
                    pumpkinArea.setPumpkinVariables(
                        Integer.parseInt(left.getText()),
                        Integer.parseInt(top.getText()),
                        Integer.parseInt(width.getText()),
                        Integer.parseInt(height.getText()),
                        eye.getText(),
                        nose.getText(),
                        mouth.getText());
                    pumpkinArea.repaint();
                }
            }
        });

        southPanel.add(leftLabel);
        southPanel.add(left);
        southPanel.add(topLabel);
        southPanel.add(top);
        southPanel.add(widthLabel);
        southPanel.add(width);
        southPanel.add(heightLabel);
        southPanel.add(height);
        southPanel.add(eyeLabel);
        southPanel.add(eye);
        southPanel.add(noseLabel);
        southPanel.add(nose);
        southPanel.add(mouthLabel);
        southPanel.add(mouth);
        southPanel.add(draw);

        frame.setVisible(true);
    }

    /**
     * Validates input from a text field
     * @param s
     * @param fieldType
     * @return
     */
    public static boolean validateInput(String s, String fieldType){
        char[] validNumericalInput = {'0', '1', '2', '3','4',
                                      '5', '6', '7', '8', '9'};
        char[] validCST = {'C', 'S', 'T'};
        char[] validMouth = {'O', 'R'};

        char[] input = s.toCharArray();

        if(fieldType.equals("CST") && input.length > 1
           || fieldType.equals("mouth") && input.length > 1){
               return false;
        }

        if(fieldType.equals("number")){
            for(char c: input){
                for(char vni: validNumericalInput){
                    if(c == vni){
                        return true;
                    }
                }
            }
        }
        else if(fieldType.equals("CST")){
            for(char c: input){
                for(char vcst: validCST){
                    if(c == vcst){
                        return true;
                    }
                }
            }
        }
        else if(fieldType.equals("mouth")){
            for(char c: input){
                for(char vm: validMouth){
                    if(c == vm){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
}


/**
 * Pumpkin drawing area
 */
class DrawingArea extends JPanel{

    private int pumpkinX = 200;
    private int pumpkinY = 100;
    private int pumpkinWidth = 100;
    private int pumpkinHeight = 100;
    private String pumpkinEye = "C";
    private String pumpkinNose = "S";
    private String pumpkinMouth = "O";

    public DrawingArea(){
        // Nothing new
    }

    public void setPumpkinVariables(int x,
                                    int y,
                                    int width,
                                    int height,
                                    String eye,
                                    String nose,
                                    String mouth){
        this.pumpkinX = x;
        this.pumpkinY = y;
        this.pumpkinWidth = width;
        this.pumpkinHeight = height;
        this.pumpkinEye = eye;
        this.pumpkinNose = nose;
        this.pumpkinMouth = mouth;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(new Color(239, 238, 238));
        g.fillRect(0, 0, 800, 480);

        // Static parts
        // Stem
        g.setColor(Color.WHITE);
        g.fillRect(pumpkinX + (7 * pumpkinWidth / 16),
                   pumpkinY - (pumpkinHeight / 8),
                   pumpkinWidth / 8,
                   pumpkinHeight / 4);
        // Pumpkin
        g.setColor(Color.YELLOW);
        g.fillOval(pumpkinX, pumpkinY, pumpkinWidth, pumpkinHeight);
        
        // Non static parts
        g.setColor(Color.WHITE);

        // Eyes
        if(this.pumpkinEye.equals("C")){
            g.fillOval(pumpkinX + (pumpkinWidth / 4),
                       pumpkinY + (pumpkinHeight / 6),
                       pumpkinWidth / 8,
                       pumpkinHeight / 8);
            g.fillOval(pumpkinX + (10 * pumpkinWidth / 16),
                       pumpkinY + (pumpkinHeight / 6),
                       pumpkinWidth / 8,
                       pumpkinHeight / 8);
        }
        else if(this.pumpkinEye.equals("S")){
            g.fillRect(pumpkinX + (pumpkinWidth / 4),
                       pumpkinY + (pumpkinHeight / 6),
                       pumpkinWidth / 8,
                       pumpkinHeight / 8);
            g.fillRect(pumpkinX + (10 * pumpkinWidth / 16),
                       pumpkinY + (pumpkinHeight / 6),
                       pumpkinWidth / 8,
                       pumpkinHeight / 8);
        }
        else if(this.pumpkinEye.equals("T")){
            int[] xPoints = {pumpkinX + (pumpkinWidth / 4),
                             pumpkinX + (5 * pumpkinWidth / 16),
                             pumpkinX + (6 * pumpkinWidth / 16)};

            int[] yPoints = {pumpkinY + (5 * pumpkinHeight / 16),
                             pumpkinY + (pumpkinHeight / 8),
                             pumpkinY + (5 * pumpkinHeight / 16)};

            Polygon tEye = new Polygon(xPoints, yPoints, 3);
            g.fillPolygon(tEye);
            tEye.translate(3 * pumpkinWidth / 8, 0);
            g.fillPolygon(tEye);
        }

        // Nose
        if(this.pumpkinNose.equals("C")){
            g.fillOval(pumpkinX + (7 * pumpkinWidth / 16),
                       pumpkinY + (7 * pumpkinHeight / 16),
                       pumpkinWidth / 8,
                       pumpkinHeight / 8);
        }
        else if(this.pumpkinNose.equals("S")){
            g.fillRect(pumpkinX + (7 * pumpkinWidth / 16),
                       pumpkinY + (7 * pumpkinHeight / 16),
                       pumpkinWidth / 8,
                       pumpkinHeight / 8);
        }
        else if(this.pumpkinNose.equals("T")){
            int[] xPoints = {pumpkinX + (7 * pumpkinWidth / 16),
                             pumpkinX + (pumpkinWidth / 2),
                             pumpkinX + (9 * pumpkinWidth / 16)};
            int[] yPoints = {pumpkinY + (9 * pumpkinHeight / 16),
                             pumpkinY + (7 * pumpkinHeight / 16),
                             pumpkinY + (9 * pumpkinHeight / 16)};

            Polygon tNose = new Polygon(xPoints, yPoints, 3);
            g.fillPolygon(tNose);
        }

        // Mouth
        if(this.pumpkinMouth.equals("O")){
            g.fillOval(pumpkinX + (pumpkinWidth / 4),
                       pumpkinY + (3 * pumpkinHeight / 4),
                       pumpkinWidth / 2,
                       pumpkinHeight / 8);
        }
        else if(this.pumpkinMouth.equals("R")){
            g.fillRect(pumpkinX + (pumpkinWidth / 4),
                       pumpkinY + (3 * pumpkinHeight / 4),
                       pumpkinWidth / 2,
                       pumpkinHeight / 8);
        }

    }
}

