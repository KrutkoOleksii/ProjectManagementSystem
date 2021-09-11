package ua.goit.controller;

import com.google.gson.Gson;
import ua.goit.model.Company;
import ua.goit.repository.BaseRepository;
import ua.goit.repository.Factory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

public class CompanyServlet extends HttpServlet {

    private final BaseRepository<Long,Company> companyRepository;
    private final Gson gson = new Gson();

    public CompanyServlet() {
        companyRepository = Factory.of(Company.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo==null || "/".equals(pathInfo)) {
            sendAsJson(resp, companyRepository.findAll());
            return;
        }
        String[] split = pathInfo.split("/");
        if (split.length!=2){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        sendAsJson(resp, companyRepository.findById(Long.parseLong(split[1])));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String payload = req.getReader().lines().collect(Collectors.joining("\n"));
        Company company = gson.fromJson(payload, Company.class);
        sendAsJson(resp, companyRepository.save(company));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String payload = req.getReader().lines().collect(Collectors.joining("\n"));
        Company company = gson.fromJson(payload, Company.class);
        sendAsJson(resp, companyRepository.save(company));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String[] split = req.getPathInfo().split("/");
        companyRepository.deleteById(Long.parseLong(split[1]));
    }

    private void sendAsJson(HttpServletResponse resp, Object payload) throws IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        String result = gson.toJson(payload);
        writer.print(result );
        writer.flush();
    }

}
