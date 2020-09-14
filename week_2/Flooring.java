public class Flooring {
    public static void main(String[] args){
        double top = 10;
        double bottom = 20;
        double left = 25;
        double right = 13;

        double bottom_half_area = right * bottom;
        double top_left_half_area = top * (left - right);
        double top_right_triangle_area = (bottom - top) 
                                          * (left - right)
                                           * 1.20; // Extra 20% for angle

        double total_area = bottom_half_area
                             + top_left_half_area
                              + top_right_triangle_area;
        
        System.out.printf("Room area: %.2f sq ft.%n"
                        + "Packages needed: %d.%n"
                        + "  Packages cost: $%.2f.%n"
                        + "    ($%.2f / sq ft)",
                        total_area,
                        boardsToPackages(areaToBoards(total_area)),
                        roomAreaToCost(total_area),
                        total_area / roomAreaToCost(total_area));
    }

    /**
     * Room area in feet to cost of flooring in dollars
     * @param area
     * @return Cost in dollars
     */
    public static double roomAreaToCost(double area){
        int boards_for_room = areaToBoards(area);
        int board_packs = boardsToPackages(boards_for_room);
        double cost = packagesToCost(board_packs);
        
        return cost;
    }


    /**
     * Feet to inches converter
     * @param feet
     * @return feet in inches
     */
    public static double feetToInches(double feet){
        return feet * 12;
    }

    /**
     * Converts square inches to equivalent board amount
     * @param area in feet
     * @return Area in board amount
     */
    public static int areaToBoards(double area){
        double area_in_inches = feetToInches(area);
        double individual_board_area = 30.0 * 6.0;

        return (int) (Math.ceil(area_in_inches/ individual_board_area));
    }

    /**
     * Convert board amount to package amount
     * @param boards
     * @return Packages of 6 boards
     */
    public static int boardsToPackages(int boards){
        return (int) (Math.ceil((double) boards / 6.0));
    }

    /**
     * Convert board packages to price in dollars
     * @param packages
     * @return Cost of packages
     */
    public static double packagesToCost(int packages){
        return packages * 24.99;
    }
}