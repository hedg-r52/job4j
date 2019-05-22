package crud.model;

/**
 * Interface role
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface Role {
    default boolean isAdministrator() {
        return false;
    }
    String getName();
    int getId();
}
