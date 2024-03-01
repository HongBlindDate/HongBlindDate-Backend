package hongblinddate.backend.common.exception;

public class AccountDuplicationException extends CustomException {

	public static final CustomException EXCEPTION = new AccountDuplicationException();

	public AccountDuplicationException() {
		super(ErrorCode.AccountDuplicationException);
	}
}
