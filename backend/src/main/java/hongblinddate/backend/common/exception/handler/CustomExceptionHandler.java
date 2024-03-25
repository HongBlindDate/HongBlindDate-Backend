package hongblinddate.backend.common.exception.handler;

import hongblinddate.backend.common.dto.CustomResponse;
import hongblinddate.backend.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public CustomResponse<?> handleCustomException(CustomException e) {
		return CustomResponse.error(e);
	}

	@ExceptionHandler(Exception.class)
	public CustomResponse<?> handleGlobalException(Exception e) {
		return CustomResponse.ok();
	}

}
