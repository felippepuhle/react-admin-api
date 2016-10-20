package br.com.felippepuhle.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.filter.GenericFilterBean;

public class JWTFilter extends GenericFilterBean {

    @Override
    public void doFilter(final ServletRequest req,
            final ServletResponse res,
            final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;

        if (!request.getRequestURI().contains("/login")) {
            try {
                final String token = this.getTokenFromHeader(request);

                final Claims claims = Jwts.parser()
                        .setSigningKey(JWTConfig.JWT_KEY)
                        .parseClaimsJws(token)
                        .getBody();

                request.setAttribute("claims", claims);
            } catch (final SignatureException e) {
                throw new ServletException("Invalid token.");
            }
        }

        chain.doFilter(req, res);
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Missing or invalid Authorization header.");
        }

        return authHeader.substring(7);
    }

}
