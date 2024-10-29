package uz.smartbank.business.esb.util;

public final class ErrorCode {

    public static final String UNAUTHORIZED = "-401";
    public static final String ACCESS_DENIED = "-403";

    private ErrorCode() {
        throw new IllegalStateException("Utility class");
    }
}
