package crud.presentation;

import crud.logic.ValidateService;
import crud.model.User;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class EditFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        String id = request.getParameter("id");
        boolean isAdmin = (boolean) session.getAttribute("admin");
        Integer currentId = (Integer) session.getAttribute("id");
        Optional<User> user = ("".equals(id) ? Optional.empty() : ValidateService.getInstance().findById(Integer.valueOf(id)));
        if (user.isEmpty() || (!isAdmin && !currentId.equals(user.get().getId()))) {
            response.sendRedirect(String.format("%s/users", request.getContextPath()));
        } else {
            chain.doFilter(req, resp);
        }
    }
}
