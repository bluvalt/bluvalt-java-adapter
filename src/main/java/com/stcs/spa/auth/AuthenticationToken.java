package com.stcs.spa.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

public class AuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 1L;
	public AuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
	}
	private String principal;
    private String credentials;
    public Object getCredentials() {
        return credentials;
    }
    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }
    @Override
    public Object getPrincipal() {
        return principal;
    }
    public void setPrincipal(String principal) {
        this.principal = principal;
    }
}