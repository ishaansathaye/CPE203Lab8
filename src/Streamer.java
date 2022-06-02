import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Streamer
{
    public static void readPoints(List<Point> points, String fileName)
            throws FileNotFoundException
    {
        File inFile = new File(fileName);
        Scanner in = new Scanner(inFile);

        while(in.hasNext())
        {
            String [] data = in.nextLine().split(",");
            double x = Double.parseDouble(data[0]);
            double y = Double.parseDouble(data[1]);
            double z = Double.parseDouble(data[2]);
            points.add(new Point(x,y,z));
        }
    }

    public static void writePoints(List<Point> points, String fileName) throws FileNotFoundException {
        PrintStream p = new PrintStream(fileName);

        for(Point pt: points)
        {
            p.println(pt.x + ", " + pt.y + ", "+ pt.z);
        }
        p.close();
    }

    public static void main(String[] args) {
        try  {
            List<Point> points = new ArrayList<>();

            readPoints(points, "positions.txt");

            Point translatedPoint = new Point(-150, -37, 0);

            List<Point> newPoints = points.stream().filter(p -> p.z <= 2.0)
              .map(p -> new Point(p.x*0.5, p.y*0.5, p.z*0.5))
              .map(p -> new Point(p.translate(translatedPoint)))
              .collect(Collectors.toList());

            writePoints(newPoints, "drawMe.txt");
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}