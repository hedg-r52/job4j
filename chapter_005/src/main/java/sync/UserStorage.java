package sync;

import java.util.HashMap;

/**
 * Хранилище пользователей
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UserStorage {

    private HashMap<Integer, User> users;

    public UserStorage() {
        this.users = new HashMap<>();
    }

    public boolean add(User user) {
        boolean result = false;
        if (!this.users.containsKey(user.getId())) {
            this.users.put(user.getId(), user);
            result = true;
        }
        return result;
    }

    public boolean update(User user) {
        boolean result = false;
        if (this.users.containsKey(user.getId())) {
            this.users.put(user.getId(), user);
            result = true;
        }
        return result;
    }

    protected boolean isUserExist(int id) {
        return this.users.containsKey(id);
    }

    public boolean delete(User user) {
        boolean result = false;
        if (this.users.containsKey(user.getId())) {
            this.users.remove(user.getId());
            result = true;
        }
        return result;
    }

    protected int getUserAmount(int index) {
        return this.users.get(index).getAmount();
    }

    public void transfer(int fromId, int toId, int amount) {
        User userFrom = this.users.get(fromId);
        User userTo = this.users.get(toId);
        if (userFrom.getAmount() > amount) {
            userFrom.setAmount(userFrom.getAmount() - amount);
            userTo.setAmount(userTo.getAmount() + amount);
        }
    }

}
