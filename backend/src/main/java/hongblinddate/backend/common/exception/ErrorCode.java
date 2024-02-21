package hongblinddate.backend.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum ErrorCode {
    AccountNotFoundException(HttpStatus.NOT_FOUND, "ACCOUNT_NOT_FOUND_001" , "계정이 존재하지 않습니다."),
    EmailNotFoundException(HttpStatus.NOT_FOUND, "EMAIL_NOT_FOUND_001" , "이메일이 존재하지 않습니다."),
    MemberNotFoundException(HttpStatus.NOT_FOUND, "MEMBER_NOT_FOUND_001", "해당하는 멤버가 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
