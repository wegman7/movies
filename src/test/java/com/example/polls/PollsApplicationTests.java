package com.example.polls;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@SpringBootTest
class PollsApplicationTests {

	Logger logger = LoggerFactory.getLogger(PollsApplicationTests.class);

	@Test
	void contextLoads() {
	}

	@Test
	void run() throws IOException {
		String apiKey = "818f5c4c9ad4472a97cdc62c2f4c3a7a";
		String url = "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&query=tom+hanks";
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url(url)
				.build();

		try (Response response = client.newCall(request).execute()) {
			logger.info(response.body().string());
		}
	}
}
