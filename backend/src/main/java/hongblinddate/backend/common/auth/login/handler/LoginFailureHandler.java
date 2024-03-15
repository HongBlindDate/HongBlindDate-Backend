package hongblinddate.backend.common.auth.login.handler;

import hongblinddate.backend.common.dto.CustomResponse;
import hongblinddate.backend.common.exception.LoginUnauthorizedException;
import hongblinddate.backend.common.util.ResponseWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) {

		CustomResponse<?> errorResponse = CustomResponse.error(LoginUnauthorizedException.EXCEPTION);

		ResponseWriter.write(response, errorResponse);
		log.info("로그인에 실패했습니다. 메시지 : {}", LoginUnauthorizedException.EXCEPTION.getMessage());
	}
}
