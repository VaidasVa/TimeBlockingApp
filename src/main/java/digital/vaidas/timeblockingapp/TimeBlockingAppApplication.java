package digital.vaidas.timeblockingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TimeBlockingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeBlockingAppApplication.class, args);
    }

}
