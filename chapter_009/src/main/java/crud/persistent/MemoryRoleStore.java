package crud.persistent;

import crud.model.AdminRole;
import crud.model.Role;
import crud.model.UserRole;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Memory role starage
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class MemoryRoleStore implements RoleStore {
    private final ConcurrentHashMap<String, Role> roles = new ConcurrentHashMap<>();
    private final static MemoryRoleStore INSTANCE = new MemoryRoleStore();
    private final static String ADMINISTRATOR_NAME = "administrator";


    public MemoryRoleStore() {
    }

    public static MemoryRoleStore getInstance() {
        INSTANCE.roles.put(ADMINISTRATOR_NAME, new AdminRole(0, ADMINISTRATOR_NAME));
        INSTANCE.roles.put("user", new UserRole(1, "user"));
        return INSTANCE;
    }
    @Override
    public List<Role> getRoles() {
        return new ArrayList<>(this.roles.values());
    }

    @Override
    public Role getRole(String name) {
        return this.roles.get(name);
    }

    @Override
    public Role getAdministrationRole() {
        return this.getRole(ADMINISTRATOR_NAME);
    }
}
