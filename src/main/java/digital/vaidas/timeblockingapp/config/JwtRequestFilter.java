//package digital.vaidas.timeblockingapp.config;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Collections;
//
//@Component
//public class JwtRequestFilter extends OncePerRequestFilter {
//
//    private final JwtTokenUtil jwtTokenUtil;
//
//    public JwtRequestFilter(JwtTokenUtil jwtTokenUtil) {
//        this.jwtTokenUtil = jwtTokenUtil;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//
//        final String authorizationHeader = request.getHeader("Authorization");
//
//        String userId = null;
//        String jwt = null;
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            jwt = authorizationHeader.substring(7);
//            try {
//                userId = jwtTokenUtil.getUserIdFromToken(jwt);
//            } catch (Exception e) {
//                logger.error("Could not get userId from token", e);
//            }
//        }
//
//        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            if (jwtTokenUtil.validateToken(jwt)) {
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userId, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//        chain.doFilter(request, response);
//    }
//}