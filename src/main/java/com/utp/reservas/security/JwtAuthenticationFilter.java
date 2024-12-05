package com.utp.reservas.security;

import com.utp.reservas.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            String header = request.getHeader("Authorization");

            if (header == null || !header.startsWith("Bearer ")) {
                chain.doFilter(request, response);
                return;
            }

            String token = header.replace("Bearer ", "");

            // Validar y obtener Claims del token
            Claims claims = JwtUtil.obtenerClaims(token);

            String username = claims.getSubject();
            String rol = claims.get("rol", String.class); // Obtén el rol del token

            if (username != null && rol != null) {
                // Configura el rol en el contexto de Spring Security
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + rol.toUpperCase());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.singletonList(authority)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            chain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("El token ha expirado");
        } catch (MalformedJwtException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write("El token es inválido");
        } catch (SignatureException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("El token tiene una firma inválida");
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write("Error al procesar el token");
        }
    }
}
