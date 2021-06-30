package br.com.musicasparamissa.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    private static final String SCOPE_READ = "READ";
    private static final String SCOPE_WRITE = "WRITE";
    private int expiration = 60 * 60;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${mpm_api.apps.mpmjadmin.client_id}")
    private String mpmjadminAppClientId;
    @Value("${mpm_api.apps.mpmjadmin.client_secret}")
    private String mpmjadminAppSecret;
    @Value("${mpm_api.apps.mpmjadmin.grant_type}")
    private String mpmjadminAppGrantType;
    @Value("${mpm_api.apps.mpmjadmin.role}")
    private String mpmjadminAppRole;

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


        ;
    }

}