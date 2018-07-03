package pl.radekbrandt.booking.resource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.radekbrandt.booking.persistent.ReservationRepository;
import pl.radekbrandt.booking.persistent.RoomRepository;

public class AbstractRestControllerTest {

	protected static final LocalDate BEGIN = LocalDate.of(2019, 01, 01);
	protected static final LocalDate END = LocalDate.of(2019, 03, 01);
	protected static final LocalDate MIDDLE_BEGIN = LocalDate.of(2019, 02, 01);
	protected static final LocalDate MIDDLE_END = LocalDate.of(2019, 02, 15);
	protected static final LocalDate BEYOND_BEGIN = LocalDate.of(2022, 01, 01);
	protected static final LocalDate BEYOND_END = LocalDate.of(2022, 02, 01);

	protected HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
	//
	protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	protected RoomRepository roomRepository;

	@MockBean
	protected ReservationRepository reservationRepository;
	
	protected String toJson(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
	
	protected String getValueFromJson(String body, String field) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = null;
			jsonNode = mapper.readTree(body);
		String value = jsonNode.get(field).asText();
		return value;
	}
}
