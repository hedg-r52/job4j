package netmanager;

/**
 * @author Andrei Solovev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public enum Command {
    IDLE("IDLE"),
    QUIT("QUIT"),
    LIST("LIST"),
    UPLOAD("UPLOAD"),
    DOWNLOAD("DOWNLOAD"),
    CD("CD"),
    WHERE("WHERE");

    private String name;

    Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
