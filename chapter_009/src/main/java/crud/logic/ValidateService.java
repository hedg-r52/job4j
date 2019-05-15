package crud.logic;

import crud.model.User;
import crud.persistent.MemoryStore;
import crud.persistent.Store;
import java.util.List;

/**
 * Validate service
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ValidateService implements Validate {
    private static final ValidateService INSTANCE = new ValidateService();
    private final Store store = MemoryStore.getInstance();

    public ValidateService() {
    }

    public static ValidateService getInstance() {
        return INSTANCE;
    }

    @Override
    public String add(User user) {
        String result = "";
        if (!user.name().isEmpty() && !user.login().isEmpty() && !user.email().isEmpty()) {
            result = (store.add(user) ? "User added." : "User not added.");
        } else {
            result += (user.name().isEmpty() ? "Name is empty" + System.lineSeparator() : "");
            result += (user.login().isEmpty() ? "Login is empty" + System.lineSeparator() : "");
            result += (user.email().isEmpty() ? "Email is empty" + System.lineSeparator() : "");
        }
        return result;
    }

    @Override
    public String update(User user) {
        return store.update(user.id(), user) ? "User updated." : "User is not updated.";
    }

    @Override
    public String delete(int id) {
        return store.delete(id) ? "User deleted." : "User is not deleted.";
    }

    @Override
    public String findAll() {
        List<User> users = store.findAll();
        return users.isEmpty() ? "No users at storage." : users.toString();
    }

    @Override
    public String findById(int id) {
        User user = store.findById(id);
        return user == null ? "No user at storage." : user.toString();
    }
}
