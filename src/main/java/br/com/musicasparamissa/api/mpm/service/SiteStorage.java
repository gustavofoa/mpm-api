package br.com.musicasparamissa.api.mpm.service;

public interface SiteStorage {

    void saveFile(String path, String content, String contentType);

}
