import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Quizzer {

    private QuestionPrinter questionPrinter;
    private Scanner userInput;

    public Quizzer(){
        printHeader();

        File f;
        QuestionReader qr;
        ArrayList<Question> qal = new ArrayList<Question>();
        this.userInput = new Scanner(System.in);

        while(qal.isEmpty()){
            f = new File(askQreturnA("Enter name of question file:"));
            qr = new QuestionReader(f);
            qal = qr.getQuestions();
        }

        this.questionPrinter = new QuestionPrinter(qal);

    }

    public String askQreturnA(String q){
        System.out.print(q);

        return this.userInput.nextLine();
    }

    public void printHeader(){
        System.out.println("********************************************");
        System.out.println("*     OOP Theory and Concept Questions     *");
        System.out.println("********************************************");
    }

    public boolean mainOptions(){
        System.out.printf("%nHere are your choices:%n");
        System.out.println("1. Take a quiz");
        System.out.println("2. See questions and answers");
        System.out.println("3. Exit");
        
        String answer = "";
        while(!answer.equals("1")
              && !answer.equals("2")
              && !answer.equals("3")){
           answer = askQreturnA("Enter the number of your choice: ");
        }
        System.out.println();

        int questionAmount = 0;
        while(answer.equals("1")
              && questionAmount <= 0){
                try{
                    String qas = "How many questions would you like?: ";
                    questionAmount = Integer.valueOf(askQreturnA(qas));
                    quizStudent(questionAmount);
                }
                catch(NumberFormatException e){
                    questionAmount = 0;
                }
        }

        if(answer.equals("2")){
            printQuestionsAndAnswers();
        }

        return answer.equals("3");
    }

    public void quizStudent(int questionAmount){
        int correctAnswers = 0;
        Random randNum = new Random();
        ArrayList<Question> qList = this.questionPrinter.getQuestionList();

        for(int i = 0; i < questionAmount; i++){
            int qNum = randNum.nextInt(qList.size());
            this.questionPrinter.printQuestion(qNum);
            Question q = qList.get(qNum);

            if(q.getAnswer().equals(askQreturnA("Enter your choice:"))){
                correctAnswers ++;
                System.out.printf("Correct!%n%n");
            }

            else{
                System.out.printf("Sorry the answer is %s%n%n", q.getAnswer());
            }
        }

        System.out.printf("You answered % of % question correctly.%n",
                          correctAnswers,
                          questionAmount);

    }

    public void printQuestionsAndAnswers(){
        ArrayList<Question> questionList = this.questionPrinter.getQuestionList();
        for(int i = 0; i < questionList.size(); i++){
            Question q = questionList.get(i);
            System.out.printf("%s %s%n", q.getAnswer(), q.getQuestion());
        }
    }

    public void printFooter(){
        System.out.println("********************************************");
        System.out.println("*     Thank you for taking CPSC 24500      *");
        System.out.println("********************************************");
    }

}
