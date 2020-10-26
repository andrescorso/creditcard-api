package com.andrescorso.cardsAPI.security;

import com.andrescorso.cardsAPI.models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private final String JWTSECRET = "NuvuSecretKey";
    // Token Expiration 15 minutes
    //private final Integer JWTEXPIRATIONMS = 900_000;
    // Token Expiration 1 day
    private final Long JWTEXPIRATIONMS = 24*60*60*1000L;
    private final String LOGINURL = "/api/v1/user/login";



    public JWTAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(LOGINURL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res){
        try {
            User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (AuthenticationException e){
            try {
                res.setContentType("application/json");
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.getWriter().write("{ \"error\": \"Wrong User-Password\" }");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return null;
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException{

        String token = JWT.create()
                .withSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWTEXPIRATIONMS))
                .sign(Algorithm.HMAC512(JWTSECRET.getBytes()));

        String username = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
        res.setContentType("application/json");
        res.getWriter().write("{\"user\": \""+username+"\", \n \"token_type\":\"Bearer\", \n \"token\":\""+token+"\"}");
        res.getWriter().flush();
    }
}
