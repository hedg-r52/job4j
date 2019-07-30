package crud.presentation;

import com.google.common.base.Joiner;
import crud.persistent.DBGeo;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class CitiesController extends HttpServlet {
    private final DBGeo dbGeo = DBGeo.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json");
        String country = req.getParameter("country");
        PrintWriter writer = resp.getWriter();
        List<String> cities = dbGeo.cities(country);
        String result = String.format(
                "[%s]",
                Joiner.on(", ").join(
                        cities.stream()
                                .map(s -> String.format("{\"title\":\"%s\"}", s))
                                .collect(Collectors.toList())
                )
        );
        writer.append(result);
        writer.flush();
    }
}
