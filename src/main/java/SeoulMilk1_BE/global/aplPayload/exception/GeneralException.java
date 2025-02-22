package SeoulMilk1_BE.global.aplPayload.exception;

import SeoulMilk1_BE.global.aplPayload.BaseErrorCode;
import SeoulMilk1_BE.global.aplPayload.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {
    
    private BaseErrorCode code;
    
    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }
    
    public ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}