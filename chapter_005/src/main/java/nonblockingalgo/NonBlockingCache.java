package nonblockingalgo;

import java.util.concurrent.ConcurrentHashMap;

public class NonBlockingCache {
    private ConcurrentHashMap<Integer, Base> models = new ConcurrentHashMap<>();

    public void add(Base model) {
        models.putIfAbsent(model.getId(), model);
    }

    public void update(Base model) {
        models.computeIfPresent(model.getId(), (k, v) -> {
            if (models.get(model.getId()).getVersion() >= model.getVersion()) {
                throw new OptimisticException();
            }
            return model;
        });
    }

    public void delete(Base model) {
        models.remove(model.getId());
    }

    protected Base getModel(Integer id) {
        return models.get(id);
    }
}
