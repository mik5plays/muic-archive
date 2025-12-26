import java.util.*;

public class JourneyMoon {

    static List<Integer> dfs(List<List<Integer>> astronaut, Integer start) {
        List<Integer> reachable = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();

        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (List<Integer> pair : astronaut) {
            graph.computeIfAbsent(pair.get(0), k -> new HashSet<>()).add(pair.get(1));
            graph.computeIfAbsent(pair.get(1), k -> new HashSet<>()).add(pair.get(0));
        }

        stack.push(start);

        while (!stack.isEmpty()) {
            Integer current = stack.pop();

            if (!visited.contains(current)) { // if not visited:
                reachable.add(current);
                visited.add(current);

                for (Integer adjacent: graph.getOrDefault(current, Collections.emptySet())) {
                    if (!visited.contains(adjacent)) {
                        stack.push(adjacent);
                    }
                }
            }
        }

        return reachable;
    }

    public static int journeyToMoon(int n, List<List<Integer>> astronaut) {
        // Write your code here
        List<List<Integer>> countries = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();

        for (List<Integer> pair : astronaut) {
            if (!visited.contains(pair.get(0))) {
                List<Integer> country = dfs(astronaut, pair.get(0));
                countries.add(country);
                visited.addAll(country);
            }
            if (!visited.contains(pair.get(1))) {
                List<Integer> country = dfs(astronaut, pair.get(1));
                countries.add(country);
                visited.addAll(country);
            }
        }

        for (int astronautId = 0; astronautId < n; astronautId++) {
            if (!visited.contains(astronautId)) {
                countries.add(Arrays.asList(astronautId));
            }
        }

        List<List<Integer>> pairs = new ArrayList<>();

        long totalPairs = 0;
        int totalAstronauts = 0;

        // checking how many pairs without making a list of pairs since that is too time consuming

        for (List<Integer> country : countries) {
            totalPairs += (long) totalAstronauts * country.size();
            totalAstronauts += country.size();
        }

        return (int) totalPairs;
    }

    public static void main(String[] args) {
        List<List<Integer>> foo = new ArrayList<>();
        foo.add(Arrays.asList(new Integer[]{0,1}));
        foo.add(Arrays.asList(new Integer[]{2,3}));
        foo.add(Arrays.asList(new Integer[]{0,4}));

        System.out.println(journeyToMoon(5, foo));
    }
}
