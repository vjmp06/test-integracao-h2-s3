package com.example.testintegracaoh2s3.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.util.SocketUtils;

import io.findify.s3mock.S3Mock;

@TestConfiguration
public class AmazonS3Configuration {
    @Bean
    public AmazonS3 s3MockBean() {
        int port = SocketUtils.findAvailableTcpPort();
        S3Mock api = new S3Mock.Builder().withPort(port).withInMemoryBackend().build();
        api.start(); // Start the Mock S3 server locally on available port

        return  AmazonS3ClientBuilder.standard().withPathStyleAccessEnabled(true)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials())) // use anonymous
                                                                                                  // credentials.
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:" + port, "us-west-2"))
                .build();
    }
}
