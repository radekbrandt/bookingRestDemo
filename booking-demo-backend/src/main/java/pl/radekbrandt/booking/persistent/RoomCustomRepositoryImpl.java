package pl.radekbrandt.booking.persistent;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.radekbrandt.booking.entity.Room;

/**
 * Basic implementation for {@link RoomCustomRepository}
 * @author Radek
 *
 */
@Repository
@Transactional(readOnly = true)
public class RoomCustomRepositoryImpl implements RoomCustomRepository {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Room> findFreeRooms(LocalDate begin, LocalDate end) {
		TypedQuery<Room> roomsQuery = entityManager.createQuery("SELECT ro FROM Room ro",
				Room.class);
		List<Room> rooms = roomsQuery.getResultList();
		
		TypedQuery<Long> resQuery = entityManager.createQuery(
				"SELECT ro.id FROM Room ro JOIN ro.reservations res "
				+ "WHERE res.room=ro "
				+ "AND res.canceledAt = NULL "
				+ "AND "
				+ "( :begin >= res.bookingBegin AND :begin < res.bookingEnd "
				+ "OR :end >= res.bookingBegin AND :begin < res.bookingEnd)",				
				Long.class);
		resQuery.setParameter("begin", begin);
		resQuery.setParameter("end", end);
		
		List<Long> reservedRooms = resQuery.getResultList();
		List<Room> freeRooms = rooms.stream().filter(r -> !reservedRooms.contains(r.getId())).collect(Collectors.toList());
		return freeRooms;
	}

}
