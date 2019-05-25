package crud.logic;

import crud.model.User;

import java.util.*;

public class ValidateStub implements Validate {
    private final Map<Integer, User> store = new HashMap<>();
    private int ids = 0;

    @Override
    public boolean add(User user) {
        boolean result = false;
        if (!isExist(user)) {
            user.setId(this.ids++);
            this.store.put(user.getId(), user);
            result = true;
        }
        return result;
    }

    @Override
    public boolean update(User user) {
        boolean result = false;
        if (store.containsKey(user.getId()) && !store.get(user.getId()).equals(user)) {
            store.replace(user.getId(), user);
            result = true;
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        return Objects.nonNull(store.remove(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.of(store.get(id));
    }

    @Override
    public Optional<User> findByLogin(String login) {
        Optional<User> result = Optional.empty();
        for (User user : this.store.values()) {
            if (login.equals(user.getLogin())) {
                result = Optional.of(user);
                break;
            }
        }
        return Optional.empty();
    }

    private boolean isExist(User user) {
        return !this.findAll().isEmpty() && this.findAll().contains(user);
    }
}
