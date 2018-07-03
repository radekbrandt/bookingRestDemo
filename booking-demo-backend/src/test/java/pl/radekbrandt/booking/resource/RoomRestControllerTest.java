package pl.radekbrandt.booking.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import pl.radekbrandt.booking.entity.Reservation;
import pl.radekbrandt.booking.entity.Room;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RoomRestController.class, secure = false)
public class RoomRestControllerTest extends AbstractRestControllerTest {

	@Test
	public void shouldAddReservation() throws Exception {
		// given
		Reservation reservation = new Reservation(null, BEYOND_BEGIN, BEYOND_END);
		Room room = new Room("Test Room 1");
		room.setId(1l);
		Optional<Room> roomOptional = Optional.of(room);
		List<Room> roomList = Arrays.asList(room);
		Mockito.when(roomRepository.findById(Mockito.anyLong())).thenReturn(roomOptional);
		Mockito.when(roomRepository.findFreeRooms(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
				.thenReturn(roomList);
		Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(reservation);

		// when
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/room/1/reservation")
				.accept(MediaType.APPLICATION_JSON).content(toJson(reservation))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		// then
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertNotNull(getValueFromJson(response.getContentAsString(), "number"));
	}

	@Test
	public void shouldRoomNotFound() throws Exception {
		// given

		// when
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/room/9999/reservation")
				.accept(MediaType.APPLICATION_JSON).content(toJson(new Reservation(null, null, null)))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		// then
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	public void shouldRoomNotAvailable() throws Exception {
		// given
		Reservation reservation = new Reservation(null, BEGIN, END);
		Room room = new Room("Test Room 1");
		room.setId(1l);
		Room room2 = new Room("Test Room 2");
		room2.setId(2l);
		Optional<Room> roomOptional = Optional.of(room);
		Mockito.when(roomRepository.findById(Mockito.anyLong())).thenReturn(roomOptional);
		Mockito.when(roomRepository.findFreeRooms(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
				.thenReturn(Arrays.asList(room2));
		Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(reservation);

		// when
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/room/1/reservation")
				.accept(MediaType.APPLICATION_JSON).content(toJson(new Reservation(null, MIDDLE_BEGIN, MIDDLE_END)))
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		// then
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}
}
