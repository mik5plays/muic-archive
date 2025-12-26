import java.util.*;

public class BFSShortestReach {
    public static List<Integer> bfs(int n, int m, List<List<Integer>> edges, int s) {
        // Write your code here
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (List<Integer> edge: edges) {
            graph.computeIfAbsent(edge.get(0), k -> new ArrayList<>()).add(edge.get(1));
            graph.computeIfAbsent(edge.get(1), k -> new ArrayList<>()).add(edge.get(0)); // dont forget to connect nodes to each other for reverse traversal!
        }
        for (int i = 1; i <= n; i++) {
            graph.putIfAbsent(i, new ArrayList<>());
        }

        System.out.println(graph);

        Map<Integer, Integer> distances = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();

        distances.put(s, 0);
        queue.add(s);

        while (!queue.isEmpty()) {
            Integer current = queue.poll();
            int currentDistance = distances.get(current);

            // Explore neighbors
            for (Integer neighbor : graph.get(current)) {
                if (!distances.containsKey(neighbor)) {
                    // Add neighbor to queue if not visited
                    distances.put(neighbor, currentDistance + 6); // distance depends on weight too
                    queue.add(neighbor);
                }
            }
        }

        List<Integer> values = new ArrayList<>();
        for (int x = 1; x <= n; x++) {
            if (distances.get(x) == null)
                values.add(-1);
            else if (x != s)
                values.add(distances.get(x));
        }

        return values;
    }

    public static void main(String[] args) {
        List<List<Integer>> foo = new ArrayList<>();
        foo.add(Arrays.asList(new Integer[]{1,2}));
        foo.add(Arrays.asList(new Integer[]{1,3}));
        foo.add(Arrays.asList(new Integer[]{3,4}));
        System.out.println(bfs(5, 3, foo, 1));
    }
}
