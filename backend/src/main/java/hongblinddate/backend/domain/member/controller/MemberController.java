package hongblinddate.backend.domain.member.controller;

import java.util.Objects;

import hongblinddate.backend.domain.member.dto.request.JoinRequest;
import hongblinddate.backend.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/join")
	public ResponseEntity<Objects> join(@RequestBody @Valid JoinRequest joinRequest) {
		memberService.create(joinRequest);

		return ResponseEntity.ok().build();
	}
}
