import java.sql.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class mergeSortTest {
    static class SC implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            if ( (int) o1.charAt(0) > (int) o2.charAt(0) )
                return 1;
            else if ( (int) o1.charAt(0) < (int) o2.charAt(0) )
                return -1;
            else
                return 0;
        }
    }

    static <T> void qsort(List<T> a, Comparator<T> cc) {
        if (a.size() <= 1) { return; }
        List<T> lt = new ArrayList<>();
        List<T> eq = new ArrayList<>();
        List<T> gt = new ArrayList<>();

        Random random = new Random();
        T p = a.get(random.nextInt(a.size()));
        split(a, p, lt, eq ,gt, cc);
        qsort(lt, cc);
        qsort(gt, cc);

        a.clear();
        a.addAll(lt); a.addAll(eq); a.addAll(gt);
    }

    static <T> void split(List<T> a, T p, List<T> lt, List<T> eq, List<T> gt, Comparator<T> cc) {
        for (T item : a)
            if (cc.compare(item, p) < 0) {
                lt.add(item);
            } else if (cc.compare(item, p) == 0) {
                eq.add(item);
            } else {
                gt.add(item);
            }
    }

    public static void main(String[] args) {
        List<String> bar = new ArrayList<>();
        bar.addAll(List.of(new String[]{"abcacus", "joe", "cool", "meow", "lame"}));
        qsort(bar, new SC());
        System.out.println(bar);
    }

}
