package pl.radekbrandt.booking.persistent;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import pl.radekbrandt.booking.entity.CancellationReason;
import pl.radekbrandt.booking.entity.Reservation;
import pl.radekbrandt.booking.entity.Room;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoomRepositoryTest {

	private static final LocalDate BEGIN = LocalDate.of(2019, 01, 01);
	private static final LocalDate END = LocalDate.of(2019, 03, 01);
	private static final LocalDate MIDDLE_BEGIN = LocalDate.of(2019, 02, 01);
	private static final LocalDate MIDDLE_END = LocalDate.of(2019, 02, 15);
	private static final LocalDate BEYOND_BEGIN = LocalDate.of(2022, 01, 01);
	private static final LocalDate BEYOND_END = LocalDate.of(2022, 02, 01);

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private RoomRepository roomRepository;

	@Before
	public void setup() {
		// given
		roomRepository.deleteAllInBatch();
		entityManager.persist(new Room("Test Room 1"));
		entityManager.persist(new Room("Test Room 2"));
		entityManager.persist(new Room("Test Room 3"));
		entityManager.persist(new Room("Test Room 4"));
		entityManager.flush();
	}

	@Test
	public void shouldReturnFreeRoomsWhenReservedThatTime() {
		// when
		Optional<Room> room1 = roomRepository.findByRoomName("Test Room 1");
		Optional<Room> room2 = roomRepository.findByRoomName("Test Room 2");

		entityManager.persist(new Reservation(room1.get(), BEGIN, END));
		entityManager.persist(new Reservation(room2.get(), MIDDLE_BEGIN, MIDDLE_END));
		entityManager.flush();
		List<Room> freeRooms = roomRepository.findFreeRooms(BEGIN, END);

		// then
		assertThat(freeRooms.size()).isEqualTo(2);
		assertThat(freeRooms.get(0).getRoomName()).isEqualTo("Test Room 3");
		assertThat(freeRooms.get(1).getRoomName()).isEqualTo("Test Room 4");
	}

	@Test
	public void shouldReturnFreeRoomsWhenReservedOtherTime() {
		// when
		Optional<Room> room1 = roomRepository.findByRoomName("Test Room 1");
		Optional<Room> room2 = roomRepository.findByRoomName("Test Room 2");
		Optional<Room> room3 = roomRepository.findByRoomName("Test Room 3");

		entityManager.persist(new Reservation(room1.get(), BEGIN, END));
		entityManager.persist(new Reservation(room2.get(), MIDDLE_BEGIN, MIDDLE_END));
		entityManager.persist(new Reservation(room3.get(), BEYOND_BEGIN, BEYOND_END));
		entityManager.flush();
		List<Room> freeRooms = roomRepository.findFreeRooms(BEGIN, END);

		// then
		assertThat(freeRooms.size()).isEqualTo(2);
		assertThat(freeRooms.get(0).getRoomName()).isEqualTo("Test Room 3");
		assertThat(freeRooms.get(1).getRoomName()).isEqualTo("Test Room 4");
	}

	@Test
	public void shouldReturnFreeRoomsWhenReservationCancelled() {
		// when
		Optional<Room> room1 = roomRepository.findByRoomName("Test Room 1");
		Optional<Room> room2 = roomRepository.findByRoomName("Test Room 2");

		Reservation reservation = new Reservation(room1.get(), BEGIN, END);
		reservation.setCancelReason(CancellationReason.BY_GUEST);

		entityManager.persist(reservation);
		entityManager.persist(new Reservation(room2.get(), MIDDLE_BEGIN, MIDDLE_END));
		entityManager.flush();
		List<Room> freeRooms = roomRepository.findFreeRooms(BEGIN, END);

		// then
		assertThat(freeRooms.size()).isEqualTo(3);
		assertThat(freeRooms.get(0).getRoomName()).isEqualTo("Test Room 1");
		assertThat(freeRooms.get(1).getRoomName()).isEqualTo("Test Room 3");
		assertThat(freeRooms.get(2).getRoomName()).isEqualTo("Test Room 4");
	}
}
