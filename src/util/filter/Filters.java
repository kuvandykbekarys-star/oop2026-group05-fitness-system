package util.filter;

import java.util.ArrayList;
import java.util.List;

public class Filters {
    public static <T> List<T> filter(List<T> items, Filter<T> filter) {
        List<T> out = new ArrayList<>();
        for (T item : items) {
            if (filter.test(item)) out.add(item);
        }
        return out;
    }
}
