package com.diploma.pizzeria.utils;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

public class S3Util {

    public static void uploadFile(String fileName, InputStream inputStream) throws IOException {

        S3Client client = S3Client.builder().build();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket("pizzeriabucket/static/dishImages")
                .key(fileName)
                .acl("public-read")
                .build();

        client.putObject(request, RequestBody.fromInputStream(inputStream, inputStream.available()));
    }
}
