package lk.ijse.dep.pos.web.api;

import lk.ijse.dep.pos.web.business.custom.ItemBO;
import lk.ijse.dep.pos.web.dto.ItemDTO;
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

@WebServlet(name = "ItemServlet", urlPatterns = "/api/v1/items")
public class ItemServlet extends HttpServlet {

    private ItemBO itemBO;

    public void init() {
        itemBO = ((AnnotationConfigApplicationContext) (getServletContext().getAttribute("ctx"))).getBean(ItemBO.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("txt-id");

        resp.setContentType("text/plain");
        try (PrintWriter out = resp.getWriter()) {
            if (code == null) {
                List<ItemDTO> allItems = itemBO.getAllItems();
                resp.setContentType("application/json");
                Jsonb jsonb = JsonbBuilder.create();
                String json = jsonb.toJson(allItems);
                out.println(json);
            } else {
                if (!itemBO.itemExist(code)) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                ItemDTO item = itemBO.getItem(code);
                out.println(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("txt-id");
        String description = req.getParameter("txt-desc");
        String unitPrice = req.getParameter("txt-price");
        String qtyOnHand = req.getParameter("txt-qty");

        if (code == null || description == null || unitPrice == null || qtyOnHand == null || !code.matches("I\\d{3}") || description.trim().length() < 2 || unitPrice.trim().length() < 1 || qtyOnHand.trim().length() < 1) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        resp.setContentType("text/plain");
        if (itemBO.itemExist(code)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            itemBO.saveItem(code, description, Double.parseDouble(unitPrice), Integer.parseInt(qtyOnHand));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String queryString = req.getQueryString();

        resp.setContentType("text/plain");
        if (queryString == null) {
            System.out.println("1");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String line = null;
        String requestBody = "";

        BufferedReader reader = req.getReader();

        while ((line = reader.readLine()) != null) {
            requestBody += line;
        }

        String code = getParameter(queryString, "txt-id");
        System.out.println(code);
        if (code == null || !code.matches("I\\d{3}")) {
            System.out.println("2");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String description = getParameter(requestBody, "txt-desc");
        String unitPrice = getParameter(requestBody, "txt-price");
        String qtyOnHand = getParameter(requestBody, "txt-qty");

        if (description == null || unitPrice == null || qtyOnHand == null || description.trim().length() < 2 || unitPrice.trim().length() < 1 || qtyOnHand.trim().length() < 1) {
            System.out.println("3");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            if (!itemBO.itemExist(code)) {
                System.out.println("4");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            itemBO.updateItem(code, description, Double.parseDouble(unitPrice), Integer.parseInt(qtyOnHand));
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("txt-id");

        resp.setContentType("text/plaim");
        if (!itemBO.itemExist(code)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            itemBO.deleteItem(code);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private String getParameter(String queryString, String parameterName) throws UnsupportedEncodingException {

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
}
