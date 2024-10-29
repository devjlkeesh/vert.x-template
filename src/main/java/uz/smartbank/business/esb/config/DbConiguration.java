package uz.smartbank.business.esb.config;

import io.vertx.core.Vertx;
import io.vertx.pgclient.PgBuilder;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;

public class DbConiguration {

    public static PgConnectOptions connectOptions = new PgConnectOptions()
            .setPort(5432)
            .setHost("localhost")
            .setDatabase("esbvertx")
            .setUser("postgres")
            .setPassword("postgres");

    public static PoolOptions poolOptions = new PoolOptions()
            .setIdleTimeout(60)
            .setMaxSize(10);

    public Pool getClient(Vertx vertx) {
        return PgBuilder
                .pool()
                .with(poolOptions)
                .connectingTo(connectOptions)
                .using(vertx)
                .build();
    }
}
