package io.github.edsonisaac.beemonitor.configurations;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfiguration {

    @Value("${app.aws.access_key_id}")
    private String awsId;

    @Value("${app.aws.secret_access_key}")
    private String awsKey;

    @Value("${app.aws.s3.region}")
    private String region;

    @Bean
    AmazonS3 s3Client() {

        final var awsCredentials = new BasicAWSCredentials(awsId, awsKey);

        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}