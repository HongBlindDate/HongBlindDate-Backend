package hongblinddate.backend.common.exception;

public class EmailNotFoundException extends CustomException {

	public static final CustomException EXCEPTION = new EmailNotFoundException();

	public EmailNotFoundException() {
		super(ErrorCode.EmailNotFoundException);
	}
}
