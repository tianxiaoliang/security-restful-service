package com.cloud.service.wsclient;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.cloud.service.requests.RegisterFarmResultRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class FarmWSClient {
	private Logger logger = Logger.getLogger(FarmWSClient.class);

	private static final boolean DEBUG = false;

	private String endpoint;
	private String apiKey;
	private String apiSecret;
	private OAuthService oAuthService;
	static Token accessToken = new Token(
			"rtcIe1nbtwhG8fjfSPnUiIM4hWegrWGNGeRokHdI", "");
	private static String URI = "/ws/farm";

	public FarmWSClient(String endpoint, String apiKey, String apiSecret) {
		if (endpoint.endsWith("/"))
			endpoint = endpoint.substring(0, endpoint.length() - 1);
		this.endpoint = endpoint;
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		if (DEBUG) {
			this.oAuthService = new ServiceBuilder()
					.provider(DummyAPIProvider.class).debug()
					.apiKey(this.apiKey).apiSecret(this.apiSecret).build();
		} else {
			this.oAuthService = new ServiceBuilder()
					.provider(DummyAPIProvider.class).apiKey(this.apiKey)
					.apiSecret(this.apiSecret).build();
		}
	}

	public String getFarms(String endpoint) {

		OAuthRequest request = new OAuthRequest(Verb.GET, this.endpoint + URI);
		request.addQuerystringParameter("scalr_endpoint", endpoint);

		String jsonResponse = this.sendRequest(request);

		return jsonResponse;
	}

	public String register(RegisterFarmResultRequest farm) {

		OAuthRequest request = new OAuthRequest(Verb.POST, this.endpoint + URI
				+ "/register");
		request.addPayload(new Gson().toJson(farm));
		System.out.println(request.getBodyContents());
		String jsonResponse = this.sendRequest(request);

		System.out.println(jsonResponse);

		return jsonResponse;
	}

	public String sendRequest(OAuthRequest request) {
		request.addHeader("Content-Type", "application/json");

		Map<String, String> headers = request.getHeaders();

		if (DEBUG)
			logger.debug("Request Headers:");
		if (DEBUG)
			logger.debug("---------------------------------------");
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			if (DEBUG)
				logger.debug("    " + entry.getKey() + "=" + entry.getValue());
		}

		Token accessToken = new Token("", "");
		oAuthService.signRequest(accessToken, request);
		Response response = request.send();

		int code = response.getCode();
		logger.info("code=" + code);
		// String strMsg = response.getMessage();
		String jsonResponse = response.getBody();
		logger.info("jsonResponse=" + jsonResponse);

		// print response json pretty
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(jsonResponse);
		String prettyJsonString = gson.toJson(je);
		if (DEBUG)
			logger.debug("\n" + prettyJsonString);

		return jsonResponse;
	}

	private static String formatJSON(String jsonStr) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(jsonStr);
		String prettyJsonString = gson.toJson(je);
		return prettyJsonString;
	}
}
