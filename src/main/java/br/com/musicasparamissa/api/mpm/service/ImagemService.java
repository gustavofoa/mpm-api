package br.com.musicasparamissa.api.mpm.service;

import br.com.musicasparamissa.api.mpm.dto.Imagem;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ImagemService {

    @Value("${mpm_api.aws.client_id}")
    private String clientId;
    @Value("${mpm_api.aws.client_secret}")
    private String clientSecret;


    public Set<Imagem> list(String bucket) {

        Set<Imagem> files = new HashSet<>();

        AWSCredentials credentials = new BasicAWSCredentials(clientId, clientSecret);

        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();


        for(S3ObjectSummary os : s3client.listObjects(bucket).getObjectSummaries()) {
            Imagem file = new Imagem();
            file.setName(os.getKey());
            file.setSize(os.getSize());

            String[] path = file.getName().split("/");
//            if(path.length == 3 && path[1].equals("diasLiturgicos"))
            if(file.getName().contains("diasLiturgicos"))
                files.add(file);
        }

        return files;

    }
}
