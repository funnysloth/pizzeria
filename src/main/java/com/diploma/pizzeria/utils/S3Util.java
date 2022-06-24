package com.diploma.pizzeria.utils;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

public class S3Util {

    public static void uploadFile(String fileName, InputStream inputStream) throws IOException {

        final AwsBasicCredentials awsBasicCredentials =
                AwsBasicCredentials.create("AKIASTAWVD2CYP74AVVH", "tOYobSWlC7v/J+mQ5a0vWYoAMKHTyA8K1HJUtKaN");
        final StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);

        S3Client client = S3Client.builder()
                .region(Region.EU_CENTRAL_1)
                .credentialsProvider(credentialsProvider)
                .build();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket("pizzeriabucket")
                .acl(ObjectCannedACL.PUBLIC_READ)
                .key(fileName)
                .build();

        client.putObject(request, RequestBody.fromInputStream(inputStream, inputStream.available()));
    }
}
