package br.com.musicasparamissa.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;

@EntityScan(
        basePackageClasses = {MpMjAdminBackendApplication.class, Jsr310JpaConverters.class}
)
@SpringBootApplication
public class MpMjAdminBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpMjAdminBackendApplication.class, args);
    }

}
