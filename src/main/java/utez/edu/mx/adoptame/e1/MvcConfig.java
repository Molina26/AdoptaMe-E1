package utez.edu.mx.adoptame.e1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.*;
import utez.edu.mx.adoptame.e1.handler.CustomLoginSuccessHandler;
import utez.edu.mx.adoptame.e1.handler.CustomLogoutSuccessHandler;
import utez.edu.mx.adoptame.e1.util.AppProperties;
import utez.edu.mx.adoptame.e1.util.GeneralInfoApp;
import utez.edu.mx.adoptame.e1.util.InfoMovement;

import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final AppProperties appProperties;

    public MvcConfig(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public static CustomLoginSuccessHandler loginSuccessHandler() {return new CustomLoginSuccessHandler();}

    @Bean
    public static CustomLogoutSuccessHandler customLogoutSuccessHandler() { return new CustomLogoutSuccessHandler();}

    @Bean(name = "AppProperties")
    public static AppProperties getAppProperties() {
        return new AppProperties();
    }

    @Bean
    public static InfoMovement getInfoMovement() {
        return new InfoMovement();
    }

    @Bean
    public static GeneralInfoApp getGeneralInfoApp() { return new GeneralInfoApp();}

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error_403").setViewName("error/error_403");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){

        String pathImages = appProperties.getImageSavePath() != null ?
                appProperties.getImageSavePath() :
                "";

        String resourcePath = Paths.get(pathImages).toAbsolutePath().toUri().toString();

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(resourcePath);
    }
}
