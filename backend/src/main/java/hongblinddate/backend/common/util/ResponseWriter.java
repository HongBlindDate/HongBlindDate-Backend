package hongblinddate.backend.common.util;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.http.HttpServletResponse;

public class ResponseWriter {

	private static final String CHARACTER_ENCODING = "utf-8";
	private static final String CONTENT_TYPE = "application/json";

	public  static <T> void write(HttpServletResponse response, T data) {
		response.setHeader("content-type", CONTENT_TYPE);
		response.setCharacterEncoding(CHARACTER_ENCODING);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		try {
			String result = objectMapper.writeValueAsString(data);
			response.getWriter().write(result);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
