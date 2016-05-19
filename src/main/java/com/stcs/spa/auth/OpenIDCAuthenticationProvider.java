package com.stcs.spa.auth;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class OpenIDCAuthenticationProvider implements AuthenticationProvider  {

	private static final Logger logger = Logger.getLogger(OpenIDCAuthenticationProvider.class);
 
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
 
        // Assume that if principal present user was authenticated against external service
        if (authentication != null && authentication.getPrincipal() != null) {
            authentication.setAuthenticated(true);          
            logger.debug("User " + authentication.getPrincipal() + " authenticated");            
            return authentication;
        }      
        logger.debug("Ticked not valid");
        throw new BadCredentialsException("Ticked not valid");
    }
    
 
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(AuthenticationToken.class);
 
    }
}
