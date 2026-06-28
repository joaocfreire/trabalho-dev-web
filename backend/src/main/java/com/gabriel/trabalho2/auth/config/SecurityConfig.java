package com.gabriel.trabalho2.auth.config;

import com.gabriel.trabalho2.auth.filter.JwtAuthenticationFilter;
import com.gabriel.trabalho2.auth.service.UsuarioDetailsService;
import com.gabriel.trabalho2.auth.util.Role;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioDetailsService usuarioDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        // Permite que requisições de origens diferentes enviem credenciais.
        // Permite o envio de informações de autenticação, cookies, etc em requisições CORS.
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // / é a raiz do servidor e /** é um padrão de PathMatch que indica todas as rotas e subrotas.
        // Isto é, qq requisição será atendida por esta por esta configuração.
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        System.out.println("***************** Executou o método securityFilterChain de SecurityFilterChain");
        httpSecurity
            .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Cross Site Request Forgery - Um tipo de ataque utilizado em session based autentication
            // Em aplicações restful, como este tipo de ataque não acontece, deve ser desabilitado por questão
            // de desempenho. Na linha abaixo é possível mudar para method reference.
            .csrf(c -> c.disable())
            .cors(c -> c.configurationSource(corsConfigurationSource()))

            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.GET, "/alunos/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/alunos/**").hasRole(Role.ADMIN.name())
                .requestMatchers(HttpMethod.PUT, "/alunos/**").hasRole(Role.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/alunos/**").hasRole(Role.ADMIN.name())

                // GET é público, POST/PUT/DELETE requerem ADMIN
                 .requestMatchers(HttpMethod.GET, "/professores/**").permitAll()
                 .requestMatchers(HttpMethod.POST, "/professores/**").hasRole(Role.ADMIN.name())
                 .requestMatchers(HttpMethod.PUT, "/professores/**").hasRole(Role.ADMIN.name())
                 .requestMatchers(HttpMethod.DELETE, "/professores/**").hasRole(Role.ADMIN.name())

                 // INSCRICAO - GET é público, POST/PUT/DELETE requerem ADMIN
                 .requestMatchers(HttpMethod.GET, "/inscricoes/**").permitAll()
                 .requestMatchers(HttpMethod.POST, "/inscricoes/**").hasRole(Role.ADMIN.name())
                 .requestMatchers(HttpMethod.PUT, "/inscricoes/**").hasRole(Role.ADMIN.name())
                 .requestMatchers(HttpMethod.DELETE, "/inscricoes/**").hasRole(Role.ADMIN.name())

                 // DISCIPLINA - GET é público, POST/PUT/DELETE requerem ADMIN
                 .requestMatchers(HttpMethod.GET, "/disciplinas/**").permitAll()
                 .requestMatchers(HttpMethod.POST, "/disciplinas/**").hasRole(Role.ADMIN.name())
                 .requestMatchers(HttpMethod.PUT, "/disciplinas/**").hasRole(Role.ADMIN.name())
                 .requestMatchers(HttpMethod.DELETE, "/disciplinas/**").hasRole(Role.ADMIN.name())


                // qq usuário pode cadastrar um usuário
                .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()

                // qq usuário pode se logar
                .requestMatchers(HttpMethod.POST, "/autenticacao/login").permitAll()

                .anyRequest().authenticated())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(c -> {
                // Quando o usuário não está logado, por default, é retornado o erro 403 - FORBIDDEN
                // Estamos mudando para 401 - UNAUTHORIZED
                c.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                // Quando o usuário está autenticado mas não possui o perfil (ROLE) necessário para
                // acessar o recurso, por default é retornado o erro 401 - UNAUTHORIZED.
                // Estamos mudando para 403 - FORBIDDEN
                c.accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                });
            });

        return httpSecurity.build();  // Cria um objeto SecurityFilterChain.
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("1. ***** Executou o método passwordEncoder()");
        // BCryptPasswordEncoder é o algoritmo recomendado para efetuar o hash das senhas.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        System.out.println("3. ***** Executou authenticationProvider()");
        var provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(usuarioDetailsService);
        return provider;
    }

    // Quando tentarmos efetuar um login através de AuthenticationController, o método
    // authenticate() de AuthenticationManager vai chamar o método authenticate() de um
    // AuthenticationProvider (no nosso caso, DaoAuthenticationProvider), que irá chamar
    // o método loadUserByUsename() de UsuarioService (a classe que implementa a interface
    // UserDetailsService - que possui o método loadUserByUsername()).

    // O DaoAuthenticationProvider irá chamar o método loadUserByUsername() de
    // usuarioService para recuperar do banco de dados a senha do usuário. Em seguida ele
    // usa o passwordEncoder para criptografar a senha recebida do usuário e a compra com
    // a senha recuperada do banco de dados.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
        throws Exception {
        System.out.println("2. ***** Executou authenticationManager()");
        return config.getAuthenticationManager();
    }

}
