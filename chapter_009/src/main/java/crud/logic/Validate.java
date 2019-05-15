package crud.logic;

import crud.model.User;

/**
 * Validate interface
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface Validate {
    String add(User user);
    String update(User user);
    String delete(int id);
    String findAll();
    String findById(int id);
}
