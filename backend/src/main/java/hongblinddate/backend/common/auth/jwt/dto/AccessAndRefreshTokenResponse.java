package hongblinddate.backend.common.auth.jwt.dto;

import lombok.Getter;

@Getter
public class AccessAndRefreshTokenResponse {
	private final String accessToken;
	private final String refreshToken;

	private AccessAndRefreshTokenResponse(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public static AccessAndRefreshTokenResponse create(String accessToken, String refreshToken) {
		return new AccessAndRefreshTokenResponse(accessToken, refreshToken);
	}

}
