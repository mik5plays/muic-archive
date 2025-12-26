package dictionary;

public interface Dictionary<T extends Enum<T>> {
    T lookup(String arg);
}
