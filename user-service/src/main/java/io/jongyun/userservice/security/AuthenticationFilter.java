package io.jongyun.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jongyun.userservice.dto.RequestLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author jongyunha created on 22. 3. 24.
 */
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException {
        try {
            RequestLogin creds =
                new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(),
                    new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authResult)
        throws IOException, ServletException {
        log.info(((User) authResult.getPrincipal()).getUsername());
        log.info(((User) authResult.getPrincipal()).getPassword());
    }
}