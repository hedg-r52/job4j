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
 * User create servlet
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UserCreateServlet extends HttpServlet {
    private final Validate logic = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        StringBuilder sb = new StringBuilder("<table border=1>");
        sb.append("<tr><th>Id</th><th>Name</th><th>Login</th><th>Email</th></tr>");
        for (User user : logic.findAll()) {
            sb.append("<tr>")
                .append("<td>").append(user.id()).append("</td>")
                .append("<td>").append(user.name()).append("</td>)")
                .append("<td>").append(user.login()).append("</td>")
                .append("<td>").append(user.email()).append("</td>")
                .append("</tr>");
        }
        sb.append("</table>");
        writer.append("<!DOCTYPE html>")
                        .append("<html lang=\"en\">")
                        .append("<head>")
                        .append("    <meta charset=\"UTF-8\">")
                        .append("    <title>Add a new user</title>")
                        .append("</head>")
                        .append("<body>")
                        .append("<form action = ' ").append(req.getContextPath()).append("/create' method='post'>")
                        .append("Name : <input type = 'text' name='name'/>")
                        .append("Login : <input type = 'text' name='login'/>")
                        .append("Email : <input type = 'text' name='email'/>")
                        .append("<input type = 'submit'>")
                        .append("</form>")
                        .append("<br/>")
                        .append("<form action='").append(req.getContextPath()).append("/list' method='get'>")
                        .append("<input type='submit' value='User list'>")
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
        logic.add(new User(name, login, email));
        doGet(req, resp);
    }
}
