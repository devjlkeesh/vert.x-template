package uz.smartbank.business.esb.api.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthUserCreateData {
    private String username;
    private String password;
    private String email;
    private String roles;
    private String permissions;
}
