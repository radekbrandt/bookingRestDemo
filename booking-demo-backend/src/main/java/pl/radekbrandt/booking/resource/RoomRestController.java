package pl.radekbrandt.booking.resource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.radekbrandt.booking.entity.Reservation;
import pl.radekbrandt.booking.entity.Room;
import pl.radekbrandt.booking.exception.RoomNotAvailableException;
import pl.radekbrandt.booking.notification.RestNotificationSender;
import pl.radekbrandt.booking.persistent.ReservationRepository;
import pl.radekbrandt.booking.persistent.RoomRepository;

/**
 * REST controller for handling operation on Room.
 * 
 * @author Radek
 *
 */
@RestController
@RequestMapping("/room")
public class RoomRestController {

	private final RoomRepository roomRepository;
	private final ReservationRepository reservationRepository;
	
	private static final Logger log = LoggerFactory.getLogger(RestNotificationSender.class);

	@Autowired
	RoomRestController(RoomRepository roomRepository, ReservationRepository reservationRepository) {
		this.roomRepository = roomRepository;
		this.reservationRepository = reservationRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Collection<Room> readFreeRooms(@RequestParam Map<String, String> dates) {
		LocalDate begin = LocalDate.parse(dates.get("begin"), DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDate end = LocalDate.parse(dates.get("end"), DateTimeFormatter.ISO_LOCAL_DATE);
		return this.roomRepository.findFreeRooms(begin, end);
	}

	@PostMapping("/{roomId}/reservation")
	ResponseEntity<?> add(@PathVariable Long roomId, @RequestBody Reservation reservation) {
		validateRoomAvailibility(roomId, reservation);
		return this.roomRepository.findById(roomId).map(room -> {
			Reservation result = reservationRepository
					.save(new Reservation(room, reservation.getBookingBegin(), reservation.getBookingEnd()));
			return ResponseEntity.ok(result);
		}).orElse(ResponseEntity.noContent().build());
	}

	private void validateRoomAvailibility(Long roomId, Reservation reservation) {
		log.info("validation room availibility for room id " + roomId + " reservation " + reservation.toString());
		List<Room> freeRooms = roomRepository.findFreeRooms(reservation.getBookingBegin(), reservation.getBookingEnd());
		if (freeRooms.stream().noneMatch(r -> r.getId().equals(roomId))) {
			throw new RoomNotAvailableException(roomId);
		}

	}

}
