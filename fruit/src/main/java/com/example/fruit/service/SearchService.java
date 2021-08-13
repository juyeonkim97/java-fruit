package com.example.fruit.service;

import java.util.Map;

import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class SearchService {

	// get fruit price
	public String getFruitPrice(String keyword) {
		try {
			JsonParser parser = new BasicJsonParser();

			// get access token
			RestTemplate template = new RestTemplate(); // 외부 API 호출
			ResponseEntity<String> res = template.getForEntity("http://fruit.api.postype.net/token", String.class);
			Map<String, Object> resMap = parser.parseMap(res.getBody());
			String accessToken = (String) resMap.get("accessToken");

			// set url
			String url = "http://fruit.api.postype.net/product?name=" + keyword;

			// set header
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", accessToken);
			HttpEntity request = new HttpEntity(headers);

			// call api
			res = template.exchange(url, HttpMethod.GET, request, String.class);
			resMap = parser.parseMap(res.getBody());
			
			return resMap.get("price").toString();
		} catch (HttpStatusCodeException e) {
			return "fail";
		}
	}

	// get vegetable price
	public String getVegetablePrice(String keyword) {
		try {
			JsonParser parser = new BasicJsonParser();
			RestTemplate template = new RestTemplate(); // 외부 API 호출

			// get access token
			ResponseEntity<String> res = template.getForEntity("http://vegetable.api.postype.net/token", String.class);
			String resCookie = res.getHeaders().getFirst("Set-Cookie");
			String accessToken = resCookie.replace("Authorization=", "").replace("; Path=/", ""); //access token만 추출

			// set url
			String url = "http://vegetable.api.postype.net/item?name=" + keyword;

			// set header
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", accessToken);
			HttpEntity request = new HttpEntity(headers);

			// call api
			res = template.exchange(url, HttpMethod.GET, request, String.class);
			Map<String, Object> resMap = parser.parseMap(res.getBody());
			
			return resMap.get("price").toString();
		} catch (HttpStatusCodeException e) {
			return "fail";
		}
	}

}
