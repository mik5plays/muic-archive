public class UsefulNumbers {
    public static void main(String[] args) {
        long secondsInAMinute = 60;
        long secondsInAnHour = secondsInAMinute * 60;
        long secondsInADay = secondsInAnHour * 24;
        long secondsInAYear = secondsInADay * 365;
        long secondsInACentury = secondsInAYear * 100;
        System.out.printf("One century contains %d seconds.\n", secondsInACentury);
    }
}
/*
Program that prints out how many seconds in a century, using variables that are based off other variables.
Note that since the result is greater than 2^31-1, we use long instead of int
to display the answer.
 */

