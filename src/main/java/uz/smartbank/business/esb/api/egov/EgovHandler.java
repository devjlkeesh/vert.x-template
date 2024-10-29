package uz.smartbank.business.esb.api.egov;

import io.vertx.ext.web.RoutingContext;
import uz.smartbank.business.esb.payload.BaseResponse;
import uz.smartbank.business.esb.util.ResponseUtil;

public class EgovHandler {

    private final EgovService egovService;

    public EgovHandler(EgovService egovService) {
        this.egovService = egovService;
    }

    public void echo(RoutingContext rc) {
        String message = rc.request().getParam("message");
        String responseBody = egovService.echo(message);
        var response = new BaseResponse<>(responseBody);
        ResponseUtil.buildOkResponse(rc, response);
    }

}
