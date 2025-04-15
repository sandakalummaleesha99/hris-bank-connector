package hris_bank_adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HrisBankAdapterApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrisBankAdapterApplication.class, args);
    }

}
