package pl.radekbrandt.booking.resource;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.radekbrandt.booking.entity.Reservation;
import pl.radekbrandt.booking.entity.ReservationCancelOnly;
import pl.radekbrandt.booking.exception.ReservationNotFoundException;
import pl.radekbrandt.booking.persistent.ReservationRepository;
import pl.radekbrandt.booking.persistent.RoomRepository;

/**
 * REST controller for handling operation on Reservation.
 * @author Radek
 *
 */
@RestController
@RequestMapping("/reservation/{number}")
public class ReservationRestController {

	private final ReservationRepository reservationRepository;

	@Autowired
	ReservationRestController(RoomRepository roomRepository, ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	@PatchMapping()
	public ResponseEntity<?> partialUpdateCancel(@RequestBody ReservationCancelOnly partialUpdate,
			@PathVariable String number) {

		Optional<Reservation> reservation = reservationRepository.findByNumber(number);
		if (reservation.isPresent()) {
			reservation.get().setCancelReason(partialUpdate.getCancelReason());
			reservationRepository.save(reservation.get());
		} else {
			throw new ReservationNotFoundException(number);
		}
		return ResponseEntity.ok(reservation.get());
	}
}
