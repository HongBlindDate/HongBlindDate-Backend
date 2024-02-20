package hongblinddate.backend.domain.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String account;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotBlank
    private String nickName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Grade grade;

    private User(String account, String email, String password, String nickName, Grade grade) {
        this.account = account;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.grade = grade;
    }

}

