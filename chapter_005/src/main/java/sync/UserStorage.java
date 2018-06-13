package sync;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;

/**
 * Хранилище пользователей
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final HashMap<Integer, User> users;

    public UserStorage() {
        this.users = new HashMap<>();
    }

    public boolean add(User user) {
        boolean result = false;
        synchronized (this) {
            if (!this.users.containsKey(user.getId())) {
                this.users.put(user.getId(), user);
                result = true;
            }
        }
        return result;
    }

    public boolean update(User user) {
        boolean result = false;
        synchronized (this) {
            if (this.users.containsKey(user.getId())) {
                this.users.put(user.getId(), user);
                result = true;
            }
        }
        return result;
    }

    protected boolean isUserExist(int id) {
        synchronized (this) {
            return this.users.containsKey(id);
        }
    }

    public boolean delete(User user) {
        boolean result = false;
        synchronized (this) {
            if (this.users.containsKey(user.getId())) {
                this.users.remove(user.getId());
                result = true;
            }
        }
        return result;
    }

    protected int getUserAmount(int index) {
        synchronized (this) {
            return this.users.get(index).getAmount();
        }
    }

    public void transfer(int fromId, int toId, int amount) {
        synchronized (this) {
            User userFrom = this.users.get(fromId);
            User userTo = this.users.get(toId);
            if (userFrom.getAmount() > amount) {
                userFrom.setAmount(userFrom.getAmount() - amount);
                userTo.setAmount(userTo.getAmount() + amount);
            }
        }
    }

}
