package SeoulMilk1_BE.global.apiPayload.exception;

import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import SeoulMilk1_BE.global.apiPayload.ErrorReasonDTO;
import SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus;
import SeoulMilk1_BE.nts_tax.exception.NtsTaxNotFoundException;
import SeoulMilk1_BE.nts_tax.exception.OcrApiException;
import SeoulMilk1_BE.user.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler
    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("ConstraintViolationException 추출 도중 에러 발생"));
        
        return handleExceptionInternalConstraint(e, ErrorStatus.valueOf(errorMessage), HttpHeaders.EMPTY,request);
    }
    
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        
        Map<String, String> errors = new LinkedHashMap<>();
        
        e.getBindingResult().getFieldErrors().stream()
                .forEach(fieldError -> {
                    String fieldName = fieldError.getField();
                    String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
                    errors.merge(fieldName, errorMessage, (existingErrorMessage, newErrorMessage) -> existingErrorMessage + ", " + newErrorMessage);
                });
        
        return handleExceptionInternalArgs(e,HttpHeaders.EMPTY,ErrorStatus.valueOf("_BAD_REQUEST"),request,errors);
    }
    
    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        e.printStackTrace();
        
        return handleExceptionInternalFalse(e, ErrorStatus._INTERNAL_SERVER_ERROR, HttpHeaders.EMPTY, ErrorStatus._INTERNAL_SERVER_ERROR.getHttpStatus(),request, e.getMessage());
    }
    
    @ExceptionHandler(value = GeneralException.class)
    public ResponseEntity onThrowException(GeneralException generalException, HttpServletRequest request) {
        ErrorReasonDTO errorReasonHttpStatus = generalException.getErrorReasonHttpStatus();
        return handleExceptionInternal(generalException,errorReasonHttpStatus,null,request);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        ErrorReasonDTO errorReason = ex.getErrorStatus().getReasonHttpStatus();
        return new ResponseEntity<>(errorReason, ex.getErrorStatus().getHttpStatus());
    }

    @ExceptionHandler(value = PasswordNotMatchException.class)
    public ResponseEntity<Object> handleUPasswordNotMatchException(PasswordNotMatchException ex, WebRequest request) {
        ErrorReasonDTO errorReason = ex.getErrorStatus().getReasonHttpStatus();
        return new ResponseEntity<>(errorReason, ex.getErrorStatus().getHttpStatus());
    }

    @ExceptionHandler(value = NtsTaxNotFoundException.class)
    public ResponseEntity<Object> handleNtsTaxNotFoundException(NtsTaxNotFoundException ex, WebRequest request) {
        ErrorReasonDTO errorReason = ex.getErrorStatus().getReasonHttpStatus();
        return new ResponseEntity<>(errorReason, ex.getErrorStatus().getHttpStatus());
    }

    @ExceptionHandler(value = OcrApiException.class)
    public ResponseEntity<Object> handleOcrApiException(OcrApiException ex, WebRequest request) {
        ErrorReasonDTO errorReason = ex.getErrorStatus().getReasonHttpStatus();
        return new ResponseEntity<>(errorReason, ex.getErrorStatus().getHttpStatus());
    }

    @ExceptionHandler(value = TeamNotFoundException.class)
    public ResponseEntity<Object> handleTeamNotFoundException(TeamNotFoundException ex, WebRequest request) {
        ErrorReasonDTO errorReason = ex.getErrorStatus().getReasonHttpStatus();
        return new ResponseEntity<>(errorReason, ex.getErrorStatus().getHttpStatus());
    }

    @ExceptionHandler(value = UserNotAssignedException.class)
    public ResponseEntity<Object> handleUserNotAssignedException(UserNotAssignedException ex, WebRequest request) {
        ErrorReasonDTO errorReason = ex.getErrorStatus().getReasonHttpStatus();
        return new ResponseEntity<>(errorReason, ex.getErrorStatus().getHttpStatus());
    }

    @ExceptionHandler(value = UserIsDeletedException.class)
    public ResponseEntity<Object> handleUserIsDeletedException(UserIsDeletedException ex, WebRequest request) {
        ErrorReasonDTO errorReason = ex.getErrorStatus().getReasonHttpStatus();
        return new ResponseEntity<>(errorReason, ex.getErrorStatus().getHttpStatus());
    }
    
    private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorReasonDTO reason,
                                                           HttpHeaders headers, HttpServletRequest request) {
        
        ApiResponse<Object> body = ApiResponse.onFailure(reason.getCode(),reason.getMessage(),null);
//        e.printStackTrace();
        
        WebRequest webRequest = new ServletWebRequest(request);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                reason.getHttpStatus(),
                webRequest
        );
    }
    
    private ResponseEntity<Object> handleExceptionInternalFalse(Exception e, ErrorStatus errorCommonStatus,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request, String errorPoint) {
        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(),errorCommonStatus.getMessage(),errorPoint);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                status,
                request
        );
    }
    
    private ResponseEntity<Object> handleExceptionInternalArgs(Exception e, HttpHeaders headers, ErrorStatus errorCommonStatus,
                                                               WebRequest request, Map<String, String> errorArgs) {
        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(),errorCommonStatus.getMessage(),errorArgs);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                errorCommonStatus.getHttpStatus(),
                request
        );
    }
    
    private ResponseEntity<Object> handleExceptionInternalConstraint(Exception e, ErrorStatus errorCommonStatus,
                                                                     HttpHeaders headers, WebRequest request) {
        ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(), errorCommonStatus.getMessage(), null);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                errorCommonStatus.getHttpStatus(),
                request
        );
    }
}
