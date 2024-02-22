package hongblinddate.backend.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import hongblinddate.backend.common.jwt.filter.JwtAuthenticationProcessingFilter;
import hongblinddate.backend.common.jwt.service.JwtService;
import hongblinddate.backend.common.login.filter.AccountPasswordAuthenticationFilter;
import hongblinddate.backend.common.login.handler.LoginFailureHandler;
import hongblinddate.backend.common.login.handler.LoginSuccessHandler;
import hongblinddate.backend.common.login.service.LoginService;
import hongblinddate.backend.domain.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoginService loginService;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;

    private static final String[] ALLOWED_PATTERN = new String[] {
            "/api/auth/login/**",
            "/api/member/join",
            "/api/member/duplicate/**",
            "/api/auth/logout/**",
    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable) // csrf 보안 사용 X
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .authorizeRequests((auth) -> auth
                                .requestMatchers(Stream.of(ALLOWED_PATTERN)
                                        .map(AntPathRequestMatcher::antMatcher)
                                        .toArray(AntPathRequestMatcher[]::new)).permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMINISTRATOR")
                                .anyRequest().authenticated()
                        );

        http.addFilterAfter(accountPasswordAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), AccountPasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(loginService);
        return new ProviderManager(provider);
    }

    @Bean
    public AccountPasswordAuthenticationFilter accountPasswordAuthenticationFilter() {
        AccountPasswordAuthenticationFilter accountPasswordAuthenticationFilter
                = new AccountPasswordAuthenticationFilter(objectMapper);
        accountPasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
        accountPasswordAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        accountPasswordAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler);
        return accountPasswordAuthenticationFilter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        return new JwtAuthenticationProcessingFilter(jwtService, memberRepository);
    }
}
