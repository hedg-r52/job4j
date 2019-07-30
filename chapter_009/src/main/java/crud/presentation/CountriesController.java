package crud.presentation;

import com.google.common.base.Joiner;
import crud.persistent.DBGeo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class CountriesController extends HttpServlet {
    private final DBGeo dbGeo = DBGeo.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json");
        PrintWriter writer = resp.getWriter();
        List<String> countries = dbGeo.countries();
        String result = String.format(
                "[%s]",
                Joiner.on(", ").join(
                        countries.stream()
                                .map(s -> String.format("{\"title\":\"%s\"}", s))
                                .collect(Collectors.toList())
                )
        );
        writer.append(result);
        writer.flush();
    }
}
