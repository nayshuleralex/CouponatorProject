package com.alexeyn.couponator.exceptions;


import com.alexeyn.couponator.enums.ErrorTypes;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;


// Exception handler class
@RestControllerAdvice
public class ExceptionsHandler {
    //	Response - Object in Spring
    @ExceptionHandler
    @ResponseBody
    // Variable name is throwable in order to remember that it handles Exception and Error
//	Spring do injection to a familiar variable - like HttpServletResponse
    public ErrorBean toResponse(Throwable throwable, HttpServletResponse response) {

        //	ErrorBean errorBean;
        if(throwable instanceof ApplicationException) {
            ApplicationException appException = (ApplicationException) throwable;

            ErrorTypes errorType = appException.getErrorType();
            int errorNumber = errorType.getInternalErrorCode();
            String errorMessage = errorType.getErrorMessage();
            String errorName = errorType.name();

            ErrorBean errorBean = new ErrorBean(errorNumber, errorMessage, errorName);
            if(appException.getErrorType().isCritical()) {
                appException.printStackTrace();
            }

            response.setStatus(errorNumber);
            return errorBean;
        }

        String errorMessage = throwable.getMessage();
        ErrorBean errorBean = new ErrorBean(601, errorMessage, "General error");
        throwable.printStackTrace();


        response.setStatus(errorBean.getInternalErrorCode());
        return errorBean;
    }
}
