package com.quickcourier.quickcourier_api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Autowired
    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    /** 🔐 Configuración principal de seguridad */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desactiva CSRF (usamos JWT)
            .csrf(csrf -> csrf.disable())

            // Habilita CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // Política sin sesiones (stateless)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Reglas de autorización
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos de autenticación
                .requestMatchers("/api/auth/**").permitAll()

                // Swagger y documentación pública
                .requestMatchers(
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/api-docs/**"
                ).permitAll()

                // Permitir solicitudes OPTIONS (CORS preflight)
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // Ejemplo de endpoint público (opcional)
                .requestMatchers(HttpMethod.GET, "/api/quotes/calculate").permitAll()

                // Todo lo demás requiere autenticación
                .anyRequest().authenticated()
            )

            // Manejo de errores JSON
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((req, res, e) -> {
                    res.setContentType("application/json");
                    res.setStatus(401);
                    res.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"" + e.getMessage() + "\"}");
                })
                .accessDeniedHandler((req, res, e) -> {
                    res.setContentType("application/json");
                    res.setStatus(403);
                    res.getWriter().write("{\"error\": \"Forbidden\", \"message\": \"" + e.getMessage() + "\"}");
                })
            )

            // Añadir el filtro JWT antes del filtro de autenticación
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /** 🔑 Codificador de contraseñas */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** 🧠 Manager de autenticación */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /** 🌐 Configuración de CORS para frontend */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Orígenes permitidos (React, Angular, etc.)
        configuration.setAllowedOrigins(List.of(
            "http://localhost:3000",  // React
            "http://localhost:4200"   // Angular
        ));

        // Métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
        ));

        // Headers permitidos y expuestos
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));

        // Permitir credenciales (cookies o tokens)
        configuration.setAllowCredentials(true);

        // Registrar configuración
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    /** ⚙️ Personalización de seguridad web (sin firewall especial) */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // Desactiva modo debug del filtro, útil para logs limpios
        return (web) -> web.debug(false);
    }
}
