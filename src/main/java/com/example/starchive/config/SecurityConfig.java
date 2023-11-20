package com.example.starchive.config;

import com.example.starchive.config.jwt.JwtAuthorizationFilter;
import com.example.starchive.config.jwt.JwtProcess;
import com.example.starchive.entity.Role;
import com.example.starchive.util.CustomResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtProcess jwtProcess;

    @Bean // IOC 컨테이너에 BCryptPasswordEncoder 객체 등록
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // JWT 필터 생성
    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            // 필터 생성!
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            //builder.addFilter(new JwtAuthenticationFilter(authenticationManager, jwtProcess));
            builder.addFilter(new JwtAuthorizationFilter(authenticationManager, jwtProcess));
            super.configure(builder);
        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // iframe 허용 안함
        http.headers().frameOptions().disable();
        // CSRF 공격 허용 → Postman 사용을 위해
        http.csrf().disable();
        http.cors().configurationSource(configurationSource());
        // JSession id를 서버쪽에서 관리하지 않는다.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // form 으로 전달받지 않고, 외부 요청(react, android, ...)만 받는다.
        http.formLogin().disable();
        // httpBasic()은 브라우저가 팝업창을 이용해서 사용자 인증을 진행한다.
        http.httpBasic().disable();

        // JWT 필터 등록
        http.apply(new CustomSecurityFilterManager());

        // 인증 실패
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            CustomResponseUtil.fail(response, "로그인을 진행해 주세요", HttpStatus.UNAUTHORIZED);
        });

        // 권한 실패
        http.exceptionHandling().accessDeniedHandler(((request, response, e) -> {
            CustomResponseUtil.fail(response, "권한이 없습니다.", HttpStatus.FORBIDDEN);
        }));

        http.authorizeRequests()
                // swagger 모두 허용
                .antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                // oauth2 모두 허용
                .antMatchers("/login/oauth2/code/**", "/api/users/login/**").permitAll()
                // cafe manager join and login
                .antMatchers("/api/admin/**").hasRole(Role.ADMIN.toString())
                .anyRequest().authenticated();

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*"); // 모든 헤더 허용
        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용(*) → 프론트 IP 주소만 허용(?)
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader("Authorization"); // 서버에서 제공하는 response 의 헤더 "Authorization" 에 대한 접근을 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
