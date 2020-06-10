package com.cocus.flight.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class FlightClient {
	
	@Value("${flight.api.addr}")
	private String addr;
	
	public List<JSONObject> getAll(String uri) throws URISyntaxException, IOException, InterruptedException, JSONException {
		HttpRequest request = HttpRequest.newBuilder()
				  .uri(new URI(addr.concat(uri)))
				  .timeout(Duration.ofSeconds(20))
				  .GET()
				  .build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
		
		if (response.statusCode() != 200) {
			throw new IOException();
		}
		
		JSONArray jsonArray = new JSONObject(response.body()).getJSONArray("data");
		
		List<JSONObject> r = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			r.add(jsonArray.getJSONObject(i));
		}
		
		return r;
	}
}
