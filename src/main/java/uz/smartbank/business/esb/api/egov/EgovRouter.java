package uz.smartbank.business.esb.api.egov;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import uz.smartbank.business.esb.api.auth.JwtTokenHandler;

public class EgovRouter {
    private final Vertx vertx;
    private final JwtTokenHandler jwtTokenHandler;
    private final EgovHandler egovHandler;

    public EgovRouter(Vertx vertx, JwtTokenHandler jwtTokenHandler, EgovHandler egovHandler) {
        this.vertx = vertx;
        this.jwtTokenHandler = jwtTokenHandler;
        this.egovHandler = egovHandler;
    }

    public void echo(RoutingContext context) {
        context.response().putHeader("content-type", "text/plain");
        context.response().end("Hello egov. Mr." + context.user().subject());
    }

    public void setRouter(Router router) {
        router.route("/api/v1/egov/*")
                .handler(jwtTokenHandler::isAuthenticatedHandler)
                .subRouter(buildSubRouter());
    }

    private Router buildSubRouter() {
        Router router = Router.router(vertx);
        router.get("/echo")
                .handler(rc -> jwtTokenHandler.hasPermission(rc, "egov_echo"))
                .handler(egovHandler::echo);
        return router;
    }
}
