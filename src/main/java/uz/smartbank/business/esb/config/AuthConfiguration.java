package uz.smartbank.business.esb.config;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;

public class AuthConfiguration {
    private final Vertx vertx;

    public AuthConfiguration(Vertx vertx) {
        this.vertx = vertx;
    }


    public Future<JWTAuth> getJwtAuth() {
        return Future.succeededFuture(JWTAuth.create(vertx, new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                        .setAlgorithm("HS256")
                        .setBuffer("secret"))));
    }
}
