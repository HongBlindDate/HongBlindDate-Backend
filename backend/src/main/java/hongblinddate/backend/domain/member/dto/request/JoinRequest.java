package hongblinddate.backend.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class JoinRequest {

    @NotEmpty(message = "계정은 필수항목입니다.")
    private String account;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String email;

    @NotEmpty(message = "닉네임은 필수항목입니다.")
    private String nickName;
}
