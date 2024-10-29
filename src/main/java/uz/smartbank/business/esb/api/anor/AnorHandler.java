package uz.smartbank.business.esb.api.anor;

import io.vertx.ext.web.RoutingContext;
import uz.smartbank.business.esb.payload.BaseResponse;
import uz.smartbank.business.esb.util.ResponseUtil;

public class AnorHandler {

    private final AnorService anorService;

    public AnorHandler(AnorService anorService) {
        this.anorService = anorService;
    }

    public void echo(RoutingContext rc) {
        String message = rc.request().getParam("message");
        String responseBody = anorService.echo(message);
        var response = new BaseResponse<>(responseBody);
        ResponseUtil.buildOkResponse(rc, response);
    }
}
