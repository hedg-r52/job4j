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
 * Test for UserUpdateServlet class
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidateService.class)
public class UserUpdateServletTest {
    @Test
    public void whenUpdateUserThenUserStoreNewData() throws ServletException, IOException {
        Validate validate = new ValidateStub();
        PowerMockito.mockStatic(ValidateService.class);
        when(ValidateService.getInstance()).thenReturn(validate);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        validate.add(new User("name", "login", "email", "password", "role"));
        User user = validate.findAll().iterator().next();
        assertThat(user.getName(), is("name"));
        assertThat(user.getLogin(), is("login"));
        assertThat(user.getEmail(), is("email"));
        assertThat(user.getPassword(), is("password"));
        assertThat(user.getRole(), is("role"));
        when(req.getParameter("id")).thenReturn("0");
        when(req.getParameter("login")).thenReturn("login2");
        when(req.getParameter("name")).thenReturn("name2");
        when(req.getParameter("password")).thenReturn("password2");
        when(req.getParameter("email")).thenReturn("email2");
        when(req.getParameter("role")).thenReturn("role2");
        new UserUpdateServlet().doPost(req, resp);
        User updatedUser = validate.findAll().iterator().next();
        assertThat(updatedUser.getName(), is("name2"));
        assertThat(updatedUser.getLogin(), is("login2"));
        assertThat(updatedUser.getPassword(), is("password2"));
        assertThat(updatedUser.getEmail(), is("email2"));
        assertThat(updatedUser.getRole(), is("role2"));
    }
}