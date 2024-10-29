package uz.smartbank.business.esb.api.auth;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import lombok.extern.slf4j.Slf4j;
import uz.smartbank.business.esb.util.PasswordUtil;

import java.util.List;

@Slf4j
public class AuthUserService {
    private final JWTAuth jwtAuth;
    private final AuthUserRepository authUserRepository;

    public AuthUserService(JWTAuth jwtAuth, AuthUserRepository authUserRepository) {
        this.jwtAuth = jwtAuth;
        this.authUserRepository = authUserRepository;
    }

    public Future<Long> save(AuthUserCreateData createData) {
        createData.setPassword(PasswordUtil.hashPassword(createData.getPassword()));
        return authUserRepository.save(createData);
    }

    public Future<AuthUserData> findByUsername(String username) {
        return authUserRepository.findByUsername(username);
    }

    public Future<String> getToken(String username, String password) {
        return authUserRepository.findByUsername(username)
                .map(authUserData -> {
                    if (PasswordUtil.verifyPassword(password, authUserData.password())) {
                        JWTOptions options = new JWTOptions()
                                .setSubject(username)
                                .setExpiresInSeconds(60 * 60);
                        JsonObject claims = new JsonObject()
                                .put("email", authUserData.email())
                                .put("roles", authUserData.roles())
                                .put("permissions", authUserData.permissions());
                        return jwtAuth.generateToken(claims, options);
                    }
                    throw new IllegalArgumentException("Invalid password");
                });
    }


    public Future<List<AuthUserData>> findAll() {
        return authUserRepository.findAll();
    }
}
