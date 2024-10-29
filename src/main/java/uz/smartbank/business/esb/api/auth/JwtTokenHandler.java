package uz.smartbank.business.esb.api.auth;

import io.vertx.core.json.JsonArray;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authentication.TokenCredentials;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.RoutingContext;
import uz.smartbank.business.esb.exception.ServiceException;
import uz.smartbank.business.esb.util.ErrorCode;
import uz.smartbank.business.esb.util.ResponseUtil;

public class JwtTokenHandler {
    private final JWTAuth jwtAuth;

    public JwtTokenHandler(JWTAuth jwtAuth) {
        this.jwtAuth = jwtAuth;
    }


    public void isAuthenticatedHandler(RoutingContext rc) {
        String authorization = rc.request().getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            TokenCredentials tokenCredentials = new TokenCredentials(authorization.substring(7));
            jwtAuth.authenticate(tokenCredentials)
                    .onSuccess(event -> {
                        rc.setUser(event);
                        rc.next();
                    })
                    .onFailure(event -> ResponseUtil.buildErrorResponse(rc, event));
            return;
        }
        ResponseUtil.buildErrorResponse(rc, new ServiceException(ErrorCode.UNAUTHORIZED, 401, null));
    }

    public void hasPermission(RoutingContext rc, String permission) {
        User user = rc.user();
        if (user == null) {
            ResponseUtil.buildErrorResponse(rc, new ServiceException(ErrorCode.UNAUTHORIZED, 401, null));
            return;
        }
        JsonArray userPermissions = user.principal().getJsonArray("permissions");

        if (userPermissions.contains(permission)) {
            rc.next();
        } else {
            ResponseUtil.buildErrorResponse(rc, new ServiceException(ErrorCode.ACCESS_DENIED, 403, null));
        }
    }

    public void hasAnyPermission(RoutingContext rc, String... permissions) {
        User user = rc.user();
        if (user == null) {
            ResponseUtil.buildErrorResponse(rc, new ServiceException(ErrorCode.UNAUTHORIZED, 401, null));
            return;
        }

        JsonArray userPermissions = user.principal().getJsonArray("permissions");
        for (String permission : permissions) {
            if (userPermissions.contains(permission)) {
                rc.next();
            }
        }
        ResponseUtil.buildErrorResponse(rc, new ServiceException(ErrorCode.ACCESS_DENIED, 403, null));
    }


    public void hasRole(RoutingContext rc, String role) {
        User user = rc.user();
        if (user == null) {
            ResponseUtil.buildErrorResponse(rc, new ServiceException(ErrorCode.UNAUTHORIZED, 401, null));
            return;
        }
        JsonArray userRoles = user.principal().getJsonArray("roles");

        if (userRoles.contains(role)) {
            rc.next();
        } else {
            ResponseUtil.buildErrorResponse(rc, new ServiceException(ErrorCode.ACCESS_DENIED, 403, null));
        }
    }

    public void hasAnyRole(RoutingContext rc, String... roles) {
        User user = rc.user();
        if (user == null) {
            ResponseUtil.buildErrorResponse(rc, new ServiceException(ErrorCode.UNAUTHORIZED, 401, null));
            return;
        }

        JsonArray userPermissions = user.principal().getJsonArray("roles");
        for (String permission : roles) {
            if (userPermissions.contains(permission)) {
                rc.next();
            }
        }
        ResponseUtil.buildErrorResponse(rc, new ServiceException(ErrorCode.ACCESS_DENIED, 403, null));
    }

}
