package hongblinddate.backend.common.auth.login.handler;

import hongblinddate.backend.common.exception.ErrorCode;
import hongblinddate.backend.common.exception.LoginUnauthorizedException;
import hongblinddate.backend.common.util.ResponseWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;

import java.io.IOException;

@Slf4j
@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) {

		Throwable ex = LoginUnauthorizedException.EXCEPTION;
		HttpStatusCode statusCode = ErrorCode.LOGIN_UNAUTHORIZED.getHttpStatus();
		String message = ErrorCode.LOGIN_UNAUTHORIZED.getMessage();
		ErrorResponse errorResponse = ErrorResponse.create(ex, statusCode, message);

		ResponseWriter.write(response, errorResponse);
		log.info("로그인에 실패했습니다. 메시지 : {}", message
		);
	}
}
