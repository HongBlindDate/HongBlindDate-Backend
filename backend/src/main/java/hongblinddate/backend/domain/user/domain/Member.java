package hongblinddate.backend.domain.user.domain;

import hongblinddate.backend.domain.user.dto.request.JoinRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String account;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    private String nickName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Grade grade;

    private String refreshToken; // 리프레시 토큰

    // 비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    private Member(String account, String password, String email, String nickName, Grade grade) {
        this.account = account;
        this.password = password;
        this.email = email;
        this.nickName = nickName;
        this.grade = grade;
    }

    public static Member create(JoinRequest joinRequest, Grade grade) {
        return new Member(joinRequest.getAccount(),
                joinRequest.getPassword(),
                joinRequest.getEmail(),
                joinRequest.getNickName(),
                grade);
    }

}

