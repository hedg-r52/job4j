package crud.presentation;

import crud.logic.*;
import crud.persistent.MemoryRoleStore;
import crud.persistent.RoleStore;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * User servlet
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UserServlet extends HttpServlet {
    private final Validate logic = ValidateService.getInstance();
    private final RoleStore roleStore = MemoryRoleStore.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", logic.findAll());
        req.getRequestDispatcher("/WEB-INF/views/UserList.jsp").forward(req, resp);
    }

    /**
     * Delete user
     *
     * @param req  request
     * @param resp response
     * @throws ServletException servlet exception
     * @throws IOException      io exception
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        int id = Integer.valueOf(req.getParameter("id"));
        logic.delete(id);
        resp.sendRedirect(req.getContextPath() + "/users");
    }
}
