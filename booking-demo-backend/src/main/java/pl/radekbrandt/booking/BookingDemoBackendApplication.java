package pl.radekbrandt.booking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import pl.radekbrandt.booking.entity.Room;
import pl.radekbrandt.booking.persistent.ReservationRepository;
import pl.radekbrandt.booking.persistent.RoomRepository;

@SpringBootApplication
@EnableScheduling
public class BookingDemoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingDemoBackendApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(RoomRepository roomRepository,
			ReservationRepository reservationRepository) {
		return (evt) -> { 
			roomRepository.save(new Room("Apartment 1"));
			roomRepository.save(new Room("Apartment 2"));
			roomRepository.save(new Room("Apartment VIP"));
			roomRepository.save(new Room("Penthouse"));
		};
	}
}
