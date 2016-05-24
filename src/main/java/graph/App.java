package graph;

import java.io.*;
import java.util.ArrayList;


public class App
{
    public static void main( String[] args )
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String fileName = null;
        try {
            fileName = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OrientedGraph<Integer> graph = new OrientedGraph<Integer>();
        BufferedReader fileIn = null;
        try {
            fileIn = new BufferedReader(new FileReader(fileName));
            String in = fileIn.readLine();
            while (in != null) {
                String[] numbers = in.split(" ");
                try {
                    Integer first = Integer.parseInt(numbers[0]);
                    Integer second = Integer.parseInt(numbers[1]);
                    graph.addEdge(first,second);
                }
                catch (Exception e) {

                }
                in = fileIn.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                fileIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Solver<Integer> solver = new Solver<Integer>();
        for (ArrayList<Integer> integers : solver.findCycles(graph)) {
            for (Integer integer : integers) {
                System.out.print(integer + " ");
            }
            System.out.println();
        }
    }

}
