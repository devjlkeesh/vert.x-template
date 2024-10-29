package uz.smartbank.business.esb.util;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uz.smartbank.business.esb.exception.ServiceException;
import uz.smartbank.business.esb.payload.BaseResponse;
import uz.smartbank.business.esb.payload.ErrorData;

import java.util.NoSuchElementException;

public class ResponseUtil {

    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

    private ResponseUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void buildOkResponse(RoutingContext rc,
                                       Object response) {
        rc.response().setStatusCode(200)
                .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                .end(Json.encodePrettily(response));
    }

    public static void buildCreatedResponse(RoutingContext rc, Object response) {
        rc.response().setStatusCode(201)
                .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                .end(Json.encodePrettily(response));
    }

    public static void buildNoContentResponse(RoutingContext rc) {
        rc.response().setStatusCode(204).end();
    }

    public static void buildErrorResponse(RoutingContext rc,
                                          Throwable throwable) {
        final int status;
        final String message;
        logger.error(throwable.getMessage(), throwable);
        if (throwable instanceof ServiceException se) {
            status = se.getStatus();
            message = se.getCode();
        } else if (throwable instanceof IllegalArgumentException || throwable instanceof IllegalStateException || throwable instanceof NullPointerException) {
            status = 400;
            message = throwable.getMessage();
        } else if (throwable instanceof NoSuchElementException) {
            status = 404;
            message = throwable.getMessage();
        } else {
            status = 500;
            message = "Internal Server Error";
        }
        ErrorData errorData = ErrorData.withCode(message);
        rc.response()
                .setStatusCode(status)
                .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                .end(Json.encodePrettily(new BaseResponse<>(errorData)));
    }


}
