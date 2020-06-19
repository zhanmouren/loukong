package com.koron.main;

import com.koron.common.UserResolver;
import com.koron.common.stub.ConfigCenter;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.MultipartConfigElement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 该注解指定项目为springboot，由此类当作程序入口 自动装配 web 依赖的环境
 **/
@EnableScheduling
@SpringBootApplication
@ComponentScan(value = {"org.swan", "com.koron", "com.koron.main"})
public class App extends WebMvcConfigurationSupport {
    public static void main(String[] args) throws IOException {
        SpringApplication app = new SpringApplication(App.class);
        if (System.getenv("config_center_url") != null)
            app.setDefaultProperties(ConfigCenter.getProperties(System.getenv("tarant"), System.getenv("app")));
        Banner b = (environment, sourceClass, printStream) -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(App.class.getClassLoader().getResourceAsStream("logo.txt")));
            String tmp = null;
            try {
                while ((tmp = reader.readLine()) != null)
                    printStream.println(tmp);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            printStream.print("App version is:" + AnsiOutput.toString(AnsiColor.RED, environment.getProperty("app.version")) + "  ");
            String version = SpringBootVersion.getVersion();
            version = (version == null ? "" : " (v" + version + ")");
            printStream.println(AnsiOutput.toString(AnsiColor.GREEN, "Spring Boot:" + version));
        };
        app.setBanner(b);
        app.run(args);
    }

    /**
     * 上传附件容量限制
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("102400KB");
        factory.setMaxRequestSize("112400KB");
        return factory.createMultipartConfig();
    }

    /*
     * 装载session
     * (non-Javadoc)
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport#addArgumentResolvers(java.util.List)
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new UserResolver());
        //argumentResolvers.add(new PersonResolver());
    }


    /**
     * 解决浏览器中文乱码问题
     *
     * @return
     */
    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }

    @Bean
    public InternalResourceViewResolver setupViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setSuffix(".html");
        return resolver;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
        converters.add(new ByteArrayHttpMessageConverter());

    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/{^[A-Za-z0-9]+$}/**").addResourceLocations("classpath:/static/");
        //registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

}