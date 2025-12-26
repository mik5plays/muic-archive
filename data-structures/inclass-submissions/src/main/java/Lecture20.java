import java.util.HashSet;
import java.util.Set;

//interface UndirectedGraph<Vertex> {
//    int numEdges(); // return number of edges
//    int numVertices(); // return number of vertices
//    int deg(Vertex v); // return the "degree" of v
//
//    Iterable<Vertex> adj(Vertex v); // return iterable of adjacent vertices to v
//
//    boolean isEdge(Vertex u, Vertex v); // is there an edge between u and v?
//
//    void addVertex(Vertex v); // add a new vertex
//
//    void addEdge(Vertex u, Vertex v); // add a new edge between u and v
//
//    void removeEdge(Vertex u, Vertex v); // remove an edge between u and v
//}

public class Lecture20<V> {
    Set<V> nbrs(UndirectedGraph<V> g, Set<V> F) {
        Set<V> ans = new HashSet<>();
        for (V u: F) {
            for (V v: g.adj(u)) { // g.adj(u) >> Iterator<Vertex>
                ans.add(v);
            }
        }
        return ans;
    }

    Set<V> bfs(UndirectedGraph<V> g, V s) {
        int i = 0; Set<V> F = new HashSet<>(); Set<V> X = new HashSet<>();
        F.add(s); X.add(s);
        while (!F.isEmpty()) {
            Set<V> newF = nbrs(g, F);
            newF.removeAll(X); // remove all previously seen vertices
            X.addAll(newF);
            F = newF;
        }
        return X;
    }
}
