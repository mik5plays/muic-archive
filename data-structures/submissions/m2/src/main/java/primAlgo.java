import java.util.*;

// very similar to textbook implementation of Prim's algorithm

class Edge<Vertex, Label extends Comparable<Label>> implements Comparable<Edge<Vertex, Label>> {
    Vertex u, v;
    Label weight;

    Edge(Vertex u, Vertex v, Label weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge<Vertex, Label> other) {
        return this.weight.compareTo(other.weight); // Compare edges by weight
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        return text.append(u).append(v).append("\t\t\t").append(weight).toString();
    }
}

public class primAlgo {
    public static <Vertex, Label extends Comparable<Label>>
    List<Edge<Vertex, Label>> primMST(weightedUndirectedAdjMap<Vertex, Label> G, Vertex start) {
        PriorityQueue<Edge<Vertex, Label>> queue = new PriorityQueue<>();
        List<Edge<Vertex, Label>> mst = new ArrayList<>();
        Set<Vertex> treeVertices = new HashSet<>();
        treeVertices.add(start);

        // Add all edges of the starting vertex to the queue
        expandNeighbors(start, G, queue, treeVertices);

        while (!queue.isEmpty() && treeVertices.size() < G.numVertices()) {
            Edge<Vertex, Label> e = queue.poll();
            if (treeVertices.contains(e.u) && treeVertices.contains(e.v))
                continue;

            mst.add(e);

            // Add the new vertex to the tree and expand its neighbors
            if (!treeVertices.contains(e.u)) {
                expandNeighbors(e.u, G, queue, treeVertices);
            } else if (!treeVertices.contains(e.v)) {
                expandNeighbors(e.v, G, queue, treeVertices);
            }
        }

        return mst;
    }

    private static <Vertex, Label extends Comparable<Label>>
    void expandNeighbors(Vertex v, weightedUndirectedAdjMap<Vertex, Label> G,
                         PriorityQueue<Edge<Vertex, Label>> queue, Set<Vertex> treeVertices) {
        treeVertices.add(v);

        // my adjacency map doesn't have getEdge() like the book so this is the alternative

        Map<Vertex, Label> neighbors = G.adj(v);
        if (neighbors != null) {
            for (Map.Entry<Vertex, Label> entry : neighbors.entrySet()) {
                Vertex neighbour = entry.getKey();
                Label weight = entry.getValue();
                if (!treeVertices.contains(neighbour)) {
                    queue.add(new Edge<>(v, neighbour, weight));
                }
            }
        }
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

        List<Edge<String, Integer>> mst = primMST(graph, "C");

        System.out.println("edge \t weight");
        for (Edge<String, Integer> edge : mst) {
            System.out.println(edge);
        }
    }
}
