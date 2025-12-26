import java.util.*;

public class PusheenOil {

    private int[] L;

    public PusheenOil(int[] pusheenOil) {
        L = pusheenOil;
    }

    public PusheenOil(List<Integer> chain, int n) {
        L = new int[n + 1];
        L[0] = chain.get(0);
        for (int i = 1; i < L.length; i++) {
            try {
                L[i] = chain.get(chain.indexOf(i) + 1);
            } catch (ArrayIndexOutOfBoundsException e) {
                L[i] = -1;
            }
        }
    }

    public void reverse() {
        // todo: write me
        Collections.reverse(List.of(L));
    }

    public void pop() {
        // todo: write me
        int[] temp = new int[L.length - 1];
        System.arraycopy(L,0,temp,0,L.length - 1);
        L = temp;
    }

    public List<Integer> asList() {
        // todo: write me
        List<Integer> formula = new ArrayList<>();
        formula.add(L[0]);

        return formula;
    }

    public int[] asMewList() { 
        return L;
    }

    public static void main(String[] args) {
        List<Integer> list = List.of(3, 1, 2, 4, 7, 6, 5);
        PusheenOil po = new PusheenOil(list, 7);
        System.out.println(Arrays.toString(po.asMewList()));


//        // Act 1: from formula
//        List<Integer> list = List.of(3, 1, 2, 4, 7, 6, 5);
//        PusheenOil po = new PusheenOil(list, 7);
//        System.out.println(po.asList()); // => 3, 1, 2, 4, 7, 6, 5
//        System.out.println(Arrays.toString(po.asMewList())); // => [3, 2, 4, 1, 7, -1, 5, 6]
//        po.reverse();
//        System.out.println(po.asList()); // => 5, 6, 7, 4, 2, 1, 3
//        System.out.println(Arrays.toString(po.asMewList())); // => 5, 3, 1, -1, 2, 6, 7, 4
//        po.reverse();
//        po.pop();
//        System.out.println(po.asList()); // => 3, 1, 2, 4, 7, 6
//        System.out.println(Arrays.toString(po.asMewList())); // => 3, 2, 4, 1, 7, -1, -1, 6
//
//        // Act 2: from mewlist
//        PusheenOil poToo = new PusheenOil(new int[]{4, -1, 3, -1, 2});
//        System.out.println(poToo.asList()); // => 4, 2, 3
//        poToo.reverse();
//        System.out.println(poToo.asList()); // => 3, 2, 4
//        poToo.pop();
//        System.out.println(poToo.asList()); // => 3, 2
//        poToo.pop();
//        System.out.println(poToo.asList()); // => 3
//        poToo.reverse();
//        System.out.println(Arrays.toString(poToo.asMewList())); // => 3, -1, -1, -1, -1
//        poToo.pop();
//        System.out.println(poToo.asList()); // => []
//        System.out.println(Arrays.toString(poToo.asMewList())); // => [-1, -1, -1, -1, -1]
    }
}
