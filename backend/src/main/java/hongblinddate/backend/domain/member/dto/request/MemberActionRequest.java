package hongblinddate.backend.domain.member.dto.request;

import lombok.Getter;

@Getter
public class MemberActionRequest {
    private String action; // 수행할 액션 (APPROVE or BAN)
    private Integer days; // 밴할 경우, 밴 기간 설정 (-1이면 10년)
}
