package lk.ijse.dep.pos.web;

import lk.ijse.dep.pos.web.JPAConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan("lk.ijse.dep.pos")
@Configuration
@Import(JPAConfig.class)
public class AppConfig {
}
