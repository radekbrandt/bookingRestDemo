package pl.radekbrandt.booking.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pl.radekbrandt.booking.client.menu.ConsoleSupport;

@SpringBootApplication
public class BookingDemoClient implements CommandLineRunner {
	
	@Autowired
	ConsoleSupport cs;

	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(BookingDemoClient.class);
		app.setBannerMode(Mode.OFF);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		cs.startMenu();
	}
}