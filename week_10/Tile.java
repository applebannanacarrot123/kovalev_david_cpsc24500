import java.awt.Color;
import java.io.Serializable;
import java.util.Random;

public class Tile implements Serializable {

    private Color[] COLOR_OPTIONS = {Color.YELLOW,
                                     Color.GREEN,
                                     Color.ORANGE,
                                     Color.RED,
                                     Color.BLUE};
    private String[] SHAPE_OPTIONS = {"CIRCLE",
                                      "SQUARE"};

    private Color tileColor;
    private String tileShape;
    

    /**
     * New random tile
     */
    public Tile(){
        Random randGen = new Random();
        this.tileColor = COLOR_OPTIONS[randGen.nextInt(COLOR_OPTIONS.length)];
        this.tileShape = SHAPE_OPTIONS[randGen.nextInt(SHAPE_OPTIONS.length)];
    }

    /**
     * Tile from file
     */
    public Tile(Color tc, String ts){
        this.tileColor = tc;
        this.tileShape = ts;
    }

    public Color getTileColor(){
        return this.tileColor;
    }

    public String getShape(){
        return this.tileShape;
    }

    public void setTileColor(Color c){
        this.tileColor = c;
    }

    public void setTileShape(String s){
        this.tileShape = s;
    }

    /**
     * Randomizes tile color and shape
     */
    public void randomize(){
        Random randGen = new Random();
        this.tileColor = COLOR_OPTIONS[randGen.nextInt(COLOR_OPTIONS.length)];
        this.tileShape = SHAPE_OPTIONS[randGen.nextInt(SHAPE_OPTIONS.length)];
    }

    @Override
    public String toString(){
        return String.format("%s,%s,%s,%s",
                             this.getTileColor().getRed(),
                             this.getTileColor().getGreen(),
                             this.getTileColor().getBlue(),
                             this.getShape());
    }
}
