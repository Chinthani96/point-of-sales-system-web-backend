package lk.ijse.dep.pos.web.api;

import lk.ijse.dep.pos.web.business.custom.CustomerBO;
import lk.ijse.dep.pos.web.business.custom.OrderBO;
import lk.ijse.dep.pos.web.dto.CustomerDTO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;

@WebServlet(name = "OrderServlet", urlPatterns = "/api/v1/orders")
public class OrderServlet extends HttpServlet {

    private OrderBO orderBO;
    private CustomerBO customerBO;

    public void init(){
        orderBO = ((AnnotationConfigApplicationContext) (getServletContext().getAttribute("ctx"))).getBean(OrderBO.class);
        customerBO = ((AnnotationConfigApplicationContext)(getServletContext().getAttribute("ctx"))).getBean(CustomerBO.class);
    }

    private String getParameter(String queryString, String parameterName) throws UnsupportedEncodingException {
        if (queryString == null || parameterName == null) {
            return null;
        }

        String[] queryParameters = queryString.split("&");
        for (String queryParameter : queryParameters) {
            if (queryParameter.contains("=") && queryParameter.startsWith(parameterName)) {
                return URLDecoder.decode(queryParameter.split("=")[1],"UTF-8");
            }
        }
        return null;
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getParameter("txt-id");
        String customerId = req.getParameter("txt-name");
        String orderDate = req.getParameter("txt-date");

        String[] codes = req.getParameterValues("code");
        String[] unitPrices = req.getParameterValues("unitPrice");
        String[] qty = req.getParameterValues("qty");


        resp.setContentType("plain/text");
        try(PrintWriter out = resp.getWriter()) {
            if (customerBO.customerExist(customerId) == true && !(orderBO.orderExist(orderId))) {
                CustomerDTO customer = customerBO.getCustomer(customerId);
                orderBO.saveOrder(orderId,Date.valueOf(orderDate),customer);
                for (int i = 0 ; i < codes.length ; i++) {
                    orderBO.saveOrderDetail(orderId,codes[i],Integer.parseInt(qty[i]),Double.parseDouble(unitPrices[i]));
                }
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }
            else{
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
