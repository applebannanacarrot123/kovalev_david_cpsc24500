import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class TilePanel extends JPanel{

    public ArrayList<Tile> tiles = new ArrayList<Tile>();

    public TilePanel(Tile t, Tile t1, Tile t2, Tile t3){
        this.tiles.add(t);
        this.tiles.add(t1);
        this.tiles.add(t2);
        this.tiles.add(t3);
        this.addMouseListener(new TileMouseListener());
    }
    
    public Tile getTile(int index){
        return tiles.get(index);
    }

    public ArrayList<Tile> getTiles(){
        return this.tiles;
    }

    public void setTiles(ArrayList<Tile> t){
        this.tiles = t;
        this.repaint();
    }

    public void randomizeTiles(){
        for(Tile t: this.tiles){
            t.randomize();
        }
        this.repaint();
    }


    @Override
    public void paintComponent(Graphics brush){
        brush.setColor(new Color(238, 238, 239));
        brush.fillRect(0, 0, 800, 500);

        for(Tile t: this.tiles){
            if(t.getShape().equals("CIRCLE")){
                brush.setColor(t.getTileColor());
                brush.fillOval((this.tiles.indexOf(t) * 175) + 50,
                                50, 150, 150);
            }
            else{
                brush.setColor(t.getTileColor());
                brush.fillRect((this.tiles.indexOf(t) * 175) + 50,
                                50, 150, 150);
            }
        }
    }

    /**
     * Mouse listener for TilePanel
     */
    class TileMouseListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            
            boolean randomize = false;

            if(50 < y && y < 200){
                if(50 < x && x < 200){
                    randomize = true;
                }
                else if(225 < x && x < 375){
                    randomize = true;
                }
                else if(400 < x && x < 550){
                    randomize = true;
                }
                else if(575 < x && x < 725){
                    randomize = true;
                }
            }

            if(randomize){
                randomizeTiles();
            }
        }
    
        @Override
        public void mouseEntered(MouseEvent e) {
            // None
        }
    
        @Override
        public void mouseExited(MouseEvent e) {
            // None
        }
    
        @Override
        public void mousePressed(MouseEvent e) {
            // None
        }
    
        @Override
        public void mouseReleased(MouseEvent e) {
            // None
        }
    }
}