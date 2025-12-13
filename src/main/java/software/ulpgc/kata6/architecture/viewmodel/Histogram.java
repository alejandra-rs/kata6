package software.ulpgc.kata6.architecture.viewmodel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Histogram implements Iterable<Integer> {

    private final Map<Integer, Integer> map;
    private final Map<String, String> labels;

    public Histogram(Map<String, String> labels) {
        this.map = new HashMap<>();
        this.labels = labels;
    }

    public void add(int key) {
        map.put(key, count(key) + 1);
    }

    public Integer count(int key) {
        return map.getOrDefault(key, 0);
    }

    public int size() {
        return map.size();
    }

    public String title() {
        return labels.getOrDefault("title", "");
    }

    public String x() {
        return labels.getOrDefault("x", "");
    }

    public String legend() {
        return labels.getOrDefault("legend", "");
    }

    @Override
    public Iterator<Integer> iterator() {
        return map.keySet().iterator();
    }
}
