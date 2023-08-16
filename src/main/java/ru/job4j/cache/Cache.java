package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (i, b) -> {
           if (memory.get(model.getId()).getVersion() != model.getVersion()) {
               throw new OptimisticException("Version are not equal.");
           }
           b = new Base(model.getId(), model.getVersion() + 1);
           b.setName(model.getName());
           return b;
        }) != null;

    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Base get(int id) {
        return memory.get(id);
    }

}
