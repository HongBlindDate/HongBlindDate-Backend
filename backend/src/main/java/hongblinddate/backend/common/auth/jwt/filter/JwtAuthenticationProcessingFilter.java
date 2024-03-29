package hongblinddate.backend.common.auth.jwt.filter;

import hongblinddate.backend.common.auth.jwt.service.JwtService;
import hongblinddate.backend.domain.member.domain.Member;
import hongblinddate.backend.domain.member.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

	private static final String NO_CHECK_URL = "/api/auth/login"; // "/login"으로 들어오는 요청은 Filter 작동 X

	private final JwtService jwtService;
	private final MemberRepository memberRepository;

	private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		if (request.getRequestURI().equals(NO_CHECK_URL)) {
			filterChain.doFilter(request, response);
			return;
		}

		String refreshToken = jwtService.extractRefreshToken(request)
			.filter(jwtService::isTokenValid)
			.orElse(null);

		if (refreshToken != null) {
			checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
			return; // RefreshToken을 보낸 경우에는 AccessToken을 재발급 하고 인증 처리는 하지 않게 하기위해 바로 return으로 필터 진행 막기
		}

		// RefreshToken이 없거나 유효하지 않다면, AccessToken을 검사하고 인증을 처리하는 로직 수행
		// AccessToken이 없거나 유효하지 않다면, 인증 객체가 담기지 않은 상태로 다음 필터로 넘어가기 때문에 403 에러 발생
		// AccessToken이 유효하다면, 인증 객체가 담긴 상태로 다음 필터로 넘어가기 때문에 인증 성공
		if (refreshToken == null) {
			checkAccessTokenAndAuthentication(request, response, filterChain);
		}
	}

	public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
		memberRepository.findByRefreshToken(refreshToken)
			.ifPresentOrElse(member -> {
				String reIssuedRefreshToken = reIssueRefreshToken(member);
				jwtService.sendAccessAndRefreshToken(response,
					jwtService.createAccessToken(member.getAccount(), member.getGrade().getDescription()),
					reIssuedRefreshToken);
			}, () -> {
				log.info("엑세스 토큰 재발급 실패");
			});
	}

	private String reIssueRefreshToken(Member member) {
		String reIssuedRefreshToken = jwtService.createRefreshToken();
		member.updateRefreshToken(reIssuedRefreshToken);
		memberRepository.saveAndFlush(member);
		return reIssuedRefreshToken;
	}

	public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		log.info("checkAccessTokenAndAuthentication() 호출");
		jwtService.extractAccessToken(request)
			.filter(jwtService::isTokenValid)
			.ifPresent(accessToken -> {
				String account = jwtService.extractAccount(accessToken);
				memberRepository.findByAccount(account)
					.ifPresent(this::saveAuthentication);
			});

		filterChain.doFilter(request, response);
	}

	public void saveAuthentication(Member myMember) {
		UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
			.username(myMember.getAccount())
			.password(myMember.getPassword())
			.roles(myMember.getGrade().getDescription())
			.build();

		Authentication authentication =
			new UsernamePasswordAuthenticationToken(userDetails, null,
				authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
