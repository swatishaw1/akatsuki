package com.example.akatsuki.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebConfig {


    private final UserDetailsService adminDetailsService;
    private final UserDetailsService userDetailsService;

    public WebConfig(@Qualifier("customAdminDetailsService") UserDetailsService adminDetailsService, @Qualifier("customUserDetailsService") UserDetailsService userDetailsService) {
        this.adminDetailsService = adminDetailsService;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request
                        // Public endpoints
                        .requestMatchers("/admins/login", "/admins/register", "/users/login", "/users/register")
                        .permitAll()

                        // Role-based access
                        .requestMatchers("/admins/**").hasRole("Admin") // ✅ no permitAll() here
                        .requestMatchers("/users/**").hasAnyRole("USER", "Admin") // ✅ allows both
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }



    //    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails swati= User.withUsername("swati").password("{noop}swati").roles("USER").build();
        UserDetails rima= User.withUsername("rima").password("{noop}rima").roles("USER").build();
        return new InMemoryUserDetailsManager(swati,rima);
    }

//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider providerUser = new DaoAuthenticationProvider();
//        providerUser.setUserDetailsService(userDetailsService);
////        providerUser.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
//        providerUser.setPasswordEncoder(bCryptPasswordEncoder());
//        return providerUser;
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(14);
    }

    @Bean
    public AuthenticationProvider adminDetailsService(){
        DaoAuthenticationProvider providerAdmin = new DaoAuthenticationProvider();
        providerAdmin.setUserDetailsService(adminDetailsService);
//        providerAdmin.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        providerAdmin.setPasswordEncoder(bCryptPasswordEncoder());
        return providerAdmin;
    }
}
