package cloudfleet.cloudfleet.Security;

import cloudfleet.cloudfleet.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private  final UserRepository userRepository;


    @Bean
    public UserDetailsService userDetailsService(){
        return email-> userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->auth
                        // Public : connexion (pour la suite avec JWT)
                        .requestMatchers("/auth/**").permitAll()

                        // Endpoint temporaire de test (à supprimer ensuite)
                        .requestMatchers("/api/test/**").authenticated()

                        // Comptes : gérés par l'admin
                        .requestMatchers("/api/dispatchers/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/drivers/**").hasAnyRole("ADMIN", "DISPATCHER", "DRIVER")
                        .requestMatchers("/api/drivers/**").hasRole("ADMIN")

                        // Véhicules : position envoyée par le chauffeur, gestion par l'admin
                        .requestMatchers(HttpMethod.PATCH, "/api/vehicules/*/position").hasAnyRole("DRIVER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/vehicules/**").hasAnyRole("ADMIN", "DISPATCHER")
                        .requestMatchers("/api/vehicules/**").hasRole("ADMIN")

                        // Missions : créées par dispatcher/admin, consultées par tous
                        .requestMatchers(HttpMethod.POST, "/api/missions").hasAnyRole("ADMIN", "DISPATCHER")
                        .requestMatchers(HttpMethod.PATCH, "/api/missions/*/status").hasAnyRole("ADMIN", "DISPATCHER", "DRIVER")
                        .requestMatchers(HttpMethod.GET, "/api/missions/**").hasAnyRole("ADMIN", "DISPATCHER", "DRIVER")
                        .requestMatchers("/api/missions/**").hasAnyRole("ADMIN", "DISPATCHER")

                        // Suivi temps réel (WebSocket)
                        .requestMatchers("/ws/**").authenticated()

                        // Tout le reste est refusé
                        .anyRequest().denyAll()
                )
                .httpBasic(Customizer.withDefaults());


        return http.build();
    }


}
