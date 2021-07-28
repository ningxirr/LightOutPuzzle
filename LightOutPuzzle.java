//Nasreeya 6213128
//Pojanut 6213205
//Palakorn 6213206
//Pakkapond 6213207

import java.util.ArrayDeque;
import java.util.List;
import java.util.Scanner;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class LightOutPuzzle {
    private int NumberRow;
    private String Input;
    private State StartState;
    private State Destination;
    private Graph<State, DefaultEdge> StateGraph;
    private ShortestPathAlgorithm<State, DefaultEdge> DijkstraSolve;

    public static void main(String[] args) {
        new LightOutPuzzle();
    }

    public LightOutPuzzle() {
        boolean Run = true;
        while (Run) {
            try {
                Scanner Scanner1 = new Scanner(System.in);
                System.out.println("Number of rows for square grid (3 or 4)= ");
                NumberRow = Scanner1.nextInt();
                if (NumberRow >= 3 && NumberRow < 5) {
                    System.out.printf("Initial states (%d bits, left to right, line by line) = \n", NumberRow * NumberRow);
                    System.out.println(" ------------------------------------------------------------- ");
                    System.out.println("|                  NOTE: 1 is ON and 0 is OFF                 |");
                    System.out.println("|  if some bits are not equal 1 or 0 ,they will be set to 0   |");
                    System.out.println(" ------------------------------------------------------------- ");
                    Input = Scanner1.next();
                    while (NumberRow * NumberRow != Input.length()) {
                        System.out.printf("Number of bits must be equal %d, Enter new initial states\n", NumberRow * NumberRow);
                        Input = Scanner1.next();
                    }
                    StartState = new State(Input);
                    Destination = new State(0);
                    solveGraph();

                    boolean Con = true;
                    while (Con) {
                        Scanner Scanner2 = new Scanner(System.in);
                        System.out.println("\nContinue next puzzle (y/n) : ");
                        String run = Scanner2.next();
                        if (run.equalsIgnoreCase("n") || run.equalsIgnoreCase("no")) {
                            Run = !Run;
                            Con = !Con;
                        } else if (run.equalsIgnoreCase("y") || run.equalsIgnoreCase("yes")) {
                            Con = !Con;
                        } else {
                            System.out.println("Invalid input, please try again");
                        }
                    }
                } else {
                    System.out.println("Number of rows for square grid must be equal 3 or 4, please try again\n");
                }
            } catch (Exception e) {
                System.out.println("Invalid input, please try again\n");
            }
        }
    }

    public void solveGraph() {
        ArrayDeque<State> StateQueue = new ArrayDeque<>();
        StateGraph = new SimpleGraph<>(DefaultEdge.class);
        StateQueue.add(StartState);
        StateGraph.addVertex(StartState);
        loop:
        {
            while (!StateQueue.isEmpty()) {
                State CurrentState = StateQueue.pollFirst();
                for (int i = 0; i < NumberRow; i++) {
                    for (int j = 0; j < NumberRow; j++) {
                        State NewState = CurrentState.toggle(i, j);
                        if (!StateGraph.containsVertex(NewState)) {
                            Graphs.addEdgeWithVertices(StateGraph, CurrentState, NewState);
                            StateQueue.addLast(NewState);
                        }
                        if (NewState.equals(Destination)) {
                            break loop;
                        }
                    }
                }
            }
        }
        if (!StateGraph.containsVertex(Destination)) {
            System.out.printf("No solution\n");
        } else {
            printSolution();
        }
    }

    public void printSolution() {
        DijkstraSolve = new DijkstraShortestPath<>(StateGraph);
        List<State> Path = DijkstraSolve.getPath(StartState, Destination).getVertexList();
        int i = 0;
        for (State s : Path) {
            if (i != 0) {
                System.out.printf("\n>>> Move %d : ", i);
                s.printmove();
                s.printState();
            } else {
                s.printState();
                System.out.printf("\n%d moves to turn off all lights\n", Path.size() - 1);
            }
            i++;
        }
    }
}
