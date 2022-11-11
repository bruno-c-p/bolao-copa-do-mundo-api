package live.bolaocopadomundo.api.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Value("cors.site")
    private String corsUrl;

    @Autowired
    private AuthenticationTokenFilter authenticationTokenFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        final var corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedOriginPatterns(List.of(corsUrl));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "PUT", "OPTIONS", "PATCH", "DELETE"));
        corsConfiguration.setExposedHeaders(List.of("Authorization"));
        corsConfiguration.setAllowCredentials(true);

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/logs").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/users/**/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/matches/**/finish").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers(HttpMethod.POST, "/users/**").permitAll()
                .antMatchers(HttpMethod.GET, "/users/exists/**").permitAll()
                .antMatchers(HttpMethod.PATCH, "/users/password-reset").permitAll()
                .antMatchers(HttpMethod.POST, "/users/password-reset-confirm").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .cors().configurationSource(request -> corsConfiguration).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/actuator/**");
    }
}
