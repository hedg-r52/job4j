package crud.persistent;

import crud.model.Role;
import java.util.List;

/**
 * Role store interface
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface RoleStore {
    List<Role> getRoles();
    Role getRole(String name);
    Role getAdministrationRole();
}
