package web.mvc.advice;


import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import web.mvc.exception.ErrorCode;
import web.mvc.exception.ErrorCodeProvider;
import web.mvc.exception.MemberAuthenticationException;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@RestControllerAdvice
public class DefaultExceptionAdvice {
    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleRuntimeException(RuntimeException e) {
        ProblemDetail problemDetail;

        if (e instanceof ErrorCodeProvider provider) {
            ErrorCode errorCode = provider.getErrorCode();
            problemDetail = ProblemDetail.forStatus(errorCode.getHttpStatus());
            problemDetail.setTitle(errorCode.getTitle());
            problemDetail.setDetail(errorCode.getMessage());
        } else {
            // 알 수 없는 런타임 예외 처리
            problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            problemDetail.setTitle("Unexpected Error");
            problemDetail.setDetail(e.getMessage());
        }

        problemDetail.setProperty("exception", e.getClass().getSimpleName());
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }
}