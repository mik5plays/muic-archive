import java.util.*;

public class hackerRank {
    public static int minimumLoss(List<Long> price) {
        // Write your code here
        Map<Long, Integer> positions = new HashMap<Long, Integer>();
        for (int i = 0; i < price.size(); i++) {
            positions.put(price.get(i), i);
        }
        price.sort(null); // default sort

        int minLoss = Integer.MAX_VALUE;
        for (int j = 1; j < price.size(); j++) {
            long val1 = price.get(j-1);
            long val2 = price.get(j);

            if (val2 - val1 < minLoss && positions.get(val2) < positions.get(val1)) { minLoss = (int) (val2 - val1); }
        }

        return minLoss;
    }

    public static int pairs(int k, List<Integer> arr) {
        arr.sort(null);
        int ans = 0;

        for (int num: arr) {
            if (Collections.binarySearch(arr, num - k) >= 0)
                ans++;
        }

        return ans;
    }

    static void splitInto(List<List<String>> a, int pivot, List<List<String>> lt, List<List<String>> eq, List<List<String>> gt) {
        for (List<String> t : a) {
            if (Integer.parseInt(t.get(0)) > pivot)
                gt.add(t);
            else if (Integer.parseInt(t.get(0)) < pivot)
                lt.add(t);
            else
                eq.add(t);
        }
    }

    static void quickSort(List<List<String>> a) {
        if (a.size() <= 1) { return; }

        List<List<String>> greaterThan = new ArrayList<>();
        List<List<String>> equalTo = new ArrayList<>();
        List<List<String>> lessThan = new ArrayList<>();

        Random random = new Random();
        int number = random.nextInt(a.size());
        int Pivot = Integer.parseInt(a.get(number).get(0));

        splitInto(a, Pivot, lessThan, equalTo, greaterThan);

        quickSort(lessThan);
        quickSort(greaterThan);


        int index = 0;
        for (List<String> t : lessThan) {
            a.set(index, t);
            index++;
        }
        for (List<String> t : equalTo) {
            a.set(index, t);
            index++;
        }
        for (List<String> t : greaterThan) {
            a.set(index, t);
            index++;
        }

    }



    public static void countSort(List<List<String>> arr) {
        for (int i = 0; i < arr.size() / 2; i++) {
            arr.get(i).set(1, "-"); // change first half to -
        }

        // insertion sort

//        for (int i = 1; i < arr.size(); i++) {
//            List<String> tempX = arr.get(i);
//            int x = Integer.parseInt(tempX.get(0));
//            int j = i;
//            while (j > 0 && x < Integer.parseInt(arr.get(j-1).get(0))) {
//                arr.set(j, arr.get(j-1));
//                j--;
//            }
//            arr.set(j, tempX);
//        }

        StringBuilder result = new StringBuilder();
        for (List<String> i: arr) {
            result.append(i.get(1)).append(" ");
        }

        System.out.println(result);

    }



    public static void main(String[] args) {
        List<Long> foo = new ArrayList<>();
        foo.add((long) 5);
        foo.add((long) 10);
        foo.add((long) 3);
//        System.out.println(minimumLoss(foo));

        List<List<String>> bar = new ArrayList<>();
        bar.add(new ArrayList<String>());
        bar.get(0).add("0");
        bar.get(0).add("a");
        bar.add(new ArrayList<String>());
        bar.get(1).add("1");
        bar.get(1).add("b");
        bar.add(new ArrayList<String>());
        bar.get(2).add("0");
        bar.get(2).add("c");
        bar.add(new ArrayList<String>());
        bar.get(3).add("1");
        bar.get(3).add("d");

        System.out.println(bar);
        countSort(bar);
    }
}
