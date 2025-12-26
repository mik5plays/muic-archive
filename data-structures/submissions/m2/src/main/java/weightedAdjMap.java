import java.util.*;

interface WeightedUndirectedGraph<Vertex, Label> {
    int numEdges(); // return number of edges
    int numVertices(); // return number of vertices
    int deg(Vertex v); // return the "degree" of v

    Map<Vertex, Label> adj(Vertex v); // return adjacent vertices to v

    boolean isEdge(Vertex u, Vertex v); // is there an edge between u and v?

    void addVertex(Vertex v); // add a new vertex

    void addEdge(Vertex u, Vertex v, Label weight); // add a new edge between u and v

    void removeEdge(Vertex u, Vertex v); // remove an edge between u and v

    Label getWeight(Vertex u, Vertex v);
}

class weightedUndirectedAdjMap<Vertex, Label> implements WeightedUndirectedGraph<Vertex, Label> {

    public Map<Vertex, Map<Vertex, Label>> graph;

    weightedUndirectedAdjMap() {
        graph = new HashMap<Vertex, Map<Vertex, Label>>();
    }

    @Override
    public int numEdges() { // number of edges in a graph (value)
        int count = 0;
        for (Map<Vertex, Label> nbrs: graph.values()) {
            count += nbrs.size();
        }
        return count / 2; // since edges are counted twice
    }

    @Override
    public int numVertices() { // number of vertices in a graph (key)
        return graph.size();
    }

    @Override
    public int deg(Vertex v) { // number of neighbours a vertex has
        Map<Vertex, Label> nbrs = graph.get(v);
        return (nbrs == null) ? null: nbrs.size();
    }

    @Override
    public Map<Vertex, Label> adj(Vertex v) { // set of neighbours
        Map<Vertex, Label> nbrs = graph.get(v);
        return nbrs;
    }

    @Override
    public boolean isEdge(Vertex u, Vertex v) {
        return graph.containsKey(u) && graph.get(u).containsKey(v);
    }

    @Override
    public void addVertex(Vertex v) {
        graph.putIfAbsent(v, new HashMap<Vertex, Label>());
    }

    @Override
    public void addEdge(Vertex u, Vertex v, Label weight) {
        this.addVertex(u);
        this.addVertex(v);
        graph.get(u).put(v, weight);
        graph.get(v).put(u, weight);
    }

    @Override
    public void removeEdge(Vertex u, Vertex v) {
        if (graph.containsKey(u))
            graph.get(u).remove(v);
        if (graph.containsKey(v))
            graph.get(v).remove(u);
    }

    @Override
    public Label getWeight(Vertex u, Vertex v) {
        if (graph.get(u) != null) {
            return graph.get(u).getOrDefault(v, null);
        }
        else {
            return graph.get(v).getOrDefault(u, null);
        }
    }
}

public class weightedAdjMap {
}
