package crud.presentation;

import crud.logic.Validate;
import crud.logic.ValidateService;
import crud.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * User update servlet
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UserUpdateServlet extends HttpServlet {
    private final Validate logic = ValidateService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String name = Optional.of(req.getParameter("name")).orElse("");
        String login = Optional.of(req.getParameter("login")).orElse("");
        String email = Optional.of(req.getParameter("email")).orElse("");
        User user = new User(name, login, email);
        user.setId(Integer.valueOf(req.getParameter("id")));
        logic.update(user);
        resp.sendRedirect(req.getContextPath() + "/list/");
    }
}
