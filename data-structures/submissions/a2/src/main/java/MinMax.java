public class MinMax {
    // Helper Functions
    public static boolean bigger(int a, int b) {
        return (a > b);
    }
    public static boolean smaller(int a, int b) {
        return (a < b);
    }
    public static double minMaxAverage(int[] numbers) {
        // Method I will use deviates from the in-class pairing method
        // For every array with n elements, there will be n/2 pairs (if n is even)
        // If odd, there will be (n/2)-1 pairs, with 1 number without a pair
        // Comparing each pair with each other, starting from the second pair (technically first pair if n is odd)
        // == The bigger number gets compared to the global max. if it's bigger, then it replaces global max
        // == The smaller number gets compared to the global min. if it's smaller, then it replaces global min
        // Doing this until we reach end of array

        // In terms of how many comparisons there should be,
        // let comparisons be c. let array be a. First comparison would be after determining whether array has even or odd elements
        // If even, compare a[0] with a[1], that is our first comparison [c++]. We now have our global min and max
        // If odd, do not compare, and we set both global min and max to be a[0] for our lone number w/o a pair

        // Now to traverse the list, we will go through each pair.
        // This means that in odd, we will go through (n-1/2) pairs.
        // If even, we go through (n/2) - 1 pairs.
        // == Each traversal per pair, we compare the two numbers [c++]
        // == We compare the bigger number with global max [c++]
        // == We compare the smaller number with global min [c++]
        // Therefore, per every pair we would do 3 comparisons.

        // After everything finishes, we would be left with a global min and max.
        // We sum + divide by 2 and return the minMaxAverage.
        // In the end, an array with odd elements would do 3(n-1/2) comparisons, or 3n/2 - 3/2 comparisons
        // An array with even elements would do 1 + 3[(n/2)-1] comparisons, or 3n/2 - 3 + 1 = 3n/2 - 2 comparisons
        // Both of these are less than 3n/2, so in theory this should answer the question.

        int curMin, curMax, myMin, myMax, x;
        int comparisons = 0;
        if (numbers.length % 2 == 0) { // Checking if we can have all pairs.
            x = 2; //Traversing list starts at the second pair, since no. of elements is even
            comparisons++;
            if (numbers[0] > numbers[1]) {
                myMin = numbers[1];
                myMax = numbers[0];
            } else {
                myMin = numbers[0];
                myMax = numbers[1];
            }
        } else {
            x = 1; // Otherwise, the first number is left w/o a pair, so "second" pair will start at index 1.
            myMin = numbers[0];
            myMax = numbers[0];
        }
        for (int i = x; i < numbers.length; i+=2) {
            comparisons++;
            if (bigger(numbers[i], numbers[i+1])) {
                comparisons += 2;
                if (bigger(numbers[i], myMax)) {
                    myMax = numbers[i];
                }
                if (smaller(numbers[i+1], myMin)) {
                    myMin = numbers[i+1];
                }
            } else {
                comparisons += 2;
                if (smaller(numbers[i], myMin)) {
                    myMin = numbers[i];
                }
                if (bigger(numbers[i+1], myMax)) {
                    myMax = numbers[i+1];
                }
            }
        }
        System.out.println("Biggest number is: " + myMax + ", Smallest number is: " + myMin + ", Total comparisons = " + comparisons);
        return (myMin + myMax)/2.0;
    }

    public static void main(String[] args) {
        int[] numbers = {3, 4, 1, 5, 9, 2, 6, 5, 12};
        System.out.println(minMaxAverage(numbers));
    }

}
