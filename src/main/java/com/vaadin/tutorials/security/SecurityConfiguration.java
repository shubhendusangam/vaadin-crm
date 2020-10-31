package com.vaadin.tutorials.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

   private static final String LOGIN_PROCESSING_URL = "/login";
   private static final String LOGIN_FAILURE_URL = "/login?error";
   private static final String LOGIN_URL = "/login";
   private static final String LOGOUT_SUCCESS_URL = "/login";

   // Add a method to block unauthenticated requests to all pages, except the login page.
   @Override
   protected void configure(HttpSecurity http) throws Exception {
      // Disables cross-site request forgery (CSRF) protection, as Vaadin already has CSRF protection
      http.csrf().disable()

            // Uses CustomRequestCache to track unauthorized requests so that users are redirected appropriately after login.
            .requestCache().requestCache(new CustomRequestCache())

            // Turns on authorization.
            .and().authorizeRequests()

            // Allows all internal traffic from the Vaadin framework.
            .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

            // Allows all authenticated traffic.
            .anyRequest().authenticated()

            // Enables form-based login and permits unauthenticated access to it.
            .and().formLogin()
            .loginPage(LOGIN_URL).permitAll()

            // Configures the login page URLs.
            .loginProcessingUrl(LOGIN_PROCESSING_URL)
            .failureUrl(LOGIN_FAILURE_URL)

            // Configures the logout URL.
            .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
   }


   @Bean
   @Override
   public UserDetailsService userDetailsService() {
      UserDetails user =
            User.withUsername("user")
                  .password("{noop}password")
                  .roles("USER")
                  .build();

      return new InMemoryUserDetailsManager(user);
   }

   @Override
   public void configure(WebSecurity web) {
      web.ignoring().antMatchers(
            "/VAADIN/**",
            "/favicon.ico",
            "/robots.txt",
            "/manifest.webmanifest",
            "/sw.js",
            "/offline.html",
            "/icons/**",
            "/images/**",
            "/styles/**",
            "/h2-console/**");
   }


}