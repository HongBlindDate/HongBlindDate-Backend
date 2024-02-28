package hongblinddate.backend.domain.member.controller;

import hongblinddate.backend.domain.member.domain.Member;
import hongblinddate.backend.domain.member.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    // 승인 대기 중인 사용자 목록을 조회하는 API
    @GetMapping("/users/pending-approvals")
    public ResponseEntity<List<Member>> getPendingApplicants() {
        List<Member> pendingApplicants = adminService.getPendingApplicants();
        return ResponseEntity.ok(pendingApplicants);
    }

    // 사용자 프로필 승인/거절 처리 API
    @PatchMapping("/users/{userId}/action")
    public ResponseEntity<Void> approveOrRejectProfile(@PathVariable Long userId, @RequestParam boolean isApproved) {
        adminService.approveOrRejectProfile(userId, isApproved);
        return ResponseEntity.ok().build();
    }
}
