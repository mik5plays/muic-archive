import java.util.*;
/*
Initialization: Enqueue the given source vertex into a queue and mark it as visited.

Exploration: While the queue is not empty:
    Dequeue a node from the queue and visit it (e.g., print its value).
For each unvisited neighbor of the dequeued node:
    Enqueue the neighbor into the queue.
    Mark the neighbor as visited.

Termination: Repeat step 2 until the queue is empty.
 */
public class genericBFS {

    public static <T> Map<T, Integer> bfs(Map<T, List<T>> graph, T startNode) {
        Map<T, Integer> distances = new HashMap<>();
        Queue<T> queue = new LinkedList<>();

        distances.put(startNode, 0);
        queue.add(startNode);

        while (!queue.isEmpty()) {
            T current = queue.poll();
            int currentDistance = distances.get(current);

            // Explore neighbors
            for (T neighbor : graph.get(current)) { // or graph.getOrDefault(current, new ArrayList<>()) (check for no neighbours)
                if (!distances.containsKey(neighbor)) {
                    // Add neighbor to queue if not visited
                    distances.put(neighbor, currentDistance + 1); // distance depends on weight too
                    queue.add(neighbor);
                }
            }
        }

        return distances;
    }
}