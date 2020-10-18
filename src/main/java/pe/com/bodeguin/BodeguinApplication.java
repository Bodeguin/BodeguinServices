package pe.com.bodeguin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableAdminServer
public class BodeguinApplication {

	public static void main(String[] args) {
		SpringApplication.run(BodeguinApplication.class, args);
	}

}
