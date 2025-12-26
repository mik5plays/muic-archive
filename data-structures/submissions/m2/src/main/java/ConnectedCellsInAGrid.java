import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class ConnectedCellsInAGrid {
    static int HEIGHT = 3;
    static int WIDTH = 3;

    static int dfs(List<List<Integer>> matrix, int startRow, int startColumn) {
        Stack<List<Integer>> stack = new Stack<>();

        int clusterSize = 0;
        // push the starting element to the stack
        stack.push(Arrays.asList(new Integer[]{startRow, startColumn}));
        matrix.get(startColumn).set(startRow, 0); // mark start as visited.
        while (!stack.isEmpty()) {
            List<Integer> cell = stack.pop();
            clusterSize++;

            int[] nbrsX = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] nbrsY = {-1, 0, 1, -1, 1, -1, 0, 1};

            for (int i = 0; i < 8; i++) {
                List<Integer> neighbour = Arrays.asList(new Integer[]{cell.get(0) + nbrsX[i], cell.get(1) + nbrsY[i]});
                if (neighbour.get(0) >= 0 && neighbour.get(0) < WIDTH
                        && neighbour.get(1) >= 0 && neighbour.get(1) < HEIGHT
                        && matrix.get(neighbour.get(1)).get(neighbour.get(0)) == 1) { // valid neighbour
                    stack.push(neighbour);
                    matrix.get(neighbour.get(1)).set(neighbour.get(0), 0);
                }
            }
        }

        return clusterSize;

    }
    public static int connectedCell(List<List<Integer>> matrix) {
        HEIGHT = matrix.size();
        WIDTH = matrix.get(0).size();

        int maxCluster = -1;
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (matrix.get(y).get(x) == 1) {
                    int clusterSize = dfs(matrix, x, y);
                    if (clusterSize > maxCluster) { maxCluster = clusterSize; }
                }
            }
        }
        return maxCluster;
    }

    public static void main(String[] args) {
        List<List<Integer>> foo = new ArrayList<>();
        foo.add(Arrays.asList(new Integer[]{1, 1, 0, 0}));
        foo.add(Arrays.asList(new Integer[]{0, 1, 1, 0}));
        foo.add(Arrays.asList(new Integer[]{0, 0, 1, 0}));
        foo.add(Arrays.asList(new Integer[]{1, 0, 0, 0}));
        System.out.println(connectedCell(foo));

    }
}
