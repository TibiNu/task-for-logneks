package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Class Solver find simple cycles in oriented graph
 */
public class Solver<T> {
    LinkedList<ArrayList<T>> allCycles = new LinkedList<ArrayList<T>>();

    public  LinkedList<ArrayList<T>> findCycles(OrientedGraph<T> graph) {
        allCycles = new LinkedList<ArrayList<T>>();
        for (T vertex : graph.getVertexMap().keySet()) {
            findCyclesFromVertex(graph,vertex);
        }
        return allCycles;
    }

    public  LinkedList<ArrayList<T>> getCycles(){
        return allCycles;
    }

    protected void findCyclesFromVertex(OrientedGraph<T> graph, T startVertex){
        ArrayList<T> visitedVertex = new ArrayList<T>();
        visitedVertex.add(startVertex);
        List<T> nextVertexes = graph.getNextVertexes(startVertex);
        if (nextVertexes.size()>0) {
            T newVertex =nextVertexes.get(0);
            dfs(graph, visitedVertex, newVertex);
        }
    }

    protected void dfs(OrientedGraph<T> graph, ArrayList<T> visitedVertexes, T newVertex) {
        if(visitedVertexes.get(0).equals(newVertex)) {
            visitedVertexes.add(newVertex);
            addToCycles(visitedVertexes);
            visitedVertexes.remove(visitedVertexes.size()-1);
        }
        else if (!(visitedVertexes.lastIndexOf(newVertex) > 0)) {
            List<T> nextVertexes = graph.getNextVertexes(newVertex);
            if (nextVertexes.size() > 0) {
                visitedVertexes.add(newVertex);
                dfs(graph,visitedVertexes,nextVertexes.get(0));
                return;
            }
        }
        T nextVertex = getNextVertexForDfs(graph,visitedVertexes);
        if (nextVertex == null) return;
        dfs(graph,visitedVertexes,nextVertex);
    }

    protected T getNextVertexForDfs(OrientedGraph graph, ArrayList<T> visitedVertexes){
        T cur = visitedVertexes.get(visitedVertexes.size()-1);
        visitedVertexes.remove(visitedVertexes.size()-1);
        List<T> nextVertexes = graph.getNextVertexes(visitedVertexes.get(visitedVertexes.size() - 1));
        int curIndex = nextVertexes.indexOf(cur);
        while (true) {
            curIndex++;
            if (curIndex < nextVertexes.size())
                break;
            if (visitedVertexes.size() <= 1) return null;
            cur = visitedVertexes.get(visitedVertexes.size()-1);
            visitedVertexes.remove(visitedVertexes.size()-1);
            nextVertexes = graph.getNextVertexes(visitedVertexes.get(visitedVertexes.size() - 1));
            curIndex = nextVertexes.indexOf(cur);
        }
        return nextVertexes.get(curIndex);
    }

    protected void addToCycles(ArrayList<T> newCycle) {
        if (isNewCycle(newCycle))
            allCycles.add(new ArrayList<T>(newCycle));
    }

    protected boolean isNewCycle(ArrayList<T> newCycle) {
        for (ArrayList<T> cycle : allCycles) {
            if (cyclesIsEqual(cycle,newCycle)) return false;
        }
        return true;
    }

    protected boolean cyclesIsEqual(ArrayList<T> firstCycle, ArrayList<T> secondCycle) {
        if (firstCycle.size() != secondCycle.size()) return false;
        if (firstCycle.size() == 0) return true;
        int firstIndex = 0;
        T current = firstCycle.get(firstIndex);
        int secondIndex = secondCycle.indexOf(current);
        if (secondIndex < 0) return false;
        while (firstIndex < firstCycle.size() - 1) {
            firstIndex++;
            secondIndex++;
            if(secondIndex >= secondCycle.size()) secondIndex=1;
            if (!firstCycle.get(firstIndex).equals(secondCycle.get(secondIndex))) return false;
        }
        return true;
    }


}
