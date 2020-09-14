import java.util.Random;

public class CircleCalc {
    public static void main(String[] args){
        Random radius_maker = new Random();
        double radius = radius_maker.nextDouble() * 25;
        
        double circle_circumference = circumference(radius);
        double circle_area = area(radius);
        
        System.out.printf("Circle radius: %.2f%n"
                        + "Circumference: %.2f%n"
                        + "Area: %.2f%n",
                         radius,
                         circle_circumference,
                         circle_area);
    }

    /**
     * Returns the circumference of a circle given the radius
     * @param radius
     * @return circumference of circle
     */
    public static double circumference(double radius){
        return 2 * Math.PI * radius;
    }

    /**
     * Returns the area of a circle given the radius
     * @param radius
     * @return area of circle
     */
    public static double area(double radius){
        return Math.PI * radius * radius;
    }
}