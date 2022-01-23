package uk.ac.cardiff.mma.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

// Adapted from: https://erpsolutions.oodles.io/developer-blogs/Generating-and-downloading-PDF-using-spring-boot-and-Thymeleaf/
// Resolve html template.
@Configuration
public class ThymeleafConfiguration {

    @Bean
    public ClassLoaderTemplateResolver templateResolver(){
        ClassLoaderTemplateResolver loaderTemplateResolver = new ClassLoaderTemplateResolver();
        loaderTemplateResolver.setPrefix("templates/");
        loaderTemplateResolver.setTemplateMode("HTML5");
        loaderTemplateResolver.setSuffix(".html");
        loaderTemplateResolver.setTemplateMode("XHTML");
        loaderTemplateResolver.setCharacterEncoding("UTF-8");
        loaderTemplateResolver.setOrder(1);
        return loaderTemplateResolver;
    }

}