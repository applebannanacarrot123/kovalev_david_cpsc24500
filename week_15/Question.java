public class Question {

    private String question;
    private String[] options = new String[4];
    private String answer;

    /**
     * Question Object built from json array
     * @param question
     * @param a
     * @param b
     * @param c
     * @param d
     * @param answer
     */
    public Question(String question,
                    String a,
                    String b,
                    String c,
                    String d,
                    String answer){
        
        this.question = question;
        
        this.options[0] = a;
        this.options[1] = b;
        this.options[2] = c;
        this.options[3] = d;
        
        this.answer = answer;
    }

    public String getQuestion(){
        return this.question;
    }

    public void setQuestion(String s){
        this.question = s;
    }

    public String[] getOptions(){
        return this.options;
    }

    public void setOption(int index, String s){
        this.options[index] = s;
    }

    public String getAnswer(){
        return this.answer;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }

}
