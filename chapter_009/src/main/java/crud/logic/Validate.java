package crud.logic;

import crud.model.User;
import java.util.List;
import java.util.Optional;

/**
 * Validate interface
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface Validate {
    boolean add(User user);
    boolean update(User user);
    boolean delete(int id);
    List<User> findAll();
    Optional<User> findById(int id);
    Optional<User> findByLogin(String login);
}
