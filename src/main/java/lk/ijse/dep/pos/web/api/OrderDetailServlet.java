package lk.ijse.dep.pos.web.api;

import lk.ijse.dep.pos.web.business.custom.OrderBO;
import lk.ijse.dep.pos.web.dto.SearchOrderDTO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "OrderDetailServlet", urlPatterns = "/api/v1/order-details")
public class OrderDetailServlet extends HttpServlet {

    protected OrderBO orderBO;

    public void init(){
        orderBO = ((AnnotationConfigApplicationContext) (getServletContext().getAttribute("ctx"))).getBean(OrderBO.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        resp.setContentType("text/plain");
        try (PrintWriter out = resp.getWriter()){

            if (id==null) {
                resp.setContentType("application/json");
                List<SearchOrderDTO> orderDetails = orderBO.getOrderDetails();
                Jsonb jsonb = JsonbBuilder.create();
                String json = jsonb.toJson(orderDetails);
                out.println(json);
            }
            else{
                out.println("No way this happens!!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
