package util.filter;

@FunctionalInterface
public interface Filter<T> {
    boolean test(T t);
}
