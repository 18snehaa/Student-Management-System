package in.mindcraft.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import in.mindcraft.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getRequestURI();

        // JWT not required for login
        if (path.equals("/auth/login")) {
            return true;
        }

        // JWT not required for Task 1
        if (path.equals("/external-api/task1")) {
            return true;
        }

        // JWT not required for Task 2
        if (path.equals("/external-api/task2")) {
            return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader =
                request.getHeader("Authorization");

        // JWT is compulsory for Task 3 and /external-api/user
        if (authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ")) {

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter().write(
                    "Authorization Bearer token is required");

            return;
        }

        String token =
                authorizationHeader.substring(7);

        if (!jwtService.isTokenValid(token)) {

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter().write(
                    "Invalid or expired JWT token");

            return;
        }

        // Token is valid
        filterChain.doFilter(request, response);
    }
}