package br.com.musicasparamissa.api.mpm.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ImagemService {

    @Value("${mpm_api.aws.client_id}")
    private String clientId;
    @Value("${mpm_api.aws.client_secret}")
    private String clientSecret;


    public Set<String> list(String bucket) {

        Set<String> files = new HashSet<>();

        AWSCredentials credentials = new BasicAWSCredentials(clientId, clientSecret);

        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.SA_EAST_1)
                .build();


        ObjectListing images = s3client.listObjects(bucket, "images/diasLiturgicos");
        List<S3ObjectSummary> summaries = images.getObjectSummaries();

        while (images.isTruncated()) {
            images = s3client.listNextBatchOfObjects(images);
            for(S3ObjectSummary os : images.getObjectSummaries()) {

                if(!os.getKey().contains("diasLiturgicos/300") && !os.getKey().contains("diasLiturgicos/80"))
                    files.add(os.getKey().substring(os.getKey().lastIndexOf("/")+1));
            }
        }

        return files;

    }
}
