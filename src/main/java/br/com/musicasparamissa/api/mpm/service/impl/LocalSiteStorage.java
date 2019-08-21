package br.com.musicasparamissa.api.mpm.service.impl;

import br.com.musicasparamissa.api.mpm.service.SiteStorage;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

//@Component("mpmSiteStorage")
public class LocalSiteStorage implements SiteStorage {

    private static String localFolder = "C:/temp/mpm";

    @Override
    public void saveFile(String path, String content, String contentType) {

        try {

            //create folders

            File rootFolder = new File(localFolder);
            if(!rootFolder.exists())
                rootFolder.mkdir();



            PrintStream out = new PrintStream(localFolder+"/"+path);
            out.append(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to write file.", e);
        }

    }

    @Override
    public void saveMpmjadminFile(String path, InputStream in, long size) {

    }

    @Override
    public void saveMympmFile(String path, InputStream in, long size) {

    }

    @Override
    public List<String> listMpmjadminFile(String path) {
        return null;
    }

    @Override
    public void getMpmjadminFile(String path, HttpServletResponse response) throws IOException {

    }

}
