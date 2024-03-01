package hongblinddate.backend.dto;

import hongblinddate.backend.common.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import javax.lang.model.type.ErrorType;

@Getter
public class ErrorResponse extends BaseResponse {
	private final String message;

	@Builder
	private ErrorResponse(HttpStatusCode httpStatusCode, String message) {
		super(false, httpStatusCode);
		this.message = message;
	}

	public static ErrorResponse of(ErrorCode errorCode) {
		return ErrorResponse.builder()
			.httpStatusCode(errorCode.getHttpStatus())
			.message(errorCode.getMessage())
			.build();
	}

	public static ErrorResponse of(HttpStatus status, String message) {
		return ErrorResponse.builder()
			.httpStatusCode(status)
			.message(message)
			.build();
	}
}
