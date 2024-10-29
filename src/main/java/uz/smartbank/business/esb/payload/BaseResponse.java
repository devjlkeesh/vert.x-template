package uz.smartbank.business.esb.payload;


public record BaseResponse<T>(T body, boolean success, ErrorData error) {

    public BaseResponse(T body) {
        this(body, true, null);
    }

    public BaseResponse(ErrorData error) {
        this(null, false, error);
    }
}
