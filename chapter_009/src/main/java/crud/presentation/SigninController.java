package crud.presentation;

import crud.model.User;
import crud.persistent.DBStore;
import crud.persistent.MemoryRoleStore;
import crud.persistent.RoleStore;
import crud.persistent.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Sign in controller
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SigninController extends HttpServlet {
    private final Store store = DBStore.getInstance();
    private final RoleStore roleStore = MemoryRoleStore.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/UserSignIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (store.isCredential(login, password)) {
            HttpSession session = req.getSession();
            session.setAttribute("login", login);
            User user = (User) store.findByLogin(login).get();
            String role = user.getRole();
            session.setAttribute("id", user.getId());
            session.setAttribute("admin", roleStore.getRole(role).isAdministrator());
            resp.sendRedirect(String.format("%s/users", req.getContextPath()));
        } else {
            req.setAttribute("error", "Credential invalid.");
            doGet(req, resp);
        }
    }
}
