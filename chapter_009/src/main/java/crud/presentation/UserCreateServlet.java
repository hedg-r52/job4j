package crud.presentation;

import crud.logic.Validate;
import crud.logic.ValidateService;
import crud.model.User;
import crud.persistent.MemoryRoleStore;
import crud.persistent.RoleStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    private final RoleStore roleStore = MemoryRoleStore.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", logic.findAll());
        req.setAttribute("roles", roleStore.getRoles());
        req.getRequestDispatcher("/WEB-INF/views/UserCreate.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        Optional<String> name = Optional.ofNullable(req.getParameter("name"));
        Optional<String> login = Optional.ofNullable(req.getParameter("login"));
        Optional<String> pass = Optional.ofNullable(req.getParameter("password"));
        Optional<String> email = Optional.ofNullable(req.getParameter("email"));
        Optional<String> role = Optional.ofNullable(req.getParameter("role"));
        if (name.isPresent() && login.isPresent() && email.isPresent() && pass.isPresent() && role.isPresent()) {
            logic.add(new User(name.get(), login.get(), email.get(), pass.get(), role.get()));
            resp.sendRedirect(req.getContextPath() + "/create");
        } else {
            req.setAttribute("error", "Not all fields are filled.");
            doGet(req, resp);
        }
    }
}
