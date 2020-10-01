import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class BaseballStandingsAnalyzer{
    public static void main(String[] args){
        System.out.printf("%s%n"
                        + "%37s%n"
                        + "%s%n",
                          "*".repeat(42),
                          "Baseball Standings Analyzer d:^)",
                          "*".repeat(42)); // Hello message
        
        Scanner input = new Scanner(System.in);
        File datasheet = new File("week_5\\baseball_standings.txt");
        ArrayList<String> leagues = new ArrayList<String>();
        ArrayList<ArrayList<String[]>> standings =
                                       new ArrayList<ArrayList<String[]>>();
        String response = "";

        // Get file path if possible
        try {
            response = getUserResponse(input,
                                       "Input the file name "
                                     + "of the datasheet "
                                     + "or enter 1 to use "
                                     + "the default datasheet");

            File possibleDatasheet = new File(response);
            if(possibleDatasheet.exists()){
                datasheet = possibleDatasheet;
            }
            else{
                System.out.printf("Defaulting to %s%n",
                                  datasheet.getAbsolutePath());
            }
        }
        catch(Exception e){
            System.out.printf("%s is not visible to this program%n"
                            + "Defaulting to %s%n",
                              response,
                              datasheet.getAbsolutePath());
        }
        retrieveStandings(datasheet, leagues, standings);

        // Main loop
        do {
            response = getUserResponse(input,
                                       buildStandingsQuestion(leagues));
            if(response.equals("7")){
                getOverallStandings(standings);
            }
            else if(response.equals("8")){
                input.close();
                return;
            }
            else{
                getLeagueStandings(standings, Integer.parseInt(response));
            }

            System.out.println();
        } while(true);
    }


    /**
     * Prints out the standings of teams in a single league
     */
    public static void getLeagueStandings(
                            ArrayList<ArrayList<String[]>> standings,
                            int i){
        System.out.printf("%-16s%8s%8s%8s%8s%n%s%n",
                          "Team",
                          "Wins",
                          "Losses",
                          "Win %",
                          "Behind",
                          "-".repeat(48));
        for(String[] leagueTeams: standings.get(i - 1)){
            System.out.printf("%-16s%8s%8s%8s%8s%n",
                              leagueTeams[0],
                              leagueTeams[1],
                              leagueTeams[2],
                              leagueTeams[3],
                              leagueTeams[4]);
        }
    }

    /**
     * Prints out the standings of the teams in all leagues
     */
    public static void getOverallStandings(
                            ArrayList<ArrayList<String[]>> standings){
        ArrayList<String[]> consolidatedLeagues = new ArrayList<String[]>();
        for(ArrayList<String[]> league: standings){
            for(String[] team: league){
                consolidatedLeagues.add(team);
            }
        }

        consolidatedLeagues.sort(TeamComparator);
        System.out.printf("%-16s%8s%8s%n%s%n",
                          "Team",  
                          "Wins",
                          "Losses",
                          "-".repeat(32));
        for(String[] team: consolidatedLeagues){
            System.out.printf("%-16s%8s%8s%n",
                              team[0],
                              team[1],
                              team[2]);
        }
    }

    /**
     * Returns the question posed to users to select standing to view 
     */    
    public static String buildStandingsQuestion(ArrayList<String> leagues){
        String question = String.format("Which standings would "
                                      + "you like to see?%n");
        int i = 0;
        for(i = 0; i < leagues.size(); i++){
            question += String.format("%s. %s%n",
                                      i + 1,
                                      leagues.get(i));
        }
        question += String.format("%s. Overall%n%s. Exit%n",
                                  i + 1,
                                  i + 2);
        question += "Enter the number of your choice:";
        return question;
    }

    /**
     * Returns a VALID reponse given by the user,
     * determined by the valid responses array.
     * In this case only a file name or a number is
     * a valid input.
     * @param input as Scanner object
     * @param question as a string
     * @return String of valid user response
     */
    public static String getUserResponse(Scanner input, String question){
        String[] validResponses = {"1", "2", "3", "4",
                                   "5", "6", "7", "8"};
        String response;
        do {
            System.out.printf("%s%n", question);
            response = input.next();
            
            /* Verify validity of response */
            // integer response
            for(String validResponse: validResponses){
                if(response.equals(validResponse)){
                    return response;
                }
            }

            // file path response
            File responsePath = new File(response);
            if(responsePath.exists()){
                return response;
            }

            System.out.printf("\"%s\" is not a valid response.%n"
                            + "Please enter a valid response.%n%n",
                               response);

            input.nextLine();
        } while(true);
    }


    /**
     * Returns the standings of a baseball season as a formatted ArrayList.
     * First item are leagues and their names
     * example -> [
     *              league,
     *              league2,
     *              ...
     *            ]
     * 
     * Second item is the leagues' team standings
     * the teams' grouping index is corresponding
     * to their leagues index in the first item
     * example -> [
     *              [[team, wins, losses], [team2, wins, losses], ...]],
     *              [[team, wins, losses], [team2, wins, losses], ...]],
     *              ...                                                         
     *            ]
     *
     * @param dataSheet
     * @return ArrayList of standings 
     */
    public static void retrieveStandings(File dataSheet,
                                ArrayList<String> leagues,
                                ArrayList<ArrayList<String[]>> standings){

        try(Scanner fileScanner = new Scanner(dataSheet);){
            boolean newLeague = false;
            newLeague = false;
            ArrayList<String[]> currentLeague = new ArrayList<String[]>();

            while(fileScanner.hasNextLine()){
                newLeague = false;

                while(!newLeague && fileScanner.hasNextLine()){
                    String nextLine = fileScanner.nextLine().trim();

                    if(isTeamLine(nextLine)){
                        currentLeague.add(teamLineToArray(nextLine));

                    }
                    else{
                        if(currentLeague.size() > 0){
                            // Prevents calculations on first line of datasheet 
                            calculateGamesBehind(currentLeague);
                            standings.add(currentLeague);
                        }
                        leagues.add(nextLine);
                        newLeague = true;
                        currentLeague = new ArrayList<String[]>();
                    }
                }
            }

            standings.add(currentLeague);
        }
        catch(Exception e){
            System.out.printf("File %s is not avaliable to read.%n",
                              dataSheet);
        }
    }



    /**
     * Quality of life function to check if a datasheet line should
     * start a new arraylist or append to the current one
     * 
     * @param line from datasheet
     * @return boolean whether line is a team statistics line
     */
    public static boolean isTeamLine(String line){
        return line.matches("(.+)\t(\\d+)\t(\\d+)");
    }

    /**
     * Returns a String array of a team's statistics
     * example ->
     *        "Team 21  14"
     *        converted to 
     *        ["Team", "21", "14", "0.6", ""]  
     * 
     * @param line from standings file
     * @return String[5] of reformatted team statistics
     */
    public static String[] teamLineToArray(String line){
        String[] output = new String[5];
        String[] searchResults = line.split("\t");
        // Tab is a terrible visual delimiter in my opinion
        // the team statistics looked different on chrome, firefox,
        // wordpad, notepad and word.
        // It was also impossible to keep the format while copying and pasting
        // from browser to file.
        // If you are reading this Mr. Klump, please never use it again.
        output[0] = searchResults[0].trim();
        output[1] = searchResults[1].trim();
        output[2] = searchResults[2].trim();
        output[3] = String.format("%.3f",
            (double)(Integer.parseInt(output[1]))
                  / (Integer.parseInt(output[1])
                     + Integer.parseInt(output[2])));
        
        output[4] = "";

        return output;
    }

    /**
     * Calculates the amount of games behind the bad teams are compared
     * to the best team in their league
     * @param teamStandings
     */
    public static void calculateGamesBehind(ArrayList<String[]> teamStandings){
        teamStandings.sort(TeamComparator);
        int bestTeamsWins = Integer.parseInt(teamStandings.get(0)[1]);
        int bestTeamsLosses = Integer.parseInt(teamStandings.get(0)[2]);
        for(int i = 1; i < teamStandings.size(); i++){
            String[] currentTeam = teamStandings.get(i);
            int currentTeamWins = Integer.parseInt(currentTeam[1]);
            int currentTeamLosses = Integer.parseInt(currentTeam[2]);
            double gamesBehind = (
                                 (bestTeamsWins - bestTeamsLosses)
                                 - (currentTeamWins - currentTeamLosses)
                                 ) / 2.0;
            currentTeam[4] = String.format("%.1f", gamesBehind);
        }
    }


    /**
     * Comparator interface for sorting the teams with the
     * built in sort function
     */
    public static Comparator<String[]> TeamComparator
                                       = new Comparator<String[]>(){
        public int compare(String[] team1, String[] team2){
            double outcome = Double.parseDouble(team2[3])
                           - Double.parseDouble(team1[3]);
            if(outcome > 0){return 1;}
            else if(outcome < 0){return -1;}
            return 0;
        }
    };
}