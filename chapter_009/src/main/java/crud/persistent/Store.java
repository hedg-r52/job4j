package crud.persistent;

import crud.model.User;
import java.util.List;
import java.util.Optional;

/**
 * Store interface
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface Store<T> {
    boolean add(T t);
    boolean update(int index, T t);
    boolean delete(int id);
    List<T> findAll();
    Optional<T> findById(int id);
    boolean isCredential(String login, String password);
    Optional<User> findByLogin(String login);
}
