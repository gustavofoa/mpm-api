package br.com.musicasparamissa.api.cc.service.impl;

import br.com.musicasparamissa.api.cc.service.SiteStorage;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

@Component("siteStorage")
public class S3SiteStorage implements SiteStorage {

    @Value("${mpm_api.aws.client_id}")
    private String clientId;
    @Value("${mpm_api.aws.client_secret}")
    private String clientSecret;

    @Value("${mpm_api.aws.s3.cc.bucket}")
    private String bucketName;


    @Override
    public void saveFile(String path, String content) {

        AWSCredentials credentials = new BasicAWSCredentials(clientId, clientSecret);

        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.SA_EAST_1)
                .build();

        try {
            System.out.println("Uploading a new object to S3 from a file\n");

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(content.length());;
            metadata.setContentType("text/html");

            s3client.putObject(new PutObjectRequest(
                    bucketName, path, new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), metadata));

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
                    "means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
                    "means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }

    }

}
