import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.awt.Color;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class TileReader {

    public File f;

    public TileReader(){
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = jfc.showDialog(null, "Select");
        if(returnVal == JFileChooser.APPROVE_OPTION){
            this.f = jfc.getSelectedFile();
        }

    }

    public ArrayList<Tile> read(){
        if(this.f.getAbsolutePath().endsWith(".txt")){
            return readFromText();
        }
        else if(this.f.getAbsolutePath().endsWith(".bin")){
            return readFromBin();
        }
        else if(this.f.getAbsolutePath().endsWith(".xml")){
            return readFromXML();
        }

        return new ArrayList<Tile>();
    }

    public ArrayList<Tile> readFromBin(){
        try (FileInputStream fis = new FileInputStream(this.f)){
            try (ObjectInputStream ois = new ObjectInputStream(fis)){
                ArrayList<Tile> alt = (ArrayList<Tile>) ois.readObject();
                return alt;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<Tile>();
    }

    public ArrayList<Tile> readFromText(){
        try (Scanner sc = new Scanner(this.f)){
            ArrayList<Tile> alt = new ArrayList<Tile>();
            while(sc.hasNextLine()){
                String[] line = sc.nextLine().split(",");
                Color c = new Color(Integer.parseInt(line[0]),
                                    Integer.parseInt(line[1]),
                                    Integer.parseInt(line[2]));

                alt.add(new Tile(c, line[3]));
            }

            return alt;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<Tile>();
    }

    public ArrayList<Tile> readFromXML(){
        try (FileInputStream fis = new FileInputStream(this.f)){
            try (XMLDecoder ois = new XMLDecoder(fis)){
                ArrayList<Tile> alt = (ArrayList<Tile>) ois.readObject();
                return alt;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<Tile>();
    }
}