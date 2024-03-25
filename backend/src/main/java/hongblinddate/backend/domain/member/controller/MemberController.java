package hongblinddate.backend.domain.member.controller;

import hongblinddate.backend.common.dto.CustomResponse;
import hongblinddate.backend.domain.member.dto.request.JoinRequest;
import hongblinddate.backend.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/join")
	public CustomResponse<?> join(@RequestBody @Valid JoinRequest joinRequest) {
		return memberService.create(joinRequest);
	}
}
