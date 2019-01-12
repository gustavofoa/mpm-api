package br.com.musicasparamissa.api.mpm.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface SiteStorage {

    void saveFile(String path, String content, String contentType);

    void saveMpmjadminFile(String path, InputStream contentBytes, long size);

    List<String> listMpmjadminFile(String path);

    void getMpmjadminFile(String path, HttpServletResponse response) throws IOException;

}
