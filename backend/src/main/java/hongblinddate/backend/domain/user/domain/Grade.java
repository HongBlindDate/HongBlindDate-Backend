package hongblinddate.backend.domain.user.domain;

import lombok.Getter;

@Getter
public enum Grade {

    ADMINISTRATOR("ADMINISTRATOR", "관리자"),
    MEMBER("MEMBER", "멤버"),
    BANNED("BANNED", "강제 탈퇴"),
    APPLICANT("APPLICANT", "승인 대기자"),
    CHANGER("CHANGER", "변경자"),
    WITHDRAW("WITHDRAW", "자진 탈퇴"),
    ;

    private final String description;
    private final String name;

    Grade(String description, String name) {
        this.description = description;
        this.name = name;
    }
}
