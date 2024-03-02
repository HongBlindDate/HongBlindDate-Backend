package hongblinddate.backend.common.exception;

public class AccountNotFoundException extends CustomException {

	public static final CustomException EXCEPTION = new AccountNotFoundException();

	public AccountNotFoundException() {
		super(ErrorCode.AccountNotFoundException);
	}
}
