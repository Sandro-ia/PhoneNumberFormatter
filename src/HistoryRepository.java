import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryRepository<K,V> {

    private final Map<K,V> storage = new LinkedHashMap<>();

    public void save(K key, V value) {
        storage.put(key, value);
    }

    public Optional<V> findById(K key) {
        return Optional.ofNullable(storage.get(key));
    }

    public Collection<V> getAll() {
        return storage.values();
    }

    public boolean isEmpty() {
        return storage.isEmpty();
    }

    public int size() {
        return storage.size();
    }
}
