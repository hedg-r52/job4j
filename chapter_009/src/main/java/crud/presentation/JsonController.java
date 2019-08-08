package crud.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import crud.model.Person;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class JsonController extends HttpServlet {
    private final Map<Integer, Person> persons = new ConcurrentHashMap();
    private final AtomicInteger id = new AtomicInteger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, persons);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            if (reader != null) {
                sb.append(reader.readLine());
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        Person person = mapper.readValue(sb.toString(), Person.class);
        this.persons.put(id.getAndIncrement(), person);
    }
}
