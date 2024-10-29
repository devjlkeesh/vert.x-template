package uz.smartbank.business.esb.api.auth;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthUserRouter {
    private static final Logger logger = LoggerFactory.getLogger(AuthUserRouter.class);

    private final Vertx vertx;
    private final AuthUserHandler authUserHandler;

    public AuthUserRouter(Vertx vertx, AuthUserHandler authUserHandler) {
        this.vertx = vertx;
        this.authUserHandler = authUserHandler;
    }

    public void setRouter(Router router) {
        router.route("/api/v1/auth/*").subRouter(buildSubRouter());
    }

    private Router buildSubRouter() {
        Router router = Router.router(vertx);
        router.get("/by-username").handler(authUserHandler::findByUsername);
        router.get("/").handler(authUserHandler::findAll);
        router.post("/create").consumes("application/json").handler(authUserHandler::save);
        router.post("/generate-token")
                .consumes("application/json")
                .handler(authUserHandler::generateToken);
        return router;
    }

}
