import java.util.ArrayList;
import java.util.List;

interface Series {
    int next();
    void reset();
}

class OddNumbers implements Series {
    int currOdd = 1;

    public int next() {
        return currOdd += 2;
    }
    public void reset() {

    }
}
class ConstSeven implements Series {
    public int next() {
        return 7;
    }
    public void reset() {
        ; // Do nothing
    }
    public int magicFoo() {
        return 42;
    }
}

class Animal { // Say we have this new parent class
    void walk() {
        System.out.println("Animal: walking");
    }
    void eat() {
        System.out.println("Animal: eating");
    }
}
class Pet extends Animal {
    void playWith(String name) {
        System.out.println("Pet: playing with " + name);
    }
}
class Meow extends Pet {
    void meow() {
        System.out.println("Cat: Meow");
    }
}

public class Lesson6 {
    static int sum(List<Integer> x) {
        int total = 0;
        for (int y: x) {
            total += y;
        }
        return total;
    }
    // This method sum() works for all children of List, such as LinkedList and ArrayList
    // This is a good example of polymorphism.

    public static void main(String[] args) {
        OddNumbers odd = new OddNumbers();
        ConstSeven const7 = new ConstSeven();
        // OddNumbers x = mystery; >> This will NOT work because every OddNumbers is a Series but not every Series is OddNumbers
        Series[] sArray = {odd, const7}; // Works because the two other classes are child classes of Series.
        // System.out.println(sArray[1].magicFoo());
        // This does NOT work since the parent class does not inherit from the children.
        Pet pet = new Pet();
        Animal a = pet; // >> Works, but redundant (can't use child methods)
        // a.playWith() >> Does NOT work since it cannot use child methods.
        Meow dog = new Meow();
        // This inherits from Pet, which inherits from Animal
        // This means the inheritance diagram looks like the following:
        // Cat --inherits--> Pet --inherits--> Animal
        // >> This means that Cat --inherits--> Animal too, just not directly.
    }
}
