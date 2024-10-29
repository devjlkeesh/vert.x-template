package uz.smartbank.business.esb.exception;

import java.util.Arrays;

public class ServiceException extends RuntimeException {
    private final String code;
    private final int status;
    private final transient Object[] params;
    private Throwable cause;

    public ServiceException(String code, int status, Throwable cause, Object... params) {
        this.code = code;
        this.status = status;
        this.params = params;
        this.cause = cause;
    }

    public static ServiceException badRequest(String code, Object... params) {
        return new ServiceException(code, 400, (Throwable) null, params);
    }

    public static ServiceException badRequest(String code, Throwable cause, Object... params) {
        return new ServiceException(code, 400, cause, params);
    }

    public static ServiceException internalServerError(String code, Object... params) {
        return new ServiceException(code, 500, (Throwable) null, params);
    }

    public static ServiceException internalServerError(String code, Throwable cause, Object... params) {
        return new ServiceException(code, 500, cause, params);
    }

    public String getCode() {
        return this.code;
    }

    public int getStatus() {
        return this.status;
    }

    public Object[] getParams() {
        return this.params;
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }

    @Override
    public String getMessage() {
        return "code : " + code + ", status : " + status + ", params : " + Arrays.toString(params);
    }
}