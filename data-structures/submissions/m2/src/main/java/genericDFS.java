import java.util.*;

public class genericDFS {

    public static <T> List<T> dfs(Map<T, List<T>> graph, T startNode) {
        List<T> reachable = new ArrayList<>();
        Set<T> visited = new HashSet<>();
        Stack<T> stack = new Stack<>();

        stack.push(startNode);

        while (!stack.isEmpty()) {
            T current = stack.pop();

            // If not visited, process the node
            if (!visited.contains(current)) {
                visited.add(current);
                reachable.add(current);

                // Add all neighbors to the stack
                for (T neighbor : graph.getOrDefault(current, Collections.emptyList())) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }

        return reachable;
    }
}