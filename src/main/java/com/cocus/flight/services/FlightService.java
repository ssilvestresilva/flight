package com.cocus.flight.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.cocus.flight.client.FlightClient;
import com.cocus.flight.dto.FlightFilterDTO;
import com.cocus.flight.dto.FlightInfoDTO;

@Service
public class FlightService {
	
	@Autowired
	private FlightClient flightClient;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FlightService.class);
	private static final String CONVERSION = "conversion";
	private static final String EUR = "EUR";
	private static final String BAGS_PRICE = "bags_price";
	private static final String FLY_FROM = "flyFrom";
	private static final String FLY_TO = "flyTo";
	private static final String DATE_FROM = "dTimeUTC";
	private static final String DATE_TO = "aTimeUTC";
	private static final String AIRLINES_STR = "airlines";
	private static final String[] AIRLINES = {"TP", "FR"};
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	
	public List<FlightInfoDTO> filter(FlightFilterDTO filter) throws URISyntaxException, IOException, InterruptedException, JSONException {
        List<FlightInfoDTO> result = new ArrayList<>();
        List<String> airLines = List.of(AIRLINES);
        flightClient.getAll(filterUri(filter)).forEach(r -> {
			try {
				if (convertJson(r.getJSONArray(AIRLINES_STR)).stream().anyMatch(a -> airLines.contains(a))) {
					result.add(loadFlights(r));					
				}
			} catch (JSONException e) {
				LOGGER.error("Error while parsing the object: ", r);
			}
		});
        LOGGER.info("FlightService.getAll - Found {} elements.", result.size());
        return result;
    }
	
	private List<String> convertJson(JSONArray jsonArray) throws JSONException {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < jsonArray.length(); i++) {
			list.add(jsonArray.getString(i));
		}
		return list;
	}
	
	public String filterUri(FlightFilterDTO filter) throws IOException {
		StringBuilder result = new StringBuilder();
		if (ObjectUtils.isEmpty(filter)) {
			throw new IOException("It's necessary to inform filters.");
		}

		String flyFrom = filter.getFlyFrom();
		String flyTo = filter.getFlyTo();
		if (StringUtils.isEmpty(flyFrom) || StringUtils.isEmpty(flyTo)) {
			throw new IOException("flyTo and flyFrom can't be empty");
		}
		result.append("?flyFrom=" + flyFrom).append("&to=" + flyTo);
	
		String dateFrom = filter.getDateFrom();
		if (!StringUtils.isEmpty(filter.getDateFrom())) {
			String dateTo = filter.getDateTo();
			if (StringUtils.isEmpty(dateTo)) {
				throw new IOException("dateTo can't be empty when send dateFrom");
			}
			result.append(formatDate(dateFrom, dateTo));
		}
		
		return result.append("&partner=picky").toString();
	}
	
	private String formatDate(String f, String t) throws IOException {
		StringBuilder r = new StringBuilder();
		String strFrom = String.valueOf(f.replace("/", "-"));
		String strTo = String.valueOf(t.replace("/", "-"));
		LocalDate localFromDate = LocalDate.parse(strFrom);
		LocalDate localToDate = LocalDate.parse(strTo);
		
		if (localFromDate.isAfter(localToDate)) {
			throw new IOException("dateFrom can't be after dateTo");
		}
		
		r.append("&dateFrom=" + localFromDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT))).append("&dateTo=" + localToDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
		return r.toString();
	}

	private FlightInfoDTO loadFlights(JSONObject r) throws JSONException {
		String currency = String.valueOf(r.get(CONVERSION));
		BigDecimal price = null;
		if (!StringUtils.isEmpty(currency) && currency.contains(EUR)) {
			JSONObject convWrapper = (JSONObject) r.get(CONVERSION);
			price = BigDecimal.valueOf(convWrapper.getLong(EUR));
			currency = EUR;
		}
		
		return FlightInfoDTO.builder()
				.departure(String.valueOf(r.get(FLY_FROM)))
				.arrival(String.valueOf(r.get(FLY_TO)))
				.currency(currency)
				.priceAvarage(price)
				.bagsPrice(convertBagToMap((JSONObject) r.get(BAGS_PRICE)))
				.dateFrom(getDate(r.get(DATE_FROM)))
				.dateTo(getDate(r.get(DATE_TO)))
				.build();
	}

	private LocalDateTime getDate(Object dt) {
		Timestamp conv = new Timestamp(Long.parseLong(String.valueOf(dt)) * 1000);
		return conv.toLocalDateTime();
	}

	@SuppressWarnings("unchecked")
	private Map<String, Double> convertBagToMap(JSONObject json) throws JSONException {
		Iterator<String> keys = json.keys();
		Map<String, Double> r = new HashMap<>();
		while(keys.hasNext()) {
		    String key = keys.next();
	    	r.put(key, json.getDouble(key));
		}
		return r;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
    public void delete() {
    	LOGGER.info("FlightService.delete - Flights deleted.");
    }
	
}
