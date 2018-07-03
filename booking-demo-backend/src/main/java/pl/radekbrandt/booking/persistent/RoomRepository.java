package pl.radekbrandt.booking.persistent;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.radekbrandt.booking.entity.Room;

/**
 * Repository for CRUD operations on {@link Room} entity.
 * @author Radek
 *
 */
public interface RoomRepository extends JpaRepository<Room, Long>, RoomCustomRepository {

	Optional<Room> findByRoomName(String roomName);
}
