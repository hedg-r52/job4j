package crud.logic;

import crud.model.User;
import crud.persistent.DBStore;
import crud.persistent.Store;
import java.util.List;
import java.util.Optional;

/**
 * Validate service
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ValidateService implements Validate {
    private static final ValidateService INSTANCE = new ValidateService();
    private final Store store = DBStore.getInstance();

    public ValidateService() {
    }

    public static Validate getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(User user) {
        boolean result = false;
        if (!isExist(user)) {
            store.add(user);
            result = true;
        }
        return result;
    }

    @Override
    public boolean update(User user) {
        return store.update(user.getId(), user);
    }

    @Override
    public boolean delete(int id) {
        return store.delete(id);
    }

    @Override
    public List<User> findAll() {
        return store.findAll();
    }

    @Override
    public Optional<User> findById(int id) {
        return store.findById(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return store.findByLogin(login);
    }

    private boolean isExist(User user) {
        return !store.findAll().isEmpty() && store.findAll().contains(user);
    }
}
