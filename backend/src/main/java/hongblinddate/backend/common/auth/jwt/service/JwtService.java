package hongblinddate.backend.common.auth.jwt.service;

import hongblinddate.backend.common.auth.jwt.dto.AccessAndRefreshTokenResponse;
import hongblinddate.backend.common.properties.JwtProperties;
import hongblinddate.backend.common.util.ResponseWriter;
import hongblinddate.backend.domain.member.domain.Member;
import hongblinddate.backend.domain.member.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Service
@Getter
@Slf4j
public class JwtService {

	private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
	private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
	private static final String ACCOUNT_CLAIM = "account";
	private static final String GRADE_CLAIM = "grade";
	private static final String BEARER = "Bearer ";

	private final SecretKey secretKey;
	private final MemberRepository memberRepository;
	private final JwtProperties jwtProperties;

	public JwtService(MemberRepository memberRepository, JwtProperties jwtProperties) {
		this.secretKey = new SecretKeySpec(jwtProperties.getSecretKey()
			.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key()
			.build()
			.getAlgorithm());
		;
		this.memberRepository = memberRepository;
		this.jwtProperties = jwtProperties;
	}

	public String createAccessToken(String account, String grade) {
		Date now = new Date();
		return Jwts.builder()
			.subject(ACCESS_TOKEN_SUBJECT)
			.issuedAt(now)
			.expiration(new Date(now.getTime() + jwtProperties.getAccessTokenExpirationPeriod()))
			.issuer(jwtProperties.getIssuer())
			.claim(ACCOUNT_CLAIM, account)
			.claim(GRADE_CLAIM, grade)
			.signWith(secretKey)
			.compact();
	}

	public String createRefreshToken() {
		Date now = new Date();
		return Jwts.builder()
			.subject(REFRESH_TOKEN_SUBJECT)
			.expiration(new Date(now.getTime() + jwtProperties.getRefreshTokenExpirationPeriod()))
			.signWith(secretKey)
			.compact();
	}

	public void sendAccessToken(HttpServletResponse response, String accessToken) {
		response.setStatus(HttpServletResponse.SC_OK);

		response.setHeader(jwtProperties.getAccessHeader(), accessToken);
		log.info("재발급된 Access Token : {}", accessToken);
	}

	public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
		response.setStatus(HttpServletResponse.SC_OK);

		setAccessTokenHeader(response, accessToken);
		setRefreshTokenHeader(response, refreshToken);
		setAccessAndRefreshTokenBody(response, accessToken, refreshToken);
		log.info("Access Token, Refresh Token 헤더 설정 완료");
	}

	public Optional<String> extractRefreshToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(jwtProperties.getRefreshHeader()))
			.filter(refreshToken -> refreshToken.startsWith(BEARER))
			.map(refreshToken -> refreshToken.replace(BEARER, ""));
	}

	public Optional<String> extractAccessToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(jwtProperties.getAccessHeader()))
			.filter(refreshToken -> refreshToken.startsWith(BEARER))
			.map(refreshToken -> refreshToken.replace(BEARER, ""));
	}

	public String extractAccount(String accessToken) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(accessToken)
			.getPayload()
			.get(ACCOUNT_CLAIM, String.class);
	}

	public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
		response.setHeader(jwtProperties.getAccessHeader(), accessToken);
	}

	public void setAccessAndRefreshTokenBody(HttpServletResponse response, String accessToken, String refreshToken) {
		AccessAndRefreshTokenResponse accessAndRefreshTokenResponse = AccessAndRefreshTokenResponse.create(accessToken,
			refreshToken);
		ResponseEntity<AccessAndRefreshTokenResponse> dataResponse = ResponseEntity.ok(accessAndRefreshTokenResponse);

		ResponseWriter.write(response, dataResponse);
	}

	public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
		response.setHeader(jwtProperties.getRefreshHeader(), refreshToken);
	}

	@Transactional
	public void deleteRefreshToken(String account) {
		log.info("리프레시 토큰을 삭제합니다.");
		memberRepository.findByAccount(account)
			.ifPresent(Member::deleteRefreshToken);
	}

	public boolean isTokenValid(String token) {
		return Jwts.parser().verifyWith(secretKey).build()
			.parseSignedClaims(token).getPayload().getExpiration().before(new Date());
	}

}
