//package com.example.starchive.config.jwt;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.InternalAuthenticationServiceException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    private static final ObjectMapper mapper = new ObjectMapper();
//    private final JwtProcess jwtProcess;
//    private AuthenticationManager authenticationManager;
//
//    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtProcess jwtProcess) {
//        super(authenticationManager);
//        setFilterProcessesUrl("/api/manager/login"); // 고객 로그인 URL 지정
//        this.authenticationManager = authenticationManager;
//        this.jwtProcess = jwtProcess;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
//
//        try {
//            ManagerLoginReqDto loginReqDto = mapper.readValue(request.getInputStream(), ManagerLoginReqDto.class);
//
//            // 강제 로그인
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginReqDto.getEmail(), loginReqDto.getPassword());
//            // UserDetailsService.LoadByUsername 호출
//            // JWT를 쓴다고 하더라도, 컨트롤러에 진입을 하면 시큐리티 권한 체크, 인증 체크의 도움을 받을 수 있게 세션을 만든다.
//            // 이 세션의 유효기간은 request ~ response 까지이다.
//
//            Authentication authentication = authenticationManager.authenticate(authenticationToken);
//
//            requestBodyHolder.set(loginReqDto);
//
//            return authentication;
//        } catch (Exception e) {
//            // JwtAuthenticationFilter.unsuccessfulAuthentication 메서드 호출
//            throw new InternalAuthenticationServiceException(e.getMessage());
//        }
//    }
//
//
//}
