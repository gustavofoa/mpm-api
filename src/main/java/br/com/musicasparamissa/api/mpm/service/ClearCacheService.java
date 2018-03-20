package br.com.musicasparamissa.api.mpm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ClearCacheService {

    @Value("${mpm_api.cache.token.key}")
    private String cacheTokenKey;
    @Value("${mpm_api.cache.token.value}")
    private String cacheTokenValue;
    @Value("${mpm_api.cache.url.all}")
    private String clearAllCache;
    @Value("${mpm_api.cache.url.one}")
    private String clearOneCache;

    @Autowired
    private RestTemplate restTemplate;

    public boolean all(){

        HttpHeaders headers = getHttpHeaders();

        HttpEntity<?> request = new HttpEntity<Object>(headers);
        ResponseEntity<String> response = restTemplate.exchange(clearAllCache, HttpMethod.DELETE, request, String.class);

        return response.getStatusCode() == HttpStatus.OK;

    }

    public boolean one(String url){

        HttpHeaders headers = getHttpHeaders();

        HttpEntity<?> request = new HttpEntity<Object>("urls[1]=" + url, headers);

        ResponseEntity<String> response = restTemplate.exchange(clearOneCache, HttpMethod.DELETE, request, String.class);
        return response.getStatusCode() == HttpStatus.ACCEPTED;

    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(cacheTokenKey, cacheTokenValue);
        return headers;
    }

}
