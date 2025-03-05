package br.com.susqfree.patient_management.infra.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
//            Jwt jwt = (Jwt) authentication.getPrincipal();
//            var x = ((Jwt) authentication.getPrincipal()).getId();
//            return jwt.getClaimAsString("email");
//        }

        return authentication.getName();
    }

}
