import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class TileWriter {

    public ArrayList<Tile> tiles;
    public File f;

    public TileWriter(ArrayList<Tile> t) {
        this.tiles = t;

        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = jfc.showDialog(null, "Select");
        if(returnVal == JFileChooser.APPROVE_OPTION){
            this.f = jfc.getSelectedFile();
            save();
        }

    }

    public void save(){
        if(this.f.getAbsolutePath().endsWith(".txt")){
            saveToText();
        }
        else if(this.f.getAbsolutePath().endsWith(".bin")){
            saveToBin();
        }
        else if(this.f.getAbsolutePath().endsWith(".xml")){
            saveToXML();
        }

    }

    public void saveToBin() {
        try (FileOutputStream fos = new FileOutputStream(this.f)){
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)){
                oos.writeObject(this.tiles);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveToText(){
        try (FileOutputStream fos = new FileOutputStream(this.f)){
            try (PrintWriter pw = new PrintWriter(fos)){
                for(Tile t: this.tiles){
                    pw.println(t.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveToXML() {
        try (FileOutputStream fos = new FileOutputStream(this.f)){
            try (XMLEncoder xmle = new XMLEncoder(fos)){
                xmle.writeObject(this.tiles);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

}
