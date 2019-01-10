package br.com.musicasparamissa.api.mpm.service;

import java.util.List;

public interface SiteStorage {

    void saveFile(String path, String content, String contentType);

    void saveMpmjadminFile(String path, byte[] contentBytes);

    List<String> listMpmjadminFile(String path);

}
