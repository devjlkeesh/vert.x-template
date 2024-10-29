package uz.smartbank.business.esb.api.auth;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import uz.smartbank.business.esb.payload.BaseResponse;
import uz.smartbank.business.esb.util.ResponseUtil;

public class AuthUserHandler {

    private final AuthUserService authUserService;

    public AuthUserHandler(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    public void save(RoutingContext rc) {
        rc.request().body().onSuccess(body -> {
                    AuthUserCreateData authUserCreateData = body.toJsonObject().mapTo(AuthUserCreateData.class);
                    authUserService.save(authUserCreateData)
                            .onSuccess(id -> ResponseUtil.buildCreatedResponse(rc, new BaseResponse<>(id)))
                            .onFailure(e -> ResponseUtil.buildErrorResponse(rc, e));
                })
                .onFailure(e -> ResponseUtil.buildErrorResponse(rc, e));
    }

    public void findByUsername(RoutingContext rc) {
        String username = rc.request().getParam("username");
        authUserService.findByUsername(username)
                .onSuccess(data -> ResponseUtil.buildCreatedResponse(rc, new BaseResponse<>(data)))
                .onFailure(e -> ResponseUtil.buildErrorResponse(rc, e));

    }


    public void generateToken(RoutingContext rc) {
        rc.request().body().onSuccess(body -> {
                    JsonObject jo = body.toJsonObject();
                    authUserService.getToken(jo.getString("username"), jo.getString("password"))
                            .onFailure(e -> ResponseUtil.buildErrorResponse(rc, e))
                            .onSuccess(token -> ResponseUtil.buildOkResponse(rc, new BaseResponse<>(token)));
                })
                .onFailure(e -> ResponseUtil.buildErrorResponse(rc, e));
    }

    public void findAll(RoutingContext rc) {
        authUserService.findAll()
                .onFailure(e -> ResponseUtil.buildErrorResponse(rc, e))
                .onSuccess(token -> ResponseUtil.buildOkResponse(rc, new BaseResponse<>(token)));
    }

}
