@FunctionalInterface
public interface Parser<T> {

    T parse(String line);
}
