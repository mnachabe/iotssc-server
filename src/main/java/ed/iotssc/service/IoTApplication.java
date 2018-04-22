package ed.iotssc.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IoTApplication {

	public static void main(String[] args) {
		SpringApplication.run(IoTApplication.class, args);
	}
}
