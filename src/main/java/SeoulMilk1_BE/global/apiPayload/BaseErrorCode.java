package SeoulMilk1_BE.global.apiPayload;

public interface BaseErrorCode {
    
    ErrorReasonDTO getReason();
    
    ErrorReasonDTO getReasonHttpStatus();
}
