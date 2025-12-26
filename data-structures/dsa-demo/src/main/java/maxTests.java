public class maxTests {
    public static int maxFor(int[] numbers) {
        int maxSoFar = numbers[0];
        for (int index=1;index<numbers.length;index++) {
            if (numbers[index] > maxSoFar) {
                maxSoFar = numbers[index];
            }
        }
        return maxSoFar;
    }
    // A function that finds the maximum number of an array of integers.
    // Starts with the first number of the list, and compares with subsequent values
    // until the list ends.
    public static int maxWhile(int[] numbers) {
        int maxSoFar = numbers[0];
        int index = 0;
        while (index < numbers.length) {
            if (numbers[index] > maxSoFar) {
                maxSoFar = numbers[index];
            }
            index++;
        }
        return maxSoFar;
    }
    public static void main(String[] args) {

    }
}
