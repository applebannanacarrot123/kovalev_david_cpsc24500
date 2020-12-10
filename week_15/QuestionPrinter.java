import java.util.ArrayList;

public class QuestionPrinter {
    
    private ArrayList<Question> qArrayList;
    
    public QuestionPrinter(ArrayList<Question> qArrayList){
        this.qArrayList = qArrayList;
    }

    public ArrayList<Question> getQuestionList(){
        return this.qArrayList;
    }

    public void setQuestionList(ArrayList<Question> qArrayList){
        this.qArrayList = qArrayList;
    }

    public void printQuestion(int index){
        Question q = this.qArrayList.get(index);
        String question = q.getQuestion();
        String[] qOptions = q.getOptions();

        System.out.printf("%s%na. %s%nb. %s%nc. %s%nd. %s%n", question,
                                                            qOptions[0],
                                                            qOptions[1],
                                                            qOptions[2],
                                                            qOptions[3]);
    }
    
}
