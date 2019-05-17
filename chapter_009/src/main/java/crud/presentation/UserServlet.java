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
     * Delete user
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
        resp.sendRedirect(req.getContextPath() + "/list/");
    }
}
