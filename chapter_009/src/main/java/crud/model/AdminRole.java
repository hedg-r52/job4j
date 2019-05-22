package crud.model;

/**
 * Admin role
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class AdminRole extends AbstractRole {
    public AdminRole(int id, String name) {
        super(id, name);
    }

    @Override
    public boolean isAdministrator() {
        return true;
    }
}
