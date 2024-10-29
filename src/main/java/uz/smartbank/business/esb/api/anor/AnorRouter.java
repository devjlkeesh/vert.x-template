package uz.smartbank.business.esb.api.anor;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import uz.smartbank.business.esb.api.auth.JwtTokenHandler;

public class AnorRouter {
    private final Vertx vertx;
    private final AnorHandler anorHandler;
    private final JwtTokenHandler jwtTokenHandler;

    public AnorRouter(Vertx vertx, AnorHandler anorHandler, JwtTokenHandler jwtTokenHandler) {
        this.vertx = vertx;
        this.anorHandler = anorHandler;
        this.jwtTokenHandler = jwtTokenHandler;
    }


    public void setRouter(Router router) {
        router.route("/api/v1/anor/*")
                .handler(jwtTokenHandler::isAuthenticatedHandler)
                .subRouter(buildSubRouter());
    }

    private Router buildSubRouter() {
        Router router = Router.router(vertx);
        router.get("/echo")
                .handler(rc -> jwtTokenHandler.hasAnyPermission(rc, "anor_echo"))
                .handler(anorHandler::echo);
        return router;
    }
}
