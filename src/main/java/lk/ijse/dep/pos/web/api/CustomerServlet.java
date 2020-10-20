package lk.ijse.dep.pos.web.api;


import lk.ijse.dep.pos.web.business.custom.CustomerBO;
import lk.ijse.dep.pos.web.dto.CustomerDTO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@WebServlet(name = "CustomerServlet", urlPatterns = "/customers")
public class CustomerServlet extends HttpServlet {
    private CustomerBO customerBO;

    public void init(){
        customerBO = ((AnnotationConfigApplicationContext)(getServletContext().getAttribute("ctx"))).getBean(CustomerBO.class);
    }

    public static String getParameter(String queryString, String parameterName) throws UnsupportedEncodingException {
        if (queryString == null || parameterName == null || queryString.trim().isEmpty() || parameterName.trim().isEmpty()) {
            return null;
        }
        String[] queryParameters = queryString.split("&");
        for (String queryParameter : queryParameters) {
            if (queryParameter.contains("=") && queryParameter.startsWith(parameterName)) {
                return URLDecoder.decode(queryParameter.split("=")[1], "UTF-8");
            }
        }
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        resp.setContentType("text/plain");
        try (PrintWriter out = resp.getWriter()){
            if (id==null) {
                resp.setContentType("application/json");
                List<CustomerDTO> allCustomers = customerBO.getAllCustomers();
                Jsonb jsonb = JsonbBuilder.create();
                String json = jsonb.toJson(allCustomers);
                out.println(json);
            }else{
                if (customerBO.customerExist(id)==true) {
                    CustomerDTO customer = customerBO.getCustomer(id);
                    out.println(customer);
                }
                else{
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        if (id == null || name == null || address == null | !id.matches("C\\d{3}") || name.trim().length()<3 || address.trim().length()<3) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        resp.setContentType("text/plain");

        try (PrintWriter out = resp.getWriter()){
            System.out.println(customerBO.customerExist(id));
            if (customerBO.customerExist(id)==true) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            customerBO.saveCustomer(id,name,address);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            out.println("Customer Saved Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { ;
        String queryString = req.getQueryString();

        resp.setContentType("text/plain");
        if (queryString == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        BufferedReader reader = req.getReader();
        String line = null;
        String requestBody = "";

        while ((line = reader.readLine())!=null) {
            requestBody+= line;
        }

        String id = getParameter(queryString,"id");
        String name = getParameter(requestBody, "name");
        String address = getParameter(requestBody, "address");

        System.out.println(id);
        System.out.println(name);
        System.out.println(address);

        if (name == null || address == null ||  name.trim().length() < 3 || address.trim().length() < 3) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try{
            if (!customerBO.customerExist(id)) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);

            }
            customerBO.updateCustomer(id,name,address);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        System.out.println(id);
        resp.setContentType("text/plain");
        if (customerBO.customerExist(id)==true) {
            try {
                customerBO.deleteCustomer(id);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
        else{
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
