package crud.presentation;

import crud.logic.*;
import crud.model.User;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * User servlet
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UserServlet extends HttpServlet {
    private Map<String, Function<HttpServletRequest, String>> dispatch = new HashMap<>();
    private final Validate logic = ValidateService.getInstance();

    public UserServlet() {
        this.dispatch.put("add", this::add);
        this.dispatch.put("update", this::update);
        this.dispatch.put("delete", this::delete);
    }

    /**
     * Method returns list all users
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(logic.findAll());
        writer.flush();
    }

    /**
     * Method for 3 actions: create, modify or delete user
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Function<HttpServletRequest, String> function = dispatch.getOrDefault(action, request -> {
            throw new UnsupportedOperationException(String.format("Action %s is not found", action));
        });
        String result = function.apply(req);
        resp.getOutputStream().println(result);
        doGet(req, resp);
    }

    /**
     * handle for adding user
     * @param req request
     * @return message
     */
    private String add(HttpServletRequest req) {
        String name = getStringParameter(req, "name");
        String login = getStringParameter(req, "login");
        String email = getStringParameter(req, "email");
        return logic.add(new User(name, login, email));
    }

    private String update(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = getStringParameter(req, "name");
        String login = getStringParameter(req, "login");
        String email = getStringParameter(req, "email");
        return logic.update(new User(id, name, login, email));
    }

    private String delete(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        return logic.delete(id);
    }

    private String getStringParameter(HttpServletRequest req, String name) {
        String result = req.getParameter(name);
        return result != null ? result : "";
    }
}
