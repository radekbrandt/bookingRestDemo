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

import pl.radekbrandt.booking.entity.Reservation;
import pl.radekbrandt.booking.entity.Room;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReservationRepositoryTest {

	private static final LocalDate BEGIN_1 = LocalDate.of(2019, 01, 01);
	private static final LocalDate END_1 = LocalDate.of(2019, 01, 02);
	private static final LocalDate END_2 = LocalDate.of(2019, 02, 02);
	private static final LocalDate BEGIN_3 = LocalDate.of(2019, 03, 01);
	private static final LocalDate END_3 = LocalDate.of(2019, 03, 02);

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private RoomRepository roomRepository;

	@Before
	public void setup() {
		// given
		roomRepository.deleteAllInBatch();
		entityManager.persist(new Room("Test Room 1"));
		entityManager.flush();
	}

	@Test
	public void shouldFindReservationByDate() {
		// when
		Optional<Room> room1 = roomRepository.findByRoomName("Test Room 1");
		entityManager.persist(new Reservation(room1.get(), BEGIN_1, END_1));
		entityManager.persist(new Reservation(room1.get(), BEGIN_1, END_2));
		entityManager.persist(new Reservation(room1.get(), BEGIN_3, END_3));
		entityManager.flush();
		Optional<List<Reservation>> findByBookingBegin = reservationRepository.findByBookingBegin(BEGIN_1);

		// then
		assertThat(findByBookingBegin.get().size()).isEqualTo(2);
	}

}
