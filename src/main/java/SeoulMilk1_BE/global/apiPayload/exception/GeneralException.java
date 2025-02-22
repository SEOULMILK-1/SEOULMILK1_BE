package SeoulMilk1_BE.global.apiPayload.exception;

import SeoulMilk1_BE.global.apiPayload.BaseErrorCode;
import SeoulMilk1_BE.global.apiPayload.ErrorReasonDTO;
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