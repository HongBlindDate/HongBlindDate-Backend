package hongblinddate.backend.domain.member.controller;

import hongblinddate.backend.domain.member.domain.Suspension;
import hongblinddate.backend.domain.member.dto.request.MemberActionRequest;
import hongblinddate.backend.domain.member.service.SuspensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/members")
public class SuspensionController {

    private final SuspensionService suspensionService;

    @Autowired
    public SuspensionController(SuspensionService suspensionService) {
        this.suspensionService = suspensionService;
    }


    @PostMapping("/{memberId}/action") // 회원 정지 API
    public ResponseEntity<?> suspendUser(@PathVariable Long memberId, @RequestBody MemberActionRequest suspensionRequest) {
        Suspension suspension = suspensionService.suspendUser(memberId, suspensionRequest.getDays());
        return ResponseEntity.ok().build();
    }
}
