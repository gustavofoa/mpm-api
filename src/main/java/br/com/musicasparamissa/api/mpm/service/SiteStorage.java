package br.com.musicasparamissa.api.mpm.service;

import java.io.IOException;
import java.util.List;

public interface SiteStorage {

    void saveFile(String path, String content, String contentType);

    void saveMpmjadminFile(String path, byte[] contentBytes);

    List<String> listMpmjadminFile(String path);

    byte[] getMpmjadminFile(String path) throws IOException;

}
