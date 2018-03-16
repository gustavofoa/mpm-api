package br.com.musicasparamissa.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableResourceServer
public class ResourceConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
            .cors().and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/stars/**")
            .permitAll()
            .anyRequest().authenticated()
        ;
    }

    @Bean
    public RestTemplate restTemplate() {

        RestTemplate restTemplate = new RestTemplate();

//        addConverters(restTemplate);

        return restTemplate;
    }

}
