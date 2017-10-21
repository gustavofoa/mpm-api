package br.com.musicasparamissa.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QueryService {
    public Page<Map<String, String>> query(String query) {
        return null;
    }
}
