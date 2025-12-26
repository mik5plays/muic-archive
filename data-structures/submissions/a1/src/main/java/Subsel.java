import java.util.Arrays;

public class Subsel {
    public static int[] takeEvery(int[] a, int stride, int beginWith) {
        int[] result = new int[1];
        int index = 0;
        while (true) {
            if (beginWith + (index*stride) < a.length && beginWith + (index*stride) >= 0) {
                result[index] = a[beginWith + (index*stride)];
                index++;
                result = Arrays.copyOf(result, (result.length)+1);
            } else {
                return Arrays.copyOf(result, (result.length)-1);
            }
        }
    }
    public static int[] takeEvery(int[] a, int stride) {
        return takeEvery(a, stride, 0);
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(takeEvery(new int[]{1, 2, 3, 4}, 2)));
        System.out.println(Arrays.toString(takeEvery(new int[]{2, 7, 1, 8, 4, 5}, 3, 2)));
        System.out.println(Arrays.toString(takeEvery(new int[]{3, 1, 4, 5, 9, 2, 6, 5}, -1,5)));

    }
}
