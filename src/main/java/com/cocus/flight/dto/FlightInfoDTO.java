package com.cocus.flight.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightInfoDTO {

	private String departure;
	private String arrival;
	private String currency;
	private BigDecimal priceAvarage;
	private Map<String, Double> bagsPrice;
	private LocalDateTime dateFrom;
	private LocalDateTime dateTo;
}
