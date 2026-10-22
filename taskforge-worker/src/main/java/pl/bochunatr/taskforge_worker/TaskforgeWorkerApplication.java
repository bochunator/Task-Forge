package pl.bochunatr.taskforge_worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TaskforgeWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskforgeWorkerApplication.class, args);
	}

}
