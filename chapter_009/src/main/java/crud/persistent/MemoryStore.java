package crud.persistent;

import crud.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * MemoryStore
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class MemoryStore implements Store<User> {
    private volatile AtomicInteger id = new AtomicInteger(0);
    private static final MemoryStore INSTANCE = new MemoryStore();
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    public MemoryStore() {
    }

    public static MemoryStore getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(User user) {
        boolean result = false;
        if (!users.containsValue(user)) {
            int userId = id.incrementAndGet();
            user.setId(userId);
            users.put(userId, user);
            result = true;
        }
        return result;
    }

    @Override
    public boolean update(int index, User user) {
        boolean result = false;
        if (users.containsKey(index) && !users.get(index).equals(user)) {
            users.replace(index, user);
            result = true;
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        return Objects.nonNull(users.remove(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.of(users.get(id));
    }

    @Override
    public boolean isCredential(String login, String password) {
        boolean result = false;
        for (User user : this.users.values()) {
            if (login.equals(user.getLogin()) && password.equals(user.getPassword())) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        Optional<User> result = Optional.empty();
        for (User user : this.users.values()) {
            if (login.equals(user.getLogin())) {
                result = Optional.of(user);
                break;
            }
        }
        return result;
    }
}
