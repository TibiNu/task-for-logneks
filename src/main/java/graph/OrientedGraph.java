package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class oriented graph
 */
public class OrientedGraph<T> {

    private HashMap<T, List<T>> vertexMap = new HashMap<T, List<T>>();

    public HashMap<T, List<T>> getVertexMap() {
        return vertexMap;
    }

    public List<T> getNextVertexes(T startVertex) {
        return vertexMap.get(startVertex);
    }

    public void addVertex(T newVertex) {
        if (!hasVertex(newVertex)) {
            vertexMap.put(newVertex, new ArrayList<T>());
        }
    }

    public boolean hasVertex(T vertex) {
        return vertexMap.containsKey(vertex);
    }

    public void addEdge(T beginVertex, T endVertex) {
        addVertex(endVertex);
        if (beginVertex.equals(endVertex)) return;
        if (!hasVertex(beginVertex)) {
            ArrayList<T> endVertexes = new ArrayList<T>();
            endVertexes.add(endVertex);
            vertexMap.put(beginVertex, endVertexes);
        }
        else {
            vertexMap.get(beginVertex).add(endVertex);
        }
    }
    public boolean hasEdge(T beginVertex, T endVertex) {
        if (!hasVertex(beginVertex) || !hasVertex(endVertex)) return false;
        return vertexMap.get(beginVertex).contains(endVertex);
    }
}
