package com.example.agiosandreas.config;

import com.example.agiosandreas.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
@EnableWebMvc
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtFilter jwtFilter;

    private final UserService userService;




    private static final String[] WHITE_LIST_URL={"/login","/register","/authenticate"};

    public SecurityConfig(AuthenticationProvider authenticationProvider, JwtFilter jwtFilter, UserService userService) {

        this.authenticationProvider = authenticationProvider;
        this.jwtFilter = jwtFilter;
        this.userService = userService;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
       return http.
               requiresChannel(chanel->chanel.anyRequest().requiresSecure()).
               x509(
                       x509->x509.subjectPrincipalRegex("CN=(.*)"))
               .userDetailsService(userService).
               cors(
                       (cors)->cors.configurationSource(corsFilterSource())
               ).
               csrf(AbstractHttpConfigurer ::disable)
                .authorizeRequests(req->req.requestMatchers(HttpMethod.POST,"/api/users/login","/api/users/register")
                        .permitAll()
                        .anyRequest().authenticated()
                )
               .httpBasic(Customizer.withDefaults())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .authenticationProvider(authenticationProvider)
               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
               .build();


    }

    @Bean
    public CorsConfigurationSource corsFilterSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT));
        config.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name()));
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", config);

        return source;
    }



}
