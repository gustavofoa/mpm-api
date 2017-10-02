package br.com.musicasparamissa.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EntityScan(
        basePackageClasses = {MpMApiApplication.class, Jsr310JpaConverters.class}
)
@SpringBootApplication
public class MpMApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpMApiApplication.class, args);
    }

}
