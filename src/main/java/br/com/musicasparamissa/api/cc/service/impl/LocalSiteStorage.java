package br.com.musicasparamissa.api.cc.service.impl;

import br.com.musicasparamissa.api.cc.service.SiteStorage;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

//@Component("siteStorage")
public class LocalSiteStorage implements SiteStorage {

    private static String localFolder = "C:/temp/cc";

    @Override
    public void saveFile(String path, String content) {

        try {

            //create folders

            File rootFolder = new File(localFolder);
            if(!rootFolder.exists())
                rootFolder.mkdir();

            File artistaFolder = new File(localFolder+"/"+(path.substring(0,path.indexOf("/"))));
            if(!artistaFolder.exists())
                artistaFolder.mkdir();

            File musicaFolder = new File(localFolder+"/"+(path.substring(0,path.lastIndexOf("/"))));
            if(!musicaFolder.exists())
                musicaFolder.mkdir();



            PrintStream out = new PrintStream(localFolder+"/"+path);
            out.append(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to write file.", e);
        }

    }

}
