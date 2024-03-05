package hongblinddate.backend.common.exception;

public class LoginUnauthorizedException extends CustomException {

	public static final CustomException EXCEPTION = new LoginUnauthorizedException();

	public LoginUnauthorizedException() {
		super(ErrorCode.EmailNotFoundException);
	}
}
