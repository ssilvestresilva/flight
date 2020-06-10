package com.cocus.flight.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Entity
@Table(name = "flight")
public class Flight {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "departure")
	private String departure;
	
	@Column(name = "arrival")
	private String arrival;
	
	@Column(name = "currency")
	private String currency;
	
	@Column(name = "price_avarage")
	private BigDecimal priceAvarage;
	
	@Column(name = "bags_price")
	private String bagsPrice;
	
	@Column(name = "date_from")
	private LocalDateTime dateFrom;
	
	@Column(name = "date_to")
	private LocalDateTime dateTo;

}
