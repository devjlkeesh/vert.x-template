package uz.smartbank.business.esb.api.auth;

import io.vertx.sqlclient.Row;

import java.util.Arrays;

public final class AuthUserRowMapper {

    private AuthUserRowMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static AuthUserData toAuthUserData(final Row row) {
        return new AuthUserData(
                row.getLong("id"),
                row.getString("username"),
                row.getString("email"),
                row.getString("password"),
                Arrays.stream(row.getString("roles").split(",")).toList(),
                Arrays.stream(row.getString("permissions").split(",")).toList()
        );
    }
}
