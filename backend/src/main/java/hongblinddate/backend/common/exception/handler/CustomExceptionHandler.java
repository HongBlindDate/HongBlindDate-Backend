package hongblinddate.backend.common.exception.handler;

import hongblinddate.backend.common.exception.CustomException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {

		ErrorResponse errorResponse = ErrorResponse.create(e, e.getErrorCode().getHttpStatus(),
			e.getErrorCode().getMessage());
		return new ResponseEntity<>(errorResponse, e.getErrorCode().getHttpStatus());
	}
}
