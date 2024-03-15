package hongblinddate.backend.common.exception.handler;

import hongblinddate.backend.common.dto.CustomResponse;
import hongblinddate.backend.common.exception.CustomException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(CustomException.class)
	protected CustomResponse<?> handleCustomException(CustomException e) {
		return CustomResponse.of(e.getErrorCode().getHttpStatus().value(), null,
			e.getMessage());
	}
}
