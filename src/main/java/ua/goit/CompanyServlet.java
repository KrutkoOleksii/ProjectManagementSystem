package ua.goit;

import com.google.gson.Gson;
import ua.goit.model.Company;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CompanyServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final Map<Long, Company> companyMap = new LinkedHashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo==null || "/".equals(pathInfo)) {
            Collection<Company> values = companyMap.values();
            sendAsJson(resp, values);
            return;
        }
        String[] split = pathInfo.split("/");
        if (split.length!=2){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (companyMap.containsKey(split[1])){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        sendAsJson(resp, companyMap.get(split[1]));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //BufferedReader reader = req.getReader();
        String payload = req.getReader().lines().collect(Collectors.joining("\n"));
        Company company = gson.fromJson(payload, Company.class);
        //String uuid = UUID.randomUUID().toString();
        companyMap.put(company.getId(),company);
        sendAsJson(resp,company);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String payload = req.getReader().lines().collect(Collectors.joining("\n"));
        Company company = gson.fromJson(payload, Company.class);
        if (companyMap.containsKey(company.getId()) ) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        companyMap.put(company.getId(), company);
        sendAsJson(resp,company);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String[] split = pathInfo.split("/");
        if (companyMap.containsKey(split[1])){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Company remove = companyMap.remove(split[1]);
        sendAsJson(resp, remove);
    }

    private void sendAsJson(HttpServletResponse resp, Object payload) throws IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        String result = gson.toJson(payload);
        writer.print(result );
        writer.flush();
    }
    public CompanyServlet() {
        //String uuid1 = UUID.randomUUID().toString();
        companyMap.put(30L, Company.builder()
                .id(30L)
                .name("Servlet center")
                .code("57473727")
                .build());
        //String uuid2 = UUID.randomUUID().toString();
        companyMap.put(31L, Company.builder()
                .id(31L)
                .name("Web finance")
                .code("10011001")
                .build());
    }
}
