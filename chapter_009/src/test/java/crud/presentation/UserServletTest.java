package crud.presentation;

import crud.logic.Validate;
import crud.logic.ValidateService;
import crud.logic.ValidateStub;
import crud.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test for UserServlet class
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidateService.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*"})
public class UserServletTest {

    @Test
    public void whenDeleteUserThenRemoveItFromStore() throws IOException {
        Validate validate = new ValidateStub();
        PowerMockito.mockStatic(ValidateService.class);
        when(ValidateService.getInstance()).thenReturn(validate);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        validate.add(new User("name", "login", "email", "password", "role"));
        assertThat(validate.findAll().size(), is(1));
        when(req.getParameter("id")).thenReturn("0");
        new UserServlet().doPost(req, resp);
        assertThat(validate.findAll().size(), is(0));
    }
}