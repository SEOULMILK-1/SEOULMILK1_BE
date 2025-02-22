package SeoulMilk1_BE.global.aplPayload;

public interface BaseErrorCode {
    
    ErrorReasonDTO getReason();
    
    ErrorReasonDTO getReasonHttpStatus();
}
