package uz.smartbank.business.esb.api.auth;

import io.vertx.core.Future;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PreparedQuery;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AuthUserRepository {
    private final PreparedQuery<RowSet<Row>> insertPreparedStatement;
    private final PreparedQuery<RowSet<Row>> findByUsernamePreparedQuery;
    private final PreparedQuery<RowSet<Row>> findAllPreparedQuery;

    public AuthUserRepository(Pool pooledClient) {
        this.insertPreparedStatement = pooledClient.preparedQuery("INSERT INTO auth_users (username, email, password, roles, permissions) VALUES ($1, $2, $3, $4, $5) RETURNING id ;");
        this.findByUsernamePreparedQuery = pooledClient.preparedQuery("SELECT * FROM auth_users WHERE username = $1 ;");
        this.findAllPreparedQuery = pooledClient.preparedQuery("SELECT * FROM auth_users ;");
    }


    public Future<Long> save(AuthUserCreateData data) {
        return insertPreparedStatement
                .execute(Tuple.of(data.getUsername(), data.getEmail(), data.getPassword(), data.getRoles(), data.getPermissions()))
                .map(rows -> rows.iterator().next().getLong("id"));
    }

    public Future<AuthUserData> findByUsername(String username) {
        return findByUsernamePreparedQuery
                .execute(Tuple.of(username))
                .flatMap(rows -> {
                    if (rows.size() > 0) {
                        Row row = rows.iterator().next();
                        return Future.succeededFuture(AuthUserRowMapper.toAuthUserData(row));
                    } else {
                        return Future.failedFuture("User not found");
                    }
                });
    }

    public Future<List<AuthUserData>> findAll() {
        return findAllPreparedQuery
                .execute()
                .map(rows -> {
                    if (rows.size() > 0) {
                        List<AuthUserData> authUserDataList = new ArrayList<>(rows.size());
                        rows.forEach(row -> authUserDataList.add(AuthUserRowMapper.toAuthUserData(row)));
                        return authUserDataList;
                    }
                    return Collections.emptyList();
                });
    }

}
