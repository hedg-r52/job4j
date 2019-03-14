package netmanager;

/**
 * Helper for parse request. Split on command and param string
 *
 * @author Andrei Solovev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class RequestParser {
    private static RequestParser requestParser;
    private String command;
    private String param;

    public static RequestParser getRequestParser() {
        if (requestParser == null) {
            requestParser = new RequestParser();
        }
        return requestParser;
    }

    private RequestParser() {
    }

    /**
     * Parse request
     * @param request
     */
    public synchronized void parse(String request) {
        String[] args = request.split(" ", 2);
        this.command = args[0].toUpperCase();
        this.param = (args.length > 1 ? args[1] : "");
    }

    public String getCommand() {
        return this.command;
    }

    public String getParam() {
        return this.param;
    }
}
