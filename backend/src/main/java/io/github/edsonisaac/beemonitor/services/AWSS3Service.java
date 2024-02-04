package io.github.edsonisaac.beemonitor.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.github.edsonisaac.beemonitor.exceptions.OperationFailureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class AWSS3Service {

    private final AmazonS3 s3Client;

    @Value("${app.aws.s3.bucket}")
    private String bucket;

    public void delete(String filename) {

        try {
            s3Client.deleteObject(bucket, filename);
        } catch (AmazonServiceException ex) {
            throw new OperationFailureException("Não foi possível excluir o arquivo do Amazon S3");
        }
    }

    public String save(File file) {

        try {
            s3Client.putObject(new PutObjectRequest(bucket, file.getName(), file));
            return s3Client.getUrl(bucket, file.getName()).toURI().toString();
        } catch (AmazonServiceException ex) {
            ex.printStackTrace();
            throw new OperationFailureException("Não foi possível salvar o arquivo no Amazon S3");
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
            throw new OperationFailureException("Não foi possível converter a URL em URI");
        }
    }
}