import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class SuperPower {
    public static boolean containsAllNumbers(BigInteger n) {
        HashMap<Integer, Boolean> numbers = new HashMap<Integer, Boolean>();
        numbers.put(0, false);
        numbers.put(1, false);
        numbers.put(2, false);
        numbers.put(3, false);
        numbers.put(4, false);
        numbers.put(5, false);
        numbers.put(6, false);
        numbers.put(7, false);
        numbers.put(8, false);
        numbers.put(9, false);

        String nString = n.toString();
        for (int i = 0; i < nString.length(); i++) {
            int currChar = Character.getNumericValue(nString.charAt(i));
            if (!numbers.get(currChar)) {
                numbers.put(currChar, true);
            }
        }
        Set<Integer> keys = numbers.keySet();
        for (Integer key: keys) {
            if (!numbers.get(key)) { return false; }
        }
        return true;
    }
    public static int numSPUpTo(int n) {
        int numsUpTo = 0;
        if (n < 56) { return numsUpTo; } // Save time since we know first superpower is 56.
        for (int i = 56; i <= n; i++) {
            BigInteger check = BigInteger.valueOf(i);
            check = check.pow(7);
            if (containsAllNumbers(check)) {
                numsUpTo++;
            }
        }
        return numsUpTo;
    }

    public static int kthSP(int k) {
        int kNum = 56;
        int kTh = 0;
        while (true) {
            BigInteger check = BigInteger.valueOf(kNum);
            check = check.pow(7);
            if (containsAllNumbers(check)) { kTh++; }
            if (kTh == k) { return kNum; }
            kNum++;
        }
    }

    public static void main(String[] args) {
        System.out.println(0==numSPUpTo(55));
        System.out.println(1==numSPUpTo(56));
        System.out.println(3==numSPUpTo(126));
        System.out.println(5==numSPUpTo(149));
        System.out.println(100==numSPUpTo(790));
        System.out.println(56==kthSP(1));
        System.out.println(177==kthSP(7));
        System.out.println(789==kthSP(100));

//        System.out.println(numSPUpTo(100000));
//        System.out.println(kthSP(100000));
    } 
}
