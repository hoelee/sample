package com.hoelee.demo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.DefaultTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * <p>Class desc:</p>
 *
 *
 * @version        v2, 2020-09-30 12:01:49AM
 * @author         hoelee
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties
@ComponentScan({"com.hoelee.demo.demo.controller", "com.hoelee.demo.demo.helper"})
public class DemoApplication {

    private static TemplateEngine templateEngine;

    /** hoelee v2 2020-09-30 12:01:49AM
     * <p>Method desc:</p>
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication  springApplication = new SpringApplication();
        ApplicationContext appCtx            = springApplication.run(DemoApplication.class, args);
    }
    /** hoelee v2 2020-09-30 03:54:52AM
     * <p>Method desc:</p>
     *
    @Bean
    public TemplateResolver templateResolver() {
        TemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        //templateResolver.setCacheable(false);
        //templateResolver.setTemplateMode("XHTML"); // Default
        templateResolver.setPrefix("/WEB-INF/thymeleaf/");
        templateResolver.setCacheTTLMs(3600000L);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        return templateResolver;
    }

     * @return
    private static void initializeTemplateEngine() {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();

        // XHTML is the default mode, but we set it anyway for better understanding of code
        templateResolver.setTemplateMode("XHTML");

        // This will convert "home" to "/WEB-INF/templates/home.html"
        templateResolver.setPrefix("/WEB-INF/thymeleaf/");
        templateResolver.setSuffix(".html");

        // Template cache TTL=1h. If not set, entries would be cached until expelled by LRU
        templateResolver.setCacheTTLMs(3600000L);

        //templateResolver.setCacheable(false);
        templateEngine = new TemplateEngine();

        templateEngine.setTemplateResolver(templateResolver);
    }
     */
}

//~ v2, 2020-09-30 05:26:41AM - Last edited by hoelee
