package sec.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
// @EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true) // to fix A6 this needs to enabled
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        // remove csrf-check because of h2-console
        http.csrf().disable(); 
        http.headers().frameOptions().sameOrigin();      
        
        // no real security at the moment
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/form").permitAll() 
                .antMatchers("/done").hasAuthority("USER")
                .antMatchers("/feedback").hasAuthority("USER")
                .antMatchers("/register").hasAuthority("ADMIN")
                .antMatchers("/h2-console/**").permitAll()     // remove to fix A5      
                .anyRequest().authenticated();
//                .anyRequest().permitAll();

        http.formLogin()
                .permitAll().and()
                .logout().permitAll();              
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
