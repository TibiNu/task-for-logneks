package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by HP on 19.05.2016.
 */
public class Solver<T> {
    LinkedList<ArrayList<T>> allCycles = new LinkedList<ArrayList<T>>();

    public  LinkedList<ArrayList<T>> findCycles(OrientedGraph<T> graph) {
        allCycles = new LinkedList<ArrayList<T>>();
        for (T vertex : graph.getVertexMap().keySet()) {

            ArrayList<T> visitedVertex = new ArrayList<T>();
            visitedVertex.add(vertex);
            T newVertex = graph.getNextVertexes(vertex).get(0);
            dfs(graph,visitedVertex,newVertex);
        }
        return allCycles;
    }
    public  LinkedList<ArrayList<T>> getCycles(){
        return allCycles;
    }
    public void findCyclesFromVertex(OrientedGraph<T> graph, T startVertex){
        ArrayList<T> visitedVertex = new ArrayList<T>();
        visitedVertex.add(startVertex);
        List<T> nextVertexes = graph.getNextVertexes(startVertex);
        if (nextVertexes.size()>0) {
            T newVertex =nextVertexes.get(0);
            dfs(graph, visitedVertex, newVertex);
        }
    }
    public void dfs(OrientedGraph<T> graph, ArrayList<T> visitedVertexes, T newVertex) {
        if(visitedVertexes.get(0).equals(newVertex)) {
            visitedVertexes.add(newVertex);
            addToCycles(visitedVertexes);
            visitedVertexes.remove(visitedVertexes.size()-1);
//            T cur = visitedVertexes.get(visitedVertexes.size()-1);
//            visitedVertexes.remove(visitedVertexes.size()-1);
//            List<T> nextVertexes = graph.getNextVertexes(visitedVertexes.get(visitedVertexes.size() - 1));
//            int curIndex = nextVertexes.indexOf(cur);
//            while (true) {
//                curIndex++;
//                if (curIndex < nextVertexes.size())
//                    break;
//                if (visitedVertexes.size() <= 1) return;
//                cur = visitedVertexes.get(visitedVertexes.size()-1);
//                visitedVertexes.remove(visitedVertexes.size()-1);
//                nextVertexes = graph.getNextVertexes(visitedVertexes.get(visitedVertexes.size() - 1));
//                curIndex = nextVertexes.indexOf(cur);
//            }
            T nextVertex = getNextVertexForDfs(graph,visitedVertexes);
            if (nextVertex == null) return;
            dfs(graph,visitedVertexes,nextVertex);
        }
        else if (visitedVertexes.lastIndexOf(newVertex) > 0) {
//            T cur = visitedVertexes.get(visitedVertexes.size()-1);
//            visitedVertexes.remove(visitedVertexes.size()-1);
//
//            List<T> nextVertexes = graph.getNextVertexes(visitedVertexes.get(visitedVertexes.size() - 1));
//            int curIndex = nextVertexes.indexOf(cur);
//            while (true) {
//                curIndex++;
//                if (curIndex < nextVertexes.size())
//                    break;
//                if (visitedVertexes.size() <= 1) return;
//                cur = visitedVertexes.get(visitedVertexes.size()-1);
//                visitedVertexes.remove(visitedVertexes.size()-1);
//                nextVertexes = graph.getNextVertexes(visitedVertexes.get(visitedVertexes.size() - 1));
//                curIndex = nextVertexes.indexOf(cur);
//            }
            T nextVertex = getNextVertexForDfs(graph,visitedVertexes);
            if (nextVertex == null) return;
            dfs(graph,visitedVertexes,nextVertex);
        }
        else {
            List<T> nextVertexes = graph.getNextVertexes(newVertex);
            if (nextVertexes.size() > 0) {
                visitedVertexes.add(newVertex);
                dfs(graph,visitedVertexes,nextVertexes.get(0));
            }
            else {
//                T cur = visitedVertexes.get(visitedVertexes.size()-1);
//                visitedVertexes.remove(visitedVertexes.size()-1);
//                nextVertexes = graph.getNextVertexes(visitedVertexes.get(visitedVertexes.size() - 1));
//                int curIndex = nextVertexes.indexOf(cur);
//                while (true) {
//                    curIndex++;
//                    if (curIndex < nextVertexes.size())
//                        break;
//                    if (visitedVertexes.size() <= 1) return;
//                    cur = visitedVertexes.get(visitedVertexes.size()-1);
//                    visitedVertexes.remove(visitedVertexes.size()-1);
//                    nextVertexes = graph.getNextVertexes(visitedVertexes.get(visitedVertexes.size() - 1));
//                    curIndex = nextVertexes.indexOf(cur);
//                }
                T nextVertex = getNextVertexForDfs(graph,visitedVertexes);
                if (nextVertex == null) return;
                dfs(graph,visitedVertexes,nextVertex);
            }
        }
    }

    public T getNextVertexForDfs(OrientedGraph graph, ArrayList<T> visitedVertexes){
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
    public void addToCycles(ArrayList<T> visitedVertexes) {
        if (isNewCycle(visitedVertexes))
            allCycles.add(new ArrayList<T>(visitedVertexes));
    }
    public boolean isNewCycle(ArrayList<T> visitedVertexes) {
        for (ArrayList<T> cycle : allCycles) {
            if (cyclesIsEqual(cycle,visitedVertexes)) return false;
        }
        return true;
    }
    public boolean cyclesIsEqual(ArrayList<T> firstCycle, ArrayList<T> secondCycle) {
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
