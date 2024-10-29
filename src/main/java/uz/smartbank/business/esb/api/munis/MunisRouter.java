package uz.smartbank.business.esb.api.munis;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import uz.smartbank.business.esb.api.auth.JwtTokenHandler;

public class MunisRouter {
    private final Vertx vertx;
    private final JwtTokenHandler jwtTokenHandler;
    private final MunisHandler munisHandler;

    public MunisRouter(Vertx vertx, JwtTokenHandler jwtTokenHandler, MunisHandler munisHandler) {
        this.vertx = vertx;
        this.jwtTokenHandler = jwtTokenHandler;
        this.munisHandler = munisHandler;
    }

    public void echo(RoutingContext context) {
        context.response().putHeader("content-type", "text/plain");
        context.response().end("Hello munis. Mr." + context.user().subject());
    }

    public void setRouter(Router router) {
        router.route("/api/v1/munis/*")
                .handler(jwtTokenHandler::isAuthenticatedHandler)
                .subRouter(buildSubRouter());
    }

    private Router buildSubRouter() {
        Router router = Router.router(vertx);
        router.get("/echo")
                .handler(rc -> jwtTokenHandler.hasPermission(rc, "munis_echo"))
                .handler(munisHandler::echo);
        return router;
    }
}
