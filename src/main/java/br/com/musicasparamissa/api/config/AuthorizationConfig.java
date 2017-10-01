package br.com.musicasparamissa.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAutoConfiguration
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    private static final String RESOURCE_ID = "mpmjadmin_api";
    private static final String SCOPE = "mpmjadmin_api";

    @Autowired
    private AuthenticationManager authenticationManager;

    private String mpmjadminAppClientId = "123456";
    private String mpmjadminAppSecret = "12345678";
    private String mpmjadminAppGrantType = "client_credentials";
    private String mpmjadminAppRole = "FULL";

    private String mpmMobileAppClientId = "123456";
    private String mpmMobileAppSecret = "12345678";
    private String mpmMobileAppGrantType = "client_credentials";
    private String mpmMobileAppRole = "READ";

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()

                .withClient(mpmjadminAppClientId)
                .secret(mpmjadminAppSecret)
                .authorizedGrantTypes(mpmjadminAppGrantType)
                .authorities(mpmjadminAppRole)
                .resourceIds(RESOURCE_ID)
                .scopes(SCOPE)

                .and()

                .withClient(mpmMobileAppClientId)
                .secret(mpmMobileAppSecret)
                .authorizedGrantTypes(mpmMobileAppGrantType)
                .authorities(mpmMobileAppRole)
                .resourceIds(RESOURCE_ID)
                .scopes(SCOPE)

        ;
    }

}