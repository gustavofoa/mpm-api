package br.com.musicasparamissa.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;

@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    private static final String SCOPE_READ = "READ";
    private static final String SCOPE_WRITE = "WRITE";
    private int expiration = 60 * 60;

    @Autowired
    private AuthenticationManager authenticationManager;

    private String mpmjadminAppClientId = "1234567";
    private String mpmjadminAppSecret = "123456789";
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
                .scopes(SCOPE_READ, SCOPE_WRITE)
                .accessTokenValiditySeconds(expiration)

                .and()

                .withClient(mpmMobileAppClientId)
                .secret(mpmMobileAppSecret)
                .authorizedGrantTypes(mpmMobileAppGrantType)
                .authorities(mpmMobileAppRole)
                .scopes(SCOPE_READ)

        ;
    }

}