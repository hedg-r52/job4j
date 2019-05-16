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
    private volatile int id;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        StringBuilder sb = new StringBuilder("<table>");
        this.id = Integer.valueOf(req.getParameter("id"));
        User user = logic.findById(id).get();
        sb.append("<tr><td>" + "Parameter's  user: ").append(user.login())
                .append("<form action = ' ").append(req.getContextPath()).append("/edit' method='post'>")
                .append("Name : <input type = 'text' value=").append(user.name()).append(" name='name'/>")
                .append("Login : <input type = 'text' value=").append(user.login()).append(" name='login'/>")
                .append("Email : <input type = 'text' value=").append(user.email()).append(" name='email'/>")
                .append("<input type = 'submit'>")
                .append("</form>")
                .append("</td></tr>");
        sb.append("</table>");
        writer.append("<!DOCTYPE html>")
                        .append("<html lang=\"en\">")
                        .append("<head>")
                        .append("    <meta charset=\"UTF-8\">")
                        .append("    <title>Update of user</title>")
                        .append("</head>")
                        .append("<br/>")
                        .append("<form action='").append(req.getContextPath()).append("/list' method='get'>")
                        .append("<input type='submit' value='Return to list of user'>")
                        .append("</form>")
                        .append(sb.toString())
                        .append("</body>")
                        .append("</html>");
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String name = Optional.of(req.getParameter("name")).orElse("");
        String login = Optional.of(req.getParameter("login")).orElse("");
        String email = Optional.of(req.getParameter("email")).orElse("");
        User user = new User(name, login, email);
        user.setId(id);
        logic.update(user);
        resp.sendRedirect(req.getContextPath() + "/list");
    }
}
