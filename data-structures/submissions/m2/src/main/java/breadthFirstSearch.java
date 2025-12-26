import java.util.*;

interface UndirectedGraph<Vertex> {
    int numEdges(); // return number of edges
    int numVertices(); // return number of vertices
    int deg(Vertex v); // return the "degree" of v

    Iterable<Vertex> adj(Vertex v); // return iterable of adjacent vertices to v

    boolean isEdge(Vertex u, Vertex v); // is there an edge between u and v?

    void addVertex(Vertex v); // add a new vertex

    void addEdge(Vertex u, Vertex v); // add a new edge between u and v

    void removeEdge(Vertex u, Vertex v); // remove an edge between u and v
}

class UndirectedAdjMap<Vertex> implements UndirectedGraph<Vertex> {

    private Map<Vertex, Set<Vertex>> graph;

    UndirectedAdjMap() {
        graph = new HashMap<Vertex, Set<Vertex>>();
    }

    @Override
    public int numEdges() { // number of edges in a graph (value)
        int count = 0;
        for (Set<Vertex> nbrs: graph.values()) {
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
        Set<Vertex> nbrs = graph.get(v);
        return (nbrs == null) ? null: nbrs.size();
    }

    @Override
    public Iterable<Vertex> adj(Vertex v) { // set of neighbours
        Set<Vertex> nbrs = graph.get(v);
        return nbrs;
    }

    @Override
    public boolean isEdge(Vertex u, Vertex v) {
        return graph.containsKey(u) && graph.get(u).contains(v);
    }

    @Override
    public void addVertex(Vertex v) {
        graph.putIfAbsent(v, new HashSet<>());
    }

    @Override
    public void addEdge(Vertex u, Vertex v) {
        this.addVertex(u);
        this.addVertex(v);
        graph.get(u).add(v);
        graph.get(v).add(u);
    }

    @Override
    public void removeEdge(Vertex u, Vertex v) {
        if (graph.containsKey(u))
            graph.get(u).remove(v);
        if (graph.containsKey(v))
            graph.get(v).remove(u);
    }
}

public class breadthFirstSearch{
    static <Vertex> Map<Vertex, Vertex> nbrs(UndirectedGraph<Vertex> g, Set<Vertex> F) {
        Map<Vertex, Vertex> ans = new HashMap<>();
        for (Vertex u: F) {
            for (Vertex v: g.adj(u)) { // g.adj(u) >> Iterable<Vertex>
                ans.put(v, u);
            }
        }
        return ans;
    }

    static <Vertex> Map<Vertex, Vertex> bfs(UndirectedGraph<Vertex> g, Vertex s) {
        Map<Vertex, Vertex> F = new HashMap<>(); Map<Vertex, Vertex> X = new HashMap<>();
        F.put(s, null); X.put(s, null);
        while (!F.isEmpty()) {
            F = nbrs(g, F.keySet());
            F.keySet().removeAll(X.keySet()); // remove all previously seen vertices
            X.putAll(F);
        }
        return X;
    }

    static <Vertex> void findShortest(UndirectedGraph<Integer> G, Integer a, Integer b) {
        Map<Vertex, Vertex> visited = (Map<Vertex, Vertex>) bfs(G, a);
        System.out.println(visited);
        if (!visited.containsKey(b)) { return; }
        List<Integer> path = new ArrayList<>();
        for (Integer curr = b; curr != null; curr = (Integer) visited.get(curr)) // since the last "visited" should be the start.
            path.add(curr);
        Collections.reverse(path); // path originally is b to a.

        System.out.println(path);
    }

    public static void main(String[] args) {
        UndirectedGraph<Integer> foo = new UndirectedAdjMap<>();

        foo.addVertex(0);
        foo.addVertex(1);
        foo.addVertex(2);
        foo.addVertex(3);

        foo.addEdge(0,2);
        foo.addEdge(0,3);
        foo.addEdge(2,3);
        foo.addEdge(1,3);

        findShortest(foo, 2, 1); // 2, 3, 1
    }

}
