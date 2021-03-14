package zhuj.android.utils.model;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder<K, V> {
    private final Map<K, V> map;

    public MapBuilder() {
        map = new HashMap<>();
    }

    public MapBuilder(Map<K, V> map) {
        this.map = map;
    }

    public Map<K, V> build() {
        return map;
    }

    public  MapBuilder<K, V> put(K key, V value) {
        map.put(key, value);
        return this;
    }

    public  MapBuilder<K, V> putAll(Map<K, V> kvMap) {
        map.putAll(kvMap);
        return this;
    }
}
