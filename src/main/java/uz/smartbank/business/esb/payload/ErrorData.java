package uz.smartbank.business.esb.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import uz.smartbank.business.esb.config.jackson.TupleArrayToMapSerializer;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorData(String code,
                        @JsonSerialize(using = TupleArrayToMapSerializer.class) Tuple2<String, String>[] detail,
                        Object[] params) {

    public static ErrorData withCode(String code) {
        return new ErrorData(code, null, null);
    }

    public static ErrorData withCodeAndParams(String code, Object... params) {
        return new ErrorData(code, null, params);
    }

    @SafeVarargs
    public static ErrorData withCodeAndDetails(String code, Tuple2<String, String>... detail) {
        return new ErrorData(code, detail, null);
    }

    public String toString() {
        return "ErrorData{code='" + this.code + "'}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof ErrorData errorData) {
            return this.code.equals(errorData.code);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.code.hashCode();
    }

}
