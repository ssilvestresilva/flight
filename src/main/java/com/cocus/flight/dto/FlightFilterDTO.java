package com.cocus.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightFilterDTO {

	private String flyFrom;
	private String flyTo;
	private String dateFrom;
	private String dateTo;
}
