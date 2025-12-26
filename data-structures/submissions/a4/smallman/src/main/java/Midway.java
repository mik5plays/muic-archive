public class Midway {
    /*
     * (Hint: Solve the following inputs by hand: {2, 2, 0} and {1, 2, 0}.
     * How many moves do we make before we move the largest disk?)
     *
     * {2, 2, 0} -> 0 moves before moving (can be moved immediately)
     * {1, 2, 0} -> 1 move before moving (moved once before can be moved)
     *
     * For a 3-disc Hanoi problem, we observe that
     * Disc 0 moves 4 times
     * Disc 1 moves 2 times
     * Disc 2 moves 1 time
     *
     * Moving n discs will take 2^n - 1 turns according to our proof.
     * To move a disc to the target peg, we will need to move all smaller discs to the auxiliary (which takes 2^i - 1 turns)
     * Then we move disc i onto target peg (+1)
     * Therefore the total amount of steps needed to move a disc i to the target peg is 2^i
     *
     * The for loop calculates what discs need to be moved (so those that are not on the target peg)
     * Adds all the steps required together and this is the total remaining steps.
     *
     */
    public static long stepsRemaining(int[] diskPos) {
        long stepsRemaining = 0;
        if (diskPos.length == 1) { return stepsRemaining; }

        for (int i = 0; i < diskPos.length; i++) {
            if (diskPos[i] != 1) // Assuming target peg is Peg 1 (the middle)
                stepsRemaining += (long) (Math.pow(2,i));
        }
        return stepsRemaining;
    }

    public static int stepsBasedOnDiscs(int n) { return (int) (Math.pow(2, n) - 1); }

    public static void main(String[] args) {
        System.out.println(stepsRemaining(new int[]{0})); // expected = 0
        System.out.println(stepsRemaining(new int[]{1,0,0,0,0})); // expected = (2^5 - 1) - 1 (first move) = 30
        System.out.println(stepsRemaining(new int[]{2,2,1})); // expected = 3
        System.out.println(stepsRemaining(new int[]{2, 2, 1, 1, 2, 2, 1})); // expected = 51
    }
}
