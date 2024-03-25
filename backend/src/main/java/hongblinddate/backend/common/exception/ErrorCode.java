package hongblinddate.backend.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum ErrorCode {
	// 401
	ACCESS_TOKEN_UNAUTHORIZED(UNAUTHORIZED, "ACCESS_TOKEN_UNAUTHORIZED_401_1", "액세스 토큰 인증을 실패하였습니다."),
	REFRESH_TOKEN_UNAUTHORIZED(UNAUTHORIZED, "REFRESH_TOKEN_UNAUTHORIZED_401_1", "리프레쉬 토큰 인증을 실패하였습니다."),
	LOGIN_UNAUTHORIZED(UNAUTHORIZED, "LOGIN_UNAUTHORIZED_401_1", "로그인에 실패하였습니다."),
	// 403
	ACCESS_FORBIDDEN(FORBIDDEN, "FORBIDDEN_403_1", "접근 권한이 없습니다."),
	ACCESS_DENIED(FORBIDDEN, "FORBIDDEN_403_2", "접근 권한이 없습니다."),

	AccountNotFoundException(NOT_FOUND, "ACCOUNT_NOT_FOUND_001", "계정이 존재하지 않습니다."),
	AccountDuplicationException(CONFLICT, "ACCOUNT_DUPLICATION_001", "계정이 이미 존재합니다."),
	EmailNotFoundException(NOT_FOUND, "EMAIL_NOT_FOUND_001", "이메일이 존재하지 않습니다."),
	MemberNotFoundException(NOT_FOUND, "MEMBER_NOT_FOUND_001", "해당하는 멤버가 없습니다."),
	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
