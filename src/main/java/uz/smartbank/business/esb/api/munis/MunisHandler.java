package uz.smartbank.business.esb.api.munis;

import io.vertx.ext.web.RoutingContext;
import uz.smartbank.business.esb.payload.BaseResponse;
import uz.smartbank.business.esb.util.ResponseUtil;

public class MunisHandler {

    private final MunisService munisService;

    public MunisHandler(MunisService munisService) {
        this.munisService = munisService;
    }

    public void echo(RoutingContext rc) {
        String message = rc.request().getParam("message");
        String responseBody = munisService.echo(message);
        var response = new BaseResponse<>(responseBody);
        ResponseUtil.buildOkResponse(rc, response);
    }

}
