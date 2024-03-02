package hongblinddate.backend.common.exception.handler;

import hongblinddate.backend.common.exception.CustomException;
import hongblinddate.backend.dto.ErrorResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(CustomException.class)
	protected ErrorResponse handleCustomException(CustomException e) {
		return ErrorResponse.of(e.getErrorCode());
	}
}
