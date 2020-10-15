import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.math.plot.Plot2DPanel;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.printf("%s%n%49s%n%s%n",
                          "*".repeat(60),
                          "International COVID-19 Mortality Rates",
                          "*".repeat(60));
        
        // Require a valid, readable file.
        boolean invalidFile = true;
        Scanner userInput = new Scanner(System.in);
        LinkedHashMap<String, ArrayList<Double>> data
                            = new LinkedHashMap<String, ArrayList<Double>>();
         
        do{
            try{
                System.out.print("Enter name of data file:");
                String response = userInput.nextLine();
                Scanner fileReader = new Scanner(new File(response));
                invalidFile = false; // Scanner was created without error

                data = readData(fileReader);
                fileReader.close();
            }
            catch(Exception e){
                System.out.println("The file could not be properly read.");
            }
        }while(invalidFile);

        // Plots
        
        do{
            String userResponse;
            ArrayList<String> countriesToPlot = new ArrayList<String>();
            
            // Get countries to plot
            boolean invalidCountries = false;
            do{
                System.out.print("Enter countries to plot separated by commas"
                               + "(or quit to end):");
                userResponse = userInput.nextLine();

                // Break if user wants to quit
                if(userResponse.trim().equalsIgnoreCase("quit")){
                    invalidCountries = true;
                    break;
                }

                ArrayList<String> countries = new ArrayList<String>();
                for(String country: userResponse.split(",")){
                    countries.add(country.trim());
                }

                // Verify countries are real
                for(String country: countries){
                    if(data.get(country) == null){
                        invalidCountries = true;
                        System.out.printf("%s is not in the data set%n",
                                          country);
                    }
                }
            
                if(!invalidCountries){
                    countriesToPlot = countries;
                }

            }while(invalidCountries);

            // Break out because user quit in inner loop
            if(invalidCountries){
                break;
            }

            if(!countriesToPlot.isEmpty()){
                // Get whether to display daily or cumulative deaths
                boolean daily = false;
                do{
                    System.out.print("[D]aily or [C]umulative?");
                    userResponse = userInput.nextLine();
                    
                    if(!userResponse.equalsIgnoreCase("d")
                        && !userResponse.equalsIgnoreCase("c")){
                        System.out.printf("%s is not D or C",
                        userResponse);
                    }
                    else{
                        if(userResponse.equalsIgnoreCase("d")){
                            daily = true;
                        }
                        break;
                    }
                }while(true);

                // Create visuals
                createPlots(data, countriesToPlot, daily);
            }
        }while(true);

        System.out.println("Wear a mask :)");
    }

    /**
     * 
     * @param fileReader as a Scanner object of valid data sheet
     * @return LinkedHashMap of country data
     */
    public static LinkedHashMap<String, ArrayList<Double>>
                                        readData(Scanner fileReader){
        LinkedHashMap<String, ArrayList<Double>> data =
                            new LinkedHashMap<String, ArrayList<Double>>();

        fileReader.nextLine(); // Skip first line of file
        while(fileReader.hasNextLine()){
            String[] line = fileReader.nextLine().split("\t");

            ArrayList<Double> countryDeaths = new ArrayList<Double>();
            for(int i = 1; i < line.length; i++){
                countryDeaths.add(Double.parseDouble(line[i]));
            }

            data.put(line[0], countryDeaths);
        }
 
        return data;
    }


    /**
     * Generates a JFrame with plotted data of the selected countries
     * @param data
     * @param countriesToPlot
     * @param daily
     */
    public static void createPlots(
                       LinkedHashMap<String, ArrayList<Double>> data,
                       ArrayList<String> countriesToPlot,
                       boolean daily){
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 500, 500);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        if(daily){frame.setTitle("Daily Deaths");}
        else{frame.setTitle("Cumulative Deaths");}

        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());
        
        Plot2DPanel plot = new Plot2DPanel();
        plot.addLegend("SOUTH");
        plot.setAxisLabels("Day", "Deaths");

        for(String country: countriesToPlot){
            // Build x values array
            double[] x = new double[data.get(country).size()];
            for(int i = 0; i < x.length; i++){
                x[i] = i;
            }

            // Build y values array, compensating for daily vs cumulative
            double[] y = new double[data.get(country).size()];
            y[0] = data.get(country).get(0);
            for(int i = 1; i < y.length; i++){
                if(daily){
                    y[i] = data.get(country).get(i)
                         - data.get(country).get(i - 1);
                }
                else{
                    y[i] = data.get(country).get(i);
                }
            }

            plot.addLinePlot(country, x, y);
        }

        pane.add(plot, BorderLayout.CENTER);
        frame.setVisible(true);
    }



}
