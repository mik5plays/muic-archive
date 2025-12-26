import java.util.*;

class DisjointSet<T> {
    // similar to GeeksForGeeks' disjoint set implementation
    private Map<T, T> parent = new HashMap<>();
    private Map<T, Integer> rank = new HashMap<>();

    void makeSet(T item) {
        parent.put(item, item);
        rank.put(item, 0);
    }

    boolean isConnected(T x, T y) {
        return find(x) == find(y);
    }

    T find(T item) {
//        if (parent.get(item) != item) {
//            parent.put(item, find(parent.get(item)));
//        }
        return parent.get(item);
    }

    void link(T x, T y) {
        T rootX = find(x);
        T rootY = find(y);

        if (!rootX.equals(rootY)) {
            int rankX = rank.get(rootX);
            int rankY = rank.get(rootY);

            if (rankX > rankY) {
                parent.put(rootY, rootX);
            } else if (rankX < rankY) {
                parent.put(rootX, rootY);
            } else {
                parent.put(rootY, rootX);
                rank.put(rootX, rankX + 1);
            }
        }
    }
}

public class kruskalAlgo {
    public static <Vertex, Label extends Comparable<Label>> List<Edge<Vertex, Label>> kruskal(weightedUndirectedAdjMap<Vertex, Label> graph) {
        List<Edge<Vertex, Label>> edges = new ArrayList<>();
        List<Edge<Vertex, Label>> mst = new ArrayList<>();
        DisjointSet<Vertex> disjointSet = new DisjointSet<>();

        // Extract all edges from the graph
        for (Vertex u : graph.graph.keySet()) {
            for (Map.Entry<Vertex, Label> entry : graph.adj(u).entrySet()) {
                Vertex v = entry.getKey();
                Label weight = entry.getValue();
                if (u.hashCode() < v.hashCode()) { // Avoid duplicating undirected edges
                    edges.add(new Edge<>(u, v, weight));
                }
            }
        }

        Collections.sort(edges); // sort the edges in ascending order

        for (Vertex vertex : graph.graph.keySet()) {
            disjointSet.makeSet(vertex);
        }

        for (Edge<Vertex, Label> edge : edges) {
            Vertex u = edge.u;
            Vertex v = edge.v;

            if (!disjointSet.isConnected(u,v)) {
                mst.add(edge);
                disjointSet.link(u, v);
            }
        }

        return mst;
    }

    public static void main(String[] args) {
        weightedUndirectedAdjMap<String, Integer> graph = new weightedUndirectedAdjMap<>();

        // Add vertices and edges
        graph.addEdge("A", "B", 25);
        graph.addEdge("A", "E", 7);
        graph.addEdge("A", "D", 5);
        graph.addEdge("B", "C", 9);
        graph.addEdge("C", "D", 12);
        graph.addEdge("D", "E", 11);

        List<Edge<String, Integer>> mst = kruskal(graph);

        System.out.println("edge \t weight");
        for (Edge<String, Integer> edge : mst) {
            System.out.println(edge);
        }

    }

}
