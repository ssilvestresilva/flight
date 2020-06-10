package com.cocus.flight;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cocus.flight.dto.FlightFilterDTO;
import com.cocus.flight.dto.FlightInfoDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FlightControllerTest {
	private static final String BASE = "api/v1/flight";
	private static final String URL = "/".concat(BASE);
	private static final String AVG = URL.concat("/avg");
	private static final String PORTO = "OPO";
	private static final String LISBOA = "LIS";

	@Autowired
    private TestRestTemplate restTemplate;
	
	@Test
	void testFilter() throws Exception {
		
		final FlightFilterDTO in = new FlightFilterDTO();
		LocalDate ldt = LocalDate.now();
		in.setFlyFrom(PORTO);
		in.setFlyTo(LISBOA);
		in.setDateFrom(ldt.plusDays(10).toString());
		in.setDateTo(ldt.plusDays(20).toString());
		ResponseEntity<FlightInfoDTO[]> res = restTemplate.postForEntity(AVG, in, FlightInfoDTO[].class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	void testInvalidDatesFilter() throws Exception {
		
		final FlightFilterDTO in = new FlightFilterDTO();
		LocalDate ldt = LocalDate.now();
		in.setFlyFrom(PORTO);
		in.setFlyTo(LISBOA);
		in.setDateFrom(ldt.plusDays(20).toString());
		in.setDateTo(ldt.plusDays(10).toString());
		ResponseEntity<Void> res = restTemplate.postForEntity(AVG, in, Void.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
