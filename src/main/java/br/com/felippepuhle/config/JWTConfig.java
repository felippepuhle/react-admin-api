package br.com.felippepuhle.config;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfig {

    public static final String JWT_KEY = "secretkey";
    public static final SignatureAlgorithm JWT_ALGORITHM = SignatureAlgorithm.HS256;

    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JWTFilter());
        registrationBean.addUrlPatterns("/admin/*");

        return registrationBean;
    }
}
