package br.com.musicasparamissa.api.mpm.service.impl;

import br.com.musicasparamissa.api.mpm.service.SiteStorage;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

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

}
