package br.com.musicasparamissa.api.mpm.service.impl;

import br.com.musicasparamissa.api.mpm.service.SiteStorage;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component("mpmSiteStorage")
public class S3SiteStorage implements SiteStorage {

    @Value("${mpm_api.aws.client_id}")
    private String clientId;
    @Value("${mpm_api.aws.client_secret}")
    private String clientSecret;

    @Value("${mpm_api.aws.s3.mpm.bucket}")
    private String bucketName;

    @Override
    public void saveFile(String path, String content, String contentType) {

        AWSCredentials credentials = new BasicAWSCredentials(clientId, clientSecret);

        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.SA_EAST_1)
                .build();

        try {
            log.debug("Uploading "+ path+" to MPM S3.");

            byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(contentBytes.length);
            metadata.setContentType(contentType);

            s3client.putObject(new PutObjectRequest(
                    bucketName, path, new ByteArrayInputStream(contentBytes), metadata));

        } catch (AmazonServiceException ase) {
            log.error("Caught an AmazonServiceException, which " +
                    "means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            log.error("Error Message:    " + ase.getMessage());
            log.error("HTTP Status Code: " + ase.getStatusCode());
            log.error("AWS Error Code:   " + ase.getErrorCode());
            log.error("Error Type:       " + ase.getErrorType());
            log.error("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            log.error("Caught an AmazonClientException, which " +
                    "means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            log.error("Error Message: " + ace.getMessage());
        }

    }

}
