import java.util.Scanner;
import java.util.Random;

public class GameZone {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int choice;

        System.out.printf("******************************%n"
                         +"%27s%n"
                         +"******************************%n",
                          "Welcome to the Game Zone");
        
        /* Main loop */
        do{
            System.out.printf("%nWhat would you like to play?%n"
                             +"1. Twenty-one%n"
                             +"2. Rock Paper Scissors%n"
                             +"3. Neither - I'm done!%n"
                             +"Please enter the number of your choice: ");
            choice = input.nextInt();

            twentyOne(input, choice);
            rockPaperScissors(choice);
        } while(choice != 3);

        System.out.println("Thank you for playing");
    }

    /**
     * Traditional gambling game of 21
     * @param input (Scanner input)
     * @param menuPosition (To determine if function runs) (1 for twentyOne)
     */
    public static void twentyOne(Scanner input, int menuPosition){
        if(menuPosition == 1){
            int playerDraw = 0;
            Random deck = new Random();

            do {
                int newCard = (deck.nextInt(11) + 1); // Next card [1,11]
                playerDraw = playerDraw + newCard;
                System.out.printf("You drew %d.%n"
                                + "Your total is %d.%n",
                                   newCard,
                                   playerDraw);

                if(playerDraw < 21){
                    System.out.print("Draw another card? ");
                }

            } while(playerDraw < 21 && input.next().equalsIgnoreCase("y"));

            if(playerDraw > 21){
                System.out.println("You lost.");
            } 
            else if(playerDraw == 21){
                System.out.println("You won!");
            } 
            else {
                int computerDraw = deck.nextInt(8) + 13;
                System.out.printf("The computer drew %d.%n", computerDraw);
                
                if(playerDraw > computerDraw){
                    System.out.println("You won!");
                } 
                else {
                    System.out.println("You lost.");
                }
            }
        }
    }

    /**
     * Traditional way of settling disagreements
     * @param menuPosition (To determine if function runs) (2 for rps)
     */
    public static void rockPaperScissors(int menuPosition){
        if(menuPosition == 2){
            // Rock=0 Paper=1 Scissors=2
            Random outcomes = new Random();
            int player = outcomes.nextInt(3);
            int computer = outcomes.nextInt(3);

            System.out.printf("You played %s, the computer played %s%n",
                              intToRockPaperScissor(player),
                              intToRockPaperScissor(computer));
            
            if((player == 0 && computer == 1) // Rock v Paper
            || (player == 1 && computer == 2) // Paper v Scissors
            || (player == 2 && computer == 0)){ // Scissors v Rock
                System.out.println("You lost");
            }
            else if(player == computer){
                System.out.println("You tied.");
            }
            else{
                System.out.println("You won!");
            }
        }
    }

    /**
     * Helper function for rockPaperScissors
     * @param n Rock=0 Paper=1 Scissors=2
     * @return String corresponding to int
     */
    public static String intToRockPaperScissor(int n){
        // Rock=0 Paper=1 Scissors=2
        if(n == 0){
            return "Rock";
        }
        else if(n == 1){
            return "Paper";
        }
        else{
            return "Scissors";
        }
    }
}