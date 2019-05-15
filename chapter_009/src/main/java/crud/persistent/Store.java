package crud.persistent;

import crud.model.User;
import java.util.List;

/**
 * Store interface
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface Store {
    boolean add(User user);
    boolean update(int index, User user);
    boolean delete(int id);
    List<User> findAll();
    User findById(int id);
}
