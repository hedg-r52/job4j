package crud.presentation;

import crud.logic.Validate;
import crud.logic.ValidateService;
import crud.logic.ValidateStub;
import crud.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test for UserCreateServlet class
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidateService.class)
public class UserCreateServletTest {
    @Test
    public void whenAddUserThenStoreIt() throws ServletException, IOException {
        Validate validate = new ValidateStub();
        PowerMockito.mockStatic(ValidateService.class);
        when(ValidateService.getInstance()).thenReturn(validate);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("login")).thenReturn("user login");
        when(req.getParameter("name")).thenReturn("user name");
        when(req.getParameter("password")).thenReturn("password");
        when(req.getParameter("email")).thenReturn("no@mail.ru");
        when(req.getParameter("role")).thenReturn("role");
        when(req.getParameter("country")).thenReturn("country");
        when(req.getParameter("city")).thenReturn("city");
        new UserCreateServlet().doPost(req, resp);
        User user = validate.findAll().iterator().next();
        assertThat(user.getName(), is("user name"));
        assertThat(user.getLogin(), is("user login"));
        assertThat(user.getPassword(), is("password"));
        assertThat(user.getEmail(), is("no@mail.ru"));
        assertThat(user.getRole(), is("role"));
        assertThat(user.getCountry(), is("country"));
        assertThat(user.getCity(), is("city"));
    }
}
