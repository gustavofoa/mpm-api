package br.com.musicasparamissa.mpmjadmin.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EntityScan(
        basePackageClasses = {MpMjAdminBackendApplication.class, Jsr310JpaConverters.class}
)
@SpringBootApplication
public class MpMjAdminBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpMjAdminBackendApplication.class, args);
    }

}
