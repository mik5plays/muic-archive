import java.util.function.BiFunction;

class Cat implements HasIsLarger<Cat>{
    private String name;
    private int weight;
    private int age;
    public Cat(String name, int weight, int age) {
        this.name = name;
        this.weight = weight;
        this.age = age;
    }
    public String getName() { return name; }
    public int getWeight() { return weight; }
    public int getAge() { return age; }

    @Override
    public boolean isLarger(Cat other) {
        return this.weight > other.weight;
    }
}

interface HasIsLarger<T> {
    boolean isLarger(T other);
}

public class MaxDemo {
    // Example I
    static <T> T maxValue(T[] items, BiFunction<T, T, Boolean> isLarger) {
        if (items.length == 0)
            return null;
        int maxDex = 0;
        for (int i = 1; i < items.length; i++) {
            if (isLarger.apply(items[i], items[maxDex])) {
                maxDex = i;
            }
        }
        return (T) items[maxDex];
    }
    // Example II
    static <T extends HasIsLarger<T>> T maxValue(T[] items) {
        if (items.length == 0)
            return null;
        int maxDex = 0;
        for (int i = 1; i < items.length; i++) {
            if (items[i].isLarger(items[maxDex])) {
                maxDex = i;
            }
        }
        return (T) items[maxDex];
    }

    public static void main(String[] args) {
        Cat[] cats = new Cat[]{
                new Cat("1", 10, 20),
                new Cat("2", 1, 5),
                new Cat("3", 5, 7)
        };
        System.out.println(maxValue(cats, (Cat x, Cat y) -> x.getAge() > y.getAge()));
        // Should print the first cat (just the class, not the name)
        // Unless I do maxValue(cats, (Cat x, Cat y) -> x.getAge() > y.getAge()).getName() instead

        // Since we're dealing with generics, going to use Integer instead of int
        Integer[] intArr = {3,1,2,4};
        System.out.println(maxValue(intArr, (Integer x, Integer y) -> x > y)); // Should print 4
    }
}
