package pl.radekbrandt.booking.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

import pl.radekbrandt.booking.entity.CancellationReason;
import pl.radekbrandt.booking.entity.Reservation;
import pl.radekbrandt.booking.entity.ReservationCancelOnly;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ReservationRestController.class, secure = false)
public class ReservationRestControllerTest extends AbstractRestControllerTest {

	@Test
	public void shouldCancelReservation() throws Exception {
		// given
		Reservation reservation = new Reservation(null, BEGIN, END);
		ReservationCancelOnly rco = new ReservationCancelOnly(CancellationReason.NOT_PAID);
		Optional<Reservation> reservationOptional = Optional.of(reservation);

		Mockito.when(reservationRepository.findByNumber(Mockito.anyString())).thenReturn(reservationOptional);
		Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(reservation);

		// when
		RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/reservation/XYZ12")
				.accept(MediaType.APPLICATION_JSON).content(toJson(rco)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		// then
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(CancellationReason.NOT_PAID.toString(),
				getValueFromJson(response.getContentAsString(), "cancelReason"));
		assertNotNull(
				getValueFromJson(response.getContentAsString(), "canceledAt"));
	}

}
