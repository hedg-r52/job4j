package crud.presentation;

import crud.logic.*;
import crud.model.User;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User servlet
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UserServlet extends HttpServlet {
    private final Validate logic = ValidateService.getInstance();

    /**
     * Method returns list all users
     * @param req request
     * @param resp response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        StringBuilder sb = new StringBuilder("<table border=1>");
        sb.append("<tr><th>Id</th><th>Name</th><th>Login</th><th>Email</th><th></th><th></th></tr>");
        for (User user : logic.findAll()) {
            sb.append("<tr>")
                    .append("<td>").append(user.id()).append("</td>")
                    .append("<td>").append(user.name()).append("</td>")
                    .append("<td>").append(user.login()).append("</td>")
                    .append("<td>").append(user.email()).append("</td>")
                    .append("<td>")
                    .append("<form action='").append(req.getContextPath()).append("/list' method='post'>")
                    .append("<button name='id' type='hidden' value=").append(user.id()).append(">Delete</button>")
                    .append("</form>")
                    .append("</td>")
                    .append("<td>")
                    .append("<form action='").append(req.getContextPath()).append("/edit' method='get'>")
                    .append("<button name='id' type='hidden' value=").append(user.id()).append(">Update</button>")
                    .append("</form>")
                    .append("</td>")
                    .append("</tr>");
        }
        sb.append("</table>");
        writer.append("<!DOCTYPE html>")
                        .append("<html lang=\"en\">")
                        .append("<head>")
                        .append("    <meta charset=\"UTF-8\">")
                        .append("    <title>List of users</title>")
                        .append("</head>")
                        .append("<br/>")
                        .append("<form action='").append(req.getContextPath()).append("/create' method='get'>")
                        .append("<input type='submit' value='Create new user'>")
                        .append("</form>")
                        .append("<br>List of users:</br>")
                        .append(sb.toString())
                        .append("</body>")
                        .append("</html>");
        writer.flush();
    }

    /**
     * Method for 3 actions: create, modify or delete user
     * @param req request
     * @param resp response
     * @throws ServletException servlet exception
     * @throws IOException io exception
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        int id = Integer.valueOf(req.getParameter("id"));
        logic.delete(id);
        doGet(req, resp);
    }
}
