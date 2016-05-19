package com.stcs.spa.auth;


import com.google.common.base.Strings;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.oauth2.sdk.*;
import com.nimbusds.oauth2.sdk.auth.ClientAuthentication;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.oauth2.sdk.token.BearerAccessToken;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;
import com.nimbusds.openid.connect.sdk.*;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.util.DefaultJWTDecoder;
import com.stcs.spa.services.SPServices;
import com.stcs.spa.util.Config;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class OpenIDCAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    Config config = Config.getInstance();
    private static final Logger logger = Logger.getLogger(OpenIDCAuthenticationFilter.class);
    OIDCProviderMetadata providerMetadata = config.providerMetadata;
    protected final static String STATE_SESSION_VARIABLE = "state";
    protected final static String NONCE_SESSION_VARIABLE = "nonce";
    @Autowired
    @Qualifier("defaultSPServices")
    SPServices spServices;

    protected OpenIDCAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException,
            IOException, ServletException {
        if (!Strings.isNullOrEmpty(request.getParameter("error"))) {

            // there's an error coming back from the server, need to handle this
            handleError(request, response);
            return null; // no auth, response is sent to display page or something

        } else if (!Strings.isNullOrEmpty(request.getParameter("code"))) { // code is not null

            // we got back the code, need to process this to get our tokens
            return handleAuthorizationCodeResponse(request, response);

        } else {
            // not an error, not a code, must be an initial login of some type
            handleAuthorizationRequest(request, response);

            return null; // no auth, response redirected to the server's Auth Endpoint (or possibly to the account chooser)
        }
    }


    /**
     * Initiate an Authorization request
     *
     * @param request  The request from which to extract parameters and perform the
     *                 authentication
     * @param response
     * @throws IOException If an input or output exception occurs
     */
    protected void handleAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {


        try {

            boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
            if (ajax) {
                response.sendError(601, "Ajax Call. Handle through JS.");
            } else {
                // Generate random state string for pairing the response to the request
                State state = new State();
                // Generate nonce
                Nonce nonce = new Nonce();
                // Specify scope
                Scope scope = new Scope();
                scope.add(OIDCScopeValue.OPENID);
                scope.add(OIDCScopeValue.EMAIL);
                scope.add(OIDCScopeValue.PROFILE);
                HttpSession session = request.getSession();
                // this value comes back in the auth code response
                saveState(session, state);
                saveNonce(session, nonce);
                URI redirectURI = new URI(config.properties.getProperty("redirect_uri"));
                ClientID clientID = new ClientID(config.properties.getProperty("client_id"));

                // Compose the request
                logger.debug(providerMetadata.getAuthorizationEndpointURI());
                AuthenticationRequest authenticationRequest = new AuthenticationRequest(providerMetadata.getAuthorizationEndpointURI(),
                        new ResponseType(ResponseType.Value.CODE),
                        scope, clientID, redirectURI, state, nonce);
                URI authReqURI = authenticationRequest.toURI();


                response.sendRedirect(authReqURI.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    /**
     * @param request The request from which to extract parameters and perform the
     *                authentication
     * @return The authenticated user token, or null if authentication is
     * incomplete.
     */
    protected Authentication handleAuthorizationCodeResponse(HttpServletRequest request, HttpServletResponse response) {


        try {


            logger.debug("in handleAuthorizationCodeResponse ");
            URI redirectURI = new URI(config.properties.getProperty("redirect_uri"));
            ClientID clientID = new ClientID(config.properties.getProperty("client_id"));
            Secret clientSecret = new Secret(config.properties.getProperty("client_secret"));
            HttpSession session = request.getSession();
            StringBuffer requestURL = request.getRequestURL();
            String queryString = request.getQueryString();

            if (queryString == null) {
                requestURL.toString();
            } else {
                requestURL.append('?').append(queryString).toString();
            }
            AuthenticationResponse authResp = null;

            URI uri1 = new URI(requestURL.toString());
            authResp = AuthenticationResponseParser.parse(uri1);


            if (authResp instanceof AuthenticationErrorResponse) {
                ErrorObject error = ((AuthenticationErrorResponse) authResp)
                        .getErrorObject();
                throw new AuthenticationServiceException("AuthenticationErrorResponse: " + error.getDescription());
            }
            AuthenticationSuccessResponse successResponse = (AuthenticationSuccessResponse) authResp;

			  /*
               * The state in the received authentication response must match the state
			   * specified in the previous outgoing authentication request.
			  */

            // check for state, if it doesn't match we bail early
            State storedState = getStoredState(request.getSession());
            if (storedState != null) {
                State state = successResponse.getState();
                if (!storedState.equals(state)) {
                    throw new AuthenticationServiceException("State parameter mismatch on return.");
                } else {
                    logger.debug("Status code is matching....");
                }
            }

            AuthorizationCode authCode = successResponse.getAuthorizationCode();
            ClientAuthentication clientAuth = new ClientSecretBasic(
                    clientID,
                    clientSecret);


            TokenRequest tokenReq = new TokenRequest(
                    providerMetadata.getTokenEndpointURI(), clientAuth,
                    new AuthorizationCodeGrant(authCode,
                            redirectURI));

            HTTPResponse tokenHTTPResp = null;
            try {
                tokenHTTPResp = tokenReq.toHTTPRequest().send();
            } catch (SerializeException | IOException se) {
                se.printStackTrace();
                throw new AuthenticationServiceException("Unable to obtain Access Token.  Token Endpoint returned: " + se.getMessage());
            }

            // Parse and check response
            TokenResponse tokenResponse = null;

            tokenResponse = OIDCTokenResponseParser.parse(tokenHTTPResp);

            if (tokenResponse instanceof TokenErrorResponse) {
                ErrorObject error = ((TokenErrorResponse) tokenResponse).getErrorObject();
                throw new AuthenticationServiceException("Unable to obtain Access Token:" + error.getCode() + ":" + error.getDescription());

            }
            OIDCAccessTokenResponse accessTokenResponse = (OIDCAccessTokenResponse) tokenResponse;
            accessTokenResponse.getAccessToken();
            ReadOnlyJWTClaimsSet claimSet = verifyIdToken(accessTokenResponse.getIDToken(), providerMetadata.getJWKSetURI());
            logger.debug("accessTokenResponse.getAccessToken()" + accessTokenResponse.getAccessToken());
            JSONObject claims = claimSet.toJSONObject();
            // compare the nonce to our stored claim
            String nonce = claimSet.getStringClaim("nonce");
            if (Strings.isNullOrEmpty(nonce)) {

                logger.error("ID token did not contain a nonce claim.");

                throw new AuthenticationServiceException("ID token did not contain a nonce claim.");
            } else {
                logger.debug("Checking if Nonce exist Passed");
            }

            Nonce storedNonce = getStoredNonce(session);
            if (!nonce.equals(storedNonce.getValue())) {
                logger.error("Possible replay attack detected! The comparison of the nonce in the returned "
                        + "ID Token to the session " + NONCE_SESSION_VARIABLE + " failed. Expected " + storedNonce + " got " + nonce + ".");

                throw new AuthenticationServiceException(
                        "Possible replay attack detected! The comparison of the nonce in the returned "
                                + "ID Token to the session " + NONCE_SESSION_VARIABLE + " failed. Expected " + storedNonce + " got " + nonce + ".");
            } else {
                logger.debug("Checking for Possible replay attack passed.");
            }
            String sub = (String) claims.get("sub");
            logger.debug("Sub................................" + sub);
            if (spServices.validateUser(Long.parseLong(sub))) {


                UserInfoRequest userInfoReq = new UserInfoRequest(
                        providerMetadata.getUserInfoEndpointURI(),
                        (BearerAccessToken) accessTokenResponse.getAccessToken());

                HTTPResponse userInfoHTTPResp = null;
                try {
                    userInfoHTTPResp = userInfoReq.toHTTPRequest().send();
                } catch (SerializeException se) {
                    se.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new AuthenticationServiceException(e.getMessage());
                }

                UserInfoResponse userInfoResponse = null;
                try {
                    userInfoResponse = UserInfoResponse.parse(userInfoHTTPResp);
                } catch (ParseException e) {
                    throw new AuthenticationServiceException("Unable to obtain Access Token. " + e.getMessage());

                }

                if (userInfoResponse instanceof UserInfoErrorResponse) {
                    ErrorObject error = ((UserInfoErrorResponse) userInfoResponse).getErrorObject();
                    throw new AuthenticationServiceException("Unable to obtain Access Token. " + error.getDescription());

                }

                UserInfoSuccessResponse successResponse1 = (UserInfoSuccessResponse) userInfoResponse;
                JSONObject claims1 = successResponse1.getUserInfo().toJSONObject();
                logger.debug("preferred_username......" + claims1.get("preferred_username"));
                request.getSession().setAttribute("userName", claims1.get("preferred_username"));
                Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                AuthenticationToken authentication = null;

                // Assign user role only if user is neither  administrator nor administrator
                if (sub != null && authorities.size() == 0) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
                }
                authentication = new AuthenticationToken(authorities);
                authentication.setPrincipal(sub);
                logger.debug("User ID.............." + claimSet.getAllClaims().toString());
                authentication.setCredentials(claimSet.getSubject());
                return getAuthenticationManager().authenticate(authentication);
            } else {
                throw new AuthenticationServiceException("User Not Registered.");
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AuthenticationServiceException("Unable to obtain Access Token: " + ex.getMessage());
        }


    }

    private ReadOnlyJWTClaimsSet verifyIdToken(JWT idToken, URI JWKSetURI) {
        RSAPublicKey providerKey = null;
        try {
            JSONObject key = getProviderRSAJWK(JWKSetURI.toURL().openStream());
            providerKey = RSAKey.parse(key).toRSAPublicKey();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticationServiceException(e.getMessage());
        }

        DefaultJWTDecoder jwtDecoder = new DefaultJWTDecoder();
        jwtDecoder.addJWSVerifier(new RSASSAVerifier(providerKey));
        ReadOnlyJWTClaimsSet claims = null;
        try {
            claims = jwtDecoder.decodeJWT(idToken);
        } catch (JOSEException | java.text.ParseException se) {
            se.printStackTrace();
        }
        return claims;
    }

    private JSONObject getProviderRSAJWK(InputStream is) {
        // Read all data from stream
        StringBuilder sb = new StringBuilder();
        try {
            Scanner scanner = new Scanner(is);
            while (scanner.hasNext()) {
                sb.append(scanner.next());
            }

            // Parse the data as json
            String jsonString = sb.toString();
            JSONObject json = JSONObjectUtils.parse(jsonString);

            // Find the RSA signing key
            JSONArray keyList = (JSONArray) json.get("keys");
            for (Object key : keyList) {
                JSONObject k = (JSONObject) key;
                if (k.get("kty").equals("RSA")) {
                    return k;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Handle Authorization Endpoint error
     *
     * @param request  The request from which to extract parameters and handle the
     *                 error
     * @param response The response, needed to do a redirect to display the error
     * @throws IOException If an input or output exception occurs
     */
    protected void handleError(HttpServletRequest request, HttpServletResponse response) {

        String error = request.getParameter("error");
        String errorDescription = request.getParameter("error_description");
        String errorURI = request.getParameter("error_uri");

        throw new AuthenticationServiceException("Error from Authorization Endpoint: " + error + " " + errorDescription + " " + errorURI);
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {

        SecurityContext context = SecurityContextHolder.getContext();
        return !(context != null && context.getAuthentication() != null);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }


    protected static void saveNonce(HttpSession session, Nonce nonce) {
        session.setAttribute(NONCE_SESSION_VARIABLE, nonce);
    }


    protected static Nonce getStoredNonce(HttpSession session) {
        return (Nonce) session.getAttribute(NONCE_SESSION_VARIABLE);
    }


    protected static void saveState(HttpSession session, State state) {
        session.setAttribute(STATE_SESSION_VARIABLE, state);
    }

    protected static State getStoredState(HttpSession session) {
        return (State) session.getAttribute(STATE_SESSION_VARIABLE);
    }
}

