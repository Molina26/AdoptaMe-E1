package utez.edu.mx.AdoptaMe_E1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import utez.edu.mx.AdoptaMe_E1.handler.CustomLoginSuccessHandler;
import utez.edu.mx.AdoptaMe_E1.handler.CustomLogoutSuccessHandler;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public static CustomLoginSuccessHandler loginSuccessHandler() {return new CustomLoginSuccessHandler();}

    @Bean
    public static CustomLogoutSuccessHandler customLogoutSuccessHandler() { return new CustomLogoutSuccessHandler();}

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error_403").setViewName("error/error_403");
    }
}
