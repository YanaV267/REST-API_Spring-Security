package com.epam.esm.jwt;

import com.epam.esm.service.impl.UserServiceImpl;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Jwt authentication filter.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String HEADER_KEY = "Authorization";
    private static final String JWT_HEADER_PREFIX = "Bearer ";
    @Autowired
    private JwtManagingUtil jwtUtil;
    @Autowired
    private UserServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = parseToken(request);
        try {
            String username = jwtUtil.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!token.isEmpty() && Boolean.TRUE.equals(jwtUtil.validateToken(token, userDetails))) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (IllegalArgumentException | SignatureException exception) {
            logger.error("No valid JWT was specified.");
        }
        filterChain.doFilter(request, response);
    }

    private String parseToken(HttpServletRequest request) {
        String header = request.getHeader(HEADER_KEY);
        if (StringUtils.hasText(header) && header.startsWith(JWT_HEADER_PREFIX)) {
            return header.substring(7);
        } else {
            return "";
        }
    }
}
