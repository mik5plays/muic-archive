import java.util.*;

public class snakesAndLadders {
    public static int quickestWayUp(List<List<Integer>> ladders, List<List<Integer>> snakes) {
        Map<Integer, Integer> moves = new HashMap<>();
        for (List<Integer> ladder: ladders) {
            moves.put(ladder.get(0), ladder.get(1));
        }
        for (List<Integer> snake: snakes) {
            moves.put(snake.get(0), snake.get(1));
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        Map<Integer, Integer> visited = new HashMap<>();
        visited.put(1, 0); // we start at 1, with 0 rolls

        // bfs
        while (!queue.isEmpty()) {
            int current = queue.poll();
            int rolls = visited.get(current);

            for (int roll = 1; roll < 7; roll++) {
                int next = current + roll;
                if (next > 100) { continue; } // ignore rolls past 100

                if (moves.containsKey(next)) {
                    next = moves.get(next);
                }

                if (next == 100) {
                    return rolls + 1;
                }

                if (!visited.containsKey(next)) { // typical BFS part (if not visited)
                    visited.put(next, rolls + 1); // mark as visited
                    queue.add(next); // put in queue to process
                }
            }
        }
        return -1; // impossible
    }
}
