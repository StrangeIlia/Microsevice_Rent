package bstu.BI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients

@SpringBootApplication
public class MainApp {
    public static void main(String[] args) {
        try {
            SpringApplication.run(MainApp.class, args);
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(MainApp.class);
            logger.info(e.getMessage());
            e.printStackTrace();
        }

    }
}
