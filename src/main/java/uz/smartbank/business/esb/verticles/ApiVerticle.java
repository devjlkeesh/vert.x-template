package uz.smartbank.business.esb.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.Router;
import io.vertx.sqlclient.Pool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uz.smartbank.business.esb.api.anor.AnorHandler;
import uz.smartbank.business.esb.api.anor.AnorRouter;
import uz.smartbank.business.esb.api.anor.AnorService;
import uz.smartbank.business.esb.api.auth.AuthUserHandler;
import uz.smartbank.business.esb.api.auth.AuthUserRepository;
import uz.smartbank.business.esb.api.auth.AuthUserRouter;
import uz.smartbank.business.esb.api.auth.AuthUserService;
import uz.smartbank.business.esb.api.auth.JwtTokenHandler;
import uz.smartbank.business.esb.api.egov.EgovHandler;
import uz.smartbank.business.esb.api.egov.EgovRouter;
import uz.smartbank.business.esb.api.egov.EgovService;
import uz.smartbank.business.esb.api.munis.MunisHandler;
import uz.smartbank.business.esb.api.munis.MunisRouter;
import uz.smartbank.business.esb.api.munis.MunisService;
import uz.smartbank.business.esb.config.AuthConfiguration;
import uz.smartbank.business.esb.config.DbConiguration;

public class ApiVerticle extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(ApiVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) {
        Router router = getRouter();
        int port = config().getInteger("server.port", 8080);
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(port, res -> {
                    if (res.succeeded()) {
                        logger.info("http server started. http://localhost:{}", port);
                    } else {
                        logger.error("http server failed to start", res.cause());
                    }
                });
    }

    private Router getRouter() {

        Router router = Router.router(vertx);
        DbConiguration dbConiguration = new DbConiguration();
        Pool pooledClient = dbConiguration.getClient(vertx);
        AuthConfiguration authConfiguration = new AuthConfiguration(vertx);
        Future<JWTAuth> jwtAuthFuture = authConfiguration.getJwtAuth();
        JWTAuth jwtAuth = jwtAuthFuture.result();
        JwtTokenHandler jwtTokenHandler = new JwtTokenHandler(jwtAuth);

        AnorService anorService = new AnorService();
        AnorHandler anorHandler = new AnorHandler(anorService);
        AnorRouter anorRouter = new AnorRouter(vertx, anorHandler, jwtTokenHandler);
        anorRouter.setRouter(router);

        EgovService egovService = new EgovService();
        EgovHandler egovHandler = new EgovHandler(egovService);
        EgovRouter egovRouter = new EgovRouter(vertx, jwtTokenHandler, egovHandler);
        egovRouter.setRouter(router);

        MunisService munisService = new MunisService();
        MunisHandler munisHandler = new MunisHandler(munisService);
        MunisRouter munisRouter = new MunisRouter(vertx, jwtTokenHandler, munisHandler);
        munisRouter.setRouter(router);

        AuthUserRepository authUserRepository = new AuthUserRepository(pooledClient);
        AuthUserService authUserService = new AuthUserService(jwtAuth, authUserRepository);
        AuthUserHandler authUserHandler = new AuthUserHandler(authUserService);
        AuthUserRouter authUserRouter = new AuthUserRouter(vertx, authUserHandler);
        authUserRouter.setRouter(router);

        return router;
    }


}
