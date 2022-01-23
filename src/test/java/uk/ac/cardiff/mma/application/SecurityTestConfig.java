package uk.ac.cardiff.mma.application;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@TestConfiguration
//@Order(1)
public class SecurityTestConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // Disable CSRF
        httpSecurity.csrf().disable()
        .authorizeRequests()
        .mvcMatchers("/*")
        .hasRole("USER")
        .mvcMatchers("/admin/*")
        .hasRole("ADMIN");
    }
}

