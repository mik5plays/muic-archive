import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue; // will use Java's one

public class MultiwayMerge {
    public static LinkedList<Integer> mergeAll(LinkedList<Integer>[] lists) {
        PriorityQueue<Integer> pQueue = new PriorityQueue<Integer>();
        LinkedList<Integer> ans = new LinkedList<Integer>();

        for (LinkedList<Integer> list: lists) {
            pQueue.add(list.getFirst());
        }

        while (!pQueue.isEmpty()) {
            Integer removed = pQueue.poll();
            ans.add(removed);
            for (LinkedList<Integer> list: lists) {
                if (!list.isEmpty())
                    if (Objects.equals(list.getFirst(), removed)) {
                        list.removeFirst();
                        if (!list.isEmpty()) {
                            pQueue.add(list.getFirst());
                            break;
                        }
                    }
            }
        }

        return ans;

    }

    public static void main(String[] args) {
        LinkedList<Integer> foo1 = new LinkedList<Integer>();
        foo1.add(4);
        foo1.add(7);
        foo1.add(9);
        foo1.add(13);
        LinkedList<Integer> foo2 = new LinkedList<Integer>();
        foo2.add(1);
        foo2.add(6);
        foo2.add(19);
        LinkedList<Integer> foo3 = new LinkedList<Integer>();
        foo3.add(8);
        foo3.add(12);
        foo3.add(17);
        LinkedList<Integer> foo4 = new LinkedList<Integer>();
        foo4.add(10);
        foo4.add(15);

        LinkedList<Integer>[] bar = (LinkedList<Integer>[]) new LinkedList[4];
        bar[0] = foo1; bar[1] = foo2; bar[2] = foo3; bar[3] = foo4;

        System.out.println(mergeAll(bar)); // it works!

    }

}
