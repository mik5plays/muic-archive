/*
Given an unlimited supply of 5-cent stamps, 11-cent, and 12-cent stamps, we can make any amount that is at least 20 cents.

Predicate is that P(n) = 5a + 11b + 12c

Base case is P(20) = 5(4)

Inductive step: Prove that p(n+5) = 5a+11b+12c
Know that p(n+5) = p(n) + 5
= 5(a+1) + 11b + 12c
= Therefore, we know that p(n+5) is valid

Find other base cases
P(21) = 11 + 5(2)
P(22) = 12 + 5(2)
P(23) = 12 + 11
P(24) = 12 + 12

Now that we've found the gaps in between our base cases, we can conclude
that
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeCoins {
    // Implementation based on the base-case as well as another case if
    // n is divisible by one of the three coins.
    public static List<Integer> change(int n){
        List<Integer> result = new ArrayList<Integer>();

        if (n < 20) {

            // Considering that per our proof, anything above 20 can be made using
            // deviations of the base case(s). To save time and to make it obvious,
            // there are few enough cases below 20 so that we can just return based
            // on their value.
            // Otherwise, returns an empty list if not possible.

            switch(n) {
                case 5:
                    result.add(5);
                case 10:
                    result.add(5);
                    result.add(5);
                case 11:
                    result.add(11);
                case 12:
                    result.add(12);
                case 15:
                    result.add(5);
                    result.add(5);
                    result.add(5);
                case 16:
                    result.add(5);
                    result.add(11);
                case 17:
                    result.add(5);
                    result.add(12);
            }
            return result;

        }



        List<Integer> baseCase20 = new ArrayList<Integer>(Arrays.asList(5,5,5,5));
        List<Integer> baseCase21 = new ArrayList<Integer>(Arrays.asList(11,5,5));
        List<Integer> baseCase22 = new ArrayList<Integer>(Arrays.asList(12,5,5));
        List<Integer> baseCase23 = new ArrayList<Integer>(Arrays.asList(12,11));
        List<Integer> baseCase24 = new ArrayList<Integer>(Arrays.asList(12,12));

        List<List<Integer>> baseCases = new ArrayList<List<Integer>>();
        baseCases.add(baseCase20);
        baseCases.add(baseCase21);
        baseCases.add(baseCase22);
        baseCases.add(baseCase23);
        baseCases.add(baseCase24);

        for (int i = 20; i < 25; i++) {
            if ( (n - i) % 5 == 0) {
                result.addAll(baseCases.get(i-20));
                for (int k = 0; k < (n - i)/5; k++) {
                    result.add(5);
                }
                return result;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(change(17));
    }
}
