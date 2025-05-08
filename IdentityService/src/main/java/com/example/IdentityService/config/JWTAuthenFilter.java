//package com.example.IdentityService.config;
//
//import com.example.IdentityService.service.JWTService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Configuration
//public class JWTAuthenFilter extends OncePerRequestFilter {
//
//    private final JWTService jwtService;
//
//    @Autowired
//    public JWTAuthenFilter(JWTService jwtService) {
//        this.jwtService = jwtService;
//    }
//    private final String[] PUBLIC_ENDPOINTS = {
//            "/identity/auth/login","/identity/home","/identity/home/login","/identity/tonkho","/identity/images/**"
//    };
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ServletException, IOException {
//
//
//        for (String url : PUBLIC_ENDPOINTS) {
//
//            if (request.getRequestURI().equals(url)) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//        }
//            String authHeader = request.getHeader("Authorization");
//
//
//            if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                filterChain.doFilter(request,response);
//                return;
//            }
//
//            String jwt = authHeader.substring(7);
//
//            String username;
//            username = jwtService.getUserName();
//
//
//            List<String> scopes = jwtService.getScopes();  // Giả sử bạn đã lấy được List<String>
//
//            List<SimpleGrantedAuthority> authorities = scopes.stream()
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(Collectors.toList());
//
//            UsernamePasswordAuthenticationToken authentication =
//                    new UsernamePasswordAuthenticationToken(username, null, authorities);
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            filterChain.doFilter(request, response);
//    }
//}
