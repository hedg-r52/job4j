package tdd;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 23.03.2018
 */
public class Pair {
    private String key;
    private String value;

    /**
     * constuctor
     * @param key key
     * @param value value
     */
    public Pair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * getter for key field
     * @return
     */
    public String getKey() {
        return key;
    }

    /**
     * getter for value field
     * @return
     */
    public String getValue() {
        return value;
    }
}
