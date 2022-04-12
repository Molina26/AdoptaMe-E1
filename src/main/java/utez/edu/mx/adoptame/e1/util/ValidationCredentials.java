package utez.edu.mx.adoptame.e1.util;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class ValidationCredentials {

    public static boolean validateCredential (Collection<? extends GrantedAuthority> auths, String roleToSearch) {
        boolean flag = false;

        for (GrantedAuthority authority : auths) {
            if (authority.getAuthority().equals(roleToSearch)) {
              flag = true;
            }
        }

        return flag;
    }
}
