import java.util.Comparator;

class IntegerComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer o1, Integer o2) {
        if (o1 < o2)
            return -1;
        else if (o1 > o2)
            return 1;
        else
            return 0;
    }
}

public class Lecture17 {
    public static void main(String[] args) {
        Lecture15.StringComparator bar = new Lecture15.StringComparator();
        MyPriorityQueue<String> baz = new MyPriorityQueue<>(bar);
        MyPriorityQueue<Integer> foo = new MyPriorityQueue<>(new IntegerComparator());
        foo.add(5);
        foo.add(7);
        foo.add(3);
        foo.add(21);
        foo.remove();
        foo.remove();
        foo.add(10);
        System.out.println(foo.find());

        baz.add("abacus");
        baz.add("zaza");
        baz.add("biff");
        baz.add("whiff");

        baz.remove();
        baz.remove();
        baz.remove();
        System.out.println(baz.find());


    }
}
