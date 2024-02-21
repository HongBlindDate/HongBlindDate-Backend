package hongblinddate.backend.common.exception;

public class MemberNotFoundException extends CustomException{

    public static final CustomException EXCEPTION = new MemberNotFoundException();

    public MemberNotFoundException() {
        super(ErrorCode.MemberNotFoundException);
    }
}
