package com.alexeyn.couponator.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;


@ControllerAdvice
public class ExceptionsHandler implements ExceptionMapper<Throwable> {

    @ExceptionHandler()
    public Response toResponse(Throwable exception) {
        if (exception instanceof ApplicationException) {
            ApplicationException e = (ApplicationException) exception;
            int internalErrorCode = e.getErrorType().getInternalErrorCode();
            String internalMessage = e.getMessage();
            String externalMessage = e.getErrorType().getErrorMessage();
            ErrorBean errorBean = new ErrorBean(internalErrorCode, internalMessage, externalMessage);
            if (e.getErrorType().isCritical()) {
                e.printStackTrace();
            }
            return Response.status(e.getErrorCode()).entity(errorBean).build();

        } else if (exception instanceof Exception) {
            String internalMessage = exception.getMessage();
            ErrorBean errorBean = new ErrorBean(601, internalMessage, "General error");
            exception.printStackTrace();
            return Response.status(601).entity(errorBean).build();
        }
        exception.printStackTrace();
        return Response.status(500).entity(null).build();
    }


}
