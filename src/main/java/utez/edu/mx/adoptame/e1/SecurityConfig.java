package utez.edu.mx.adoptame.e1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import utez.edu.mx.adoptame.e1.handler.CustomLoginSuccessHandler;
import utez.edu.mx.adoptame.e1.handler.CustomLogoutSuccessHandler;
import utez.edu.mx.adoptame.e1.service.JpaUserDetailService;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JpaUserDetailService detailService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final CustomLoginSuccessHandler successHandler;

    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    public SecurityConfig(JpaUserDetailService detailService, BCryptPasswordEncoder bCryptPasswordEncoder, CustomLoginSuccessHandler successHandler, CustomLogoutSuccessHandler customLogoutSuccessHandler) {
        this.detailService = detailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.successHandler = successHandler;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(detailService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/uploads/**", "/", "/index", "/user/register", "/icons/**","/user/createAccount", "/images/**", "/blog/general", "/blog/find_details_blog/{id}/{flag}", "/create-acount")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .successHandler(successHandler)
                    .loginPage("/login")
                    .permitAll()
                .and()
                    .logout()
                    .logoutSuccessHandler(customLogoutSuccessHandler)
                    .permitAll()
                .and()
                    .exceptionHandling().accessDeniedPage("/error_403");
    }
}
