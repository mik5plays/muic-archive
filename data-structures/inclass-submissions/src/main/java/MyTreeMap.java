import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class MyTreeMap<K extends Comparable<K>, V> {
    K key;
    V value;
    MyTreeMap<K, V> left, right;

     public MyTreeMap(K key, V value) {
        this.key = key;
        this.value = value;
        this.left = null; this.right = null;
    }

    V get(K k) {
        MyTreeMap x = this;
        V ans = null;
        while (x != null) {
            if (x.key.compareTo(k) >= 0) {
                x = x.left;
            } else {
                ans = (V) x.value;
                x = x.right; // check for possible larger values
            }
        }
        return ans;
    }

    void put(K k, V v) {
        if (k == null || v == null) { return; }

        if (this.key.compareTo(k) == 0) { this.value = v; }

        MyTreeMap x = this;
        while (x.left != null && x.right != null && x.key != k) {
            if (x.key.compareTo(k) < 0) { x = this.right; }
            else if (x.key.compareTo(k) > 0) { x = this.left; }
        }

        if (x.key.compareTo(k) < 0) { x.left = new MyTreeMap(k, v); }
        else if (x.key.compareTo(k) > 0){ x.right = new MyTreeMap(k, v); }
        else { x.value = v; }
    }

    K lowerKey(K k) {
         return k;

    }
}
