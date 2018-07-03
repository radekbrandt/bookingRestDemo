package pl.radekbrandt.booking.persistent;

import java.time.LocalDate;
import java.util.List;

import pl.radekbrandt.booking.entity.Room;

/**
 * Custom repository for {@link Room}  entity.
 * @author Radek
 *
 */
public interface RoomCustomRepository {
	
	/**
	 * Return a list of available rooms at given period. 
	 *
	 * @param begin
	 * @param end
	 * @return
	 */
	List<Room> findFreeRooms(LocalDate begin, LocalDate end);
}
