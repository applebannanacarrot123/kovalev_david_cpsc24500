public class QuizApp {
    public static void main(String[] args){
        Quizzer q = new Quizzer();

        boolean ended = false;
        while(!ended){
            ended = q.mainOptions();
        }

        q.printFooter();
    }
}
