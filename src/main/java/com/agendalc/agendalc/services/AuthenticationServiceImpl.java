package com.agendalc.agendalc.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.agendalc.agendalc.services.interfaces.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            // Depending on the security configuration, this might be a valid state
            // or an error. Throwing an exception is safer if a user is always expected.
            throw new IllegalStateException("No se pudo obtener la autenticación del contexto de seguridad.");
        }
        return authentication.getName();
    }
}
