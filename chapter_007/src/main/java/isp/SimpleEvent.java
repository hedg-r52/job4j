package isp;

/**
 * Simple event
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleEvent implements Event {
    private String message;

    /**
     * Set message
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get message
     * @return
     */
    @Override
    public String message() {
        return this.message;
    }
}
