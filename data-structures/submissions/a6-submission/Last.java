public class Last {
    public static Integer binarySearchLast(int[] a, int k) {
        if (a.length < 1)
            return null;

        int l = 0;
        int h = a.length - 1;
        Integer ans = null;

        while (l <= h) {
            int mid = (h + l) / 2;
            /*
            Change it so we focus on the right side to see if there's any more on the right side
            Doesn't iterate l by +1 until the last occurrence since that takes more time
            We do normal binary search operations
             */
            if (a[mid] == k) { ans = mid; l = mid + 1; }
            else if (a[mid] > k) { h = mid - 1; }
            else if (a[mid] < k) { l = mid + 1; }
        }

        return ans;
        // Runtime should still be O(logn)
    }

    public static void main(String[] args) {
        System.out.println(binarySearchLast(new int[]{1, 2, 2, 2, 4, 5},2)); // expected: 3
        System.out.println(binarySearchLast(new int[]{1, 2, 2, 2, 4, 5},0)); // expected: null
        System.out.println(binarySearchLast(new int[]{1, 2, 2, 2, 4, 5},5)); // expected: 5

        // Test with debugging to make sure it doesn't iterate by +1 like linear search
        System.out.println(binarySearchLast(new int[]{1,2,2,2,2,2,2,2,2,2,2,2,2,7,9},2)); // expected: 12
    }
}
