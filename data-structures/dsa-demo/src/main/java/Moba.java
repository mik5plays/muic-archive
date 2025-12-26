interface Magic {
    void magic();
}
interface Attack {
    void attack();
}
class Carry implements Attack {
    String name;
    private int magicNum = 42;
    public Carry(String name) {
        this.name = name;
        System.out.println(name + ":ready");
    }
    public Carry() {
        this("Unknown");
        System.out.println("Using default");
    }
    public void attack() {
        System.out.println(name + ":attack!");
    }
    public void heal(int n) {
        System.out.println(name+":is healing!");
        if (n > 0)
            throw new RuntimeException("Can’t");
        System.out.println("Done!");
    }
}
class Valhein extends Carry
        implements Attack, Magic {
    public Valhein(String name) {
        super(name);
        System.out.println(name +
                ":choose a Valhein");
    }
    public Valhein() {
        this("Unnamed Valhein");
    }
    @Override
    public void magic() {
        System.out.println(name +
                ":use magic!");
    }
}

public class Moba {

    public static void stageAttack(Attack o) { o.attack(); }

    public static void stageMagic(Magic o) { o.magic(); }

    public static void main(String[] args) {
        Valhein v = new Valhein("Peter"); // #1
        Carry c = new Carry(); // #2
        stageAttack(v); // #3
        stageAttack(c); // #4
        v.magic(); // #5
//        stageMagic(c); // #6
//        stageMagic(v); // #7
//        System.out.println(v.name); // #8
//        System.out.println(c.magicNum); // #9

// #10 begins --
//        try {
//            c.heal(3);
//        } catch (RuntimeException e) {
//            System.out.println("Fail to heal");
//        } finally {
//            System.out.println("Skill is Cooling down");
//        }
    }
}
