package crud.model;

/**
 * Abstract role
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public abstract class AbstractRole implements Role {
    protected final int id;
    protected final String name;

    public AbstractRole(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
