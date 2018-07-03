package pl.radekbrandt.booking.persistent;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.radekbrandt.booking.entity.Reservation;

/**
 * Repository for CRUD operations on {@link Reservation} entity.
 * @author Radek
 *
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	Optional<Reservation> findByNumber(String number);
	
	/**
	 * Returns a list of reservation that begins the given day.
	 * @param bookingBegin
	 * @return
	 */
	Optional<List<Reservation>> findByBookingBegin(LocalDate bookingBegin);

}
