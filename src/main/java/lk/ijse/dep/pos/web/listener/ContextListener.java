package lk.ijse.dep.pos.web.listener;

import lk.ijse.dep.pos.web.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        sce.getServletContext().setAttribute("ctx",ctx);
    }
}
