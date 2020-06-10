package com.cocus.flight.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocus.flight.dto.FlightFilterDTO;
import com.cocus.flight.dto.FlightInfoDTO;
import com.cocus.flight.services.FlightService;

@RestController
@RequestMapping("api/v1/flight")
public class FlightController {
	
	@Autowired
	private FlightService flightService;
	
//	@GetMapping
//	public ResponseEntity<List<FlightInfoDTO>> getAll() throws URISyntaxException, IOException, InterruptedException, JSONException {
//		return ResponseEntity.ok(flightService.getAll());
//	}

	@PostMapping("/avg")
	public ResponseEntity<List<FlightInfoDTO>> filter(@RequestBody FlightFilterDTO filter) throws Exception {
		List<FlightInfoDTO> result = new ArrayList<>();
		try {
			result = flightService.filter(filter);	
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return ResponseEntity.ok(result);
	}
	
	@DeleteMapping
	public ResponseEntity<Void> delete() {
		return ResponseEntity.noContent().build();
	}
}
