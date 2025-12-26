import java.util.Arrays;

public class AllCaps {
    public static int[] allCapLocations(String st) {
        int[] result = new int[0];
        int index = 0;
        for (int i = 0; i < st.length(); i++) {
            char curr = st.charAt(i);
            if ((int) curr >= (int) 'A' && (int) curr <= (int) 'Z') {
                int[] newArray = new int[index + 1];
                System.arraycopy(result, 0, newArray, 0, index);
                newArray[index] = i;
                index++;
                result = newArray;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] foo = {3,5};
        System.out.println(Arrays.toString(allCapLocations("@82968*%(*@^ttt)")));

    }
}
