package uz.smartbank.business.esb.api.auth;

import java.util.List;

public record AuthUserData(Long id, String username, String email, String password, List<String> roles,List<String> permissions) {
}
