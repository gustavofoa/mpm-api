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
import com.amazonaws.services.s3.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component("mpmSiteStorage")
public class S3SiteStorage implements SiteStorage {

    @Value("${mpm_api.aws.client_id}")
    private String clientId;
    @Value("${mpm_api.aws.client_secret}")
    private String clientSecret;

    @Value("${mpm_api.aws.s3.mpm.bucket}")
    private String bucketName;
    @Value("${mpm_api.aws.s3.mpmjadmin.bucket}")
    private String bucketMpmjaminName;
    @Value("${mpm_api.aws.s3.mpm.blog_bucket}")
    private String bucketBlogName;
    @Value("${mpm_api.aws.s3.mpm.mympm_bucket}")
    private String bucketMyMpMName;

    @Override
    public void saveFile(String path, String content, String contentType) {

        AWSCredentials credentials = new BasicAWSCredentials(clientId, clientSecret);

        AmazonS3 s3client = getAmazonS3Client();

        try {
            log.debug("Uploading "+ path + " to MPM S3.");

            byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(contentBytes.length);
            metadata.setContentType(contentType);

            s3client.putObject(new PutObjectRequest(
                    bucketName, path, new ByteArrayInputStream(contentBytes), metadata));

            if(path.equals("datas.json")) {

                s3client = AmazonS3ClientBuilder
                        .standard()
                        .withCredentials(new AWSStaticCredentialsProvider(credentials))
                        .withRegion(Regions.SA_EAST_1)
                        .build();

                s3client.putObject(new PutObjectRequest(
                        bucketBlogName, path, new ByteArrayInputStream(contentBytes), metadata));
                
				s3client.putObject(new PutObjectRequest(
                        bucketMyMpMName, path, new ByteArrayInputStream(contentBytes), metadata));
            }


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

    @Override
    public void saveMpmjadminFile(String path, InputStream in, long size) {

        saveFile(bucketMpmjaminName, path, in, size);

    }

    @Override
    public void saveMympmFile(String path, InputStream in, long size) {

        saveFile(bucketMyMpMName, path, in, size);

    }

    public void saveFile(String bucket, String path, InputStream in, long size) {

        AmazonS3 s3client = getAmazonS3Client();

        try {
            log.info("Uploading "+ path + " to MPM S3.");

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(size);

            s3client.putObject(new PutObjectRequest(bucket, path, in, metadata));

            log.info("File "+ path + " sent to S3.");

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

    @Override
    public List<String> listMpmjadminFile(String path) {

        AmazonS3 s3client = getAmazonS3Client();

        ObjectListing objectListing = s3client.listObjects(bucketMpmjaminName, path);

        List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();

        List<String> files = new ArrayList<>();
        objectSummaries.forEach(s -> files.add(s.getKey().replace(path,"")));

        return files;
    }

    @Override
    public void getMpmjadminFile(String path, HttpServletResponse response) throws IOException {

        AmazonS3 s3client = getAmazonS3Client();

        S3Object object = s3client.getObject(bucketMpmjaminName, path);

        IOUtils.copy(object.getObjectContent(), response.getOutputStream());

    }

    private AmazonS3 getAmazonS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(clientId, clientSecret);

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();
    }

    private AmazonS3 getAmazonS3ClientSaEast() {
        AWSCredentials credentials = new BasicAWSCredentials(clientId, clientSecret);

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.SA_EAST_1)
                .build();
    }

}
