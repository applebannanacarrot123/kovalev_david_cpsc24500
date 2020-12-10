import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class QuestionReader {
    
    public File questionFile;
    public ArrayList<Question> questionArray;
    
    public QuestionReader(File f){
        this.questionFile = f;
    }

    public QuestionReader(String s){
        this(new File(s));
    }


    public ArrayList<Question> getQuestions(){
        return readFile();
    }

    /**
     * Read through questions json file
     * and return question list
     * @return
     */
    private ArrayList<Question> readFile(){
        ArrayList<Question> qa = new ArrayList<Question>();
        try(FileReader filereader = new FileReader(this.questionFile)){
            JSONParser jp = new JSONParser();
            JSONObject all = (JSONObject) jp.parse(filereader);
            JSONArray jsonArray = (JSONArray) all.get("questions");
            Iterator qIterator = jsonArray.iterator();

            while(qIterator.hasNext()){
                JSONObject qJSONObject = (JSONObject) qIterator.next();
                String question = qJSONObject.get("question").toString();
                String a = qJSONObject.get("a").toString();
                String b = qJSONObject.get("b").toString();
                String c = qJSONObject.get("c").toString();
                String d = qJSONObject.get("d").toString();
                String answer = qJSONObject.get("answer").toString();

                qa.add(new Question(question, a, b, c, d, answer));
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("File could not be read correctly.");
        }
    
        return qa;
    }

    
}
