package com.day_walk.backend.domain.image.service;

import com.day_walk.backend.global.error.CustomException;
import com.day_walk.backend.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;

import java.net.URL;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final String bucketName = "team04-image-bucket";

    private final S3Client s3Client;

    public String uploadImage(MultipartFile multipartFile) {

        if(multipartFile.isEmpty()) {
            throw new CustomException(ErrorCode.IMAGE_NOT_FOUND);
        }

        try {
            String randomName = UUID.randomUUID().toString();
            String fileName = randomName + "_" + multipartFile.getOriginalFilename();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(multipartFile.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize())
            );

            return s3Client.utilities().getUrl(GetUrlRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build()).toExternalForm();

        } catch (Exception e) {
            throw new RuntimeException("S3 업로드 실패: " + e.getMessage(), e);
        }
    }

    public boolean deleteImage(String imgUrl) {
        if(imgUrl == null) {
            throw new CustomException(ErrorCode.IMAGE_NOT_FOUND);
        }
        try {
            imgUrl = imgUrl.replace("\"", "");
            URL url = new URL(imgUrl);
            String key = url.getPath().substring(1);
            System.out.println("Key : " + key);

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            DeleteObjectResponse deleteResponse = s3Client.deleteObject(deleteObjectRequest);

            if (deleteResponse.sdkHttpResponse().isSuccessful()) {
                return true;
            } else {
                System.err.println("이미지 삭제 실패: " + key);
                throw new CustomException(ErrorCode.IMAGE_DELETE_FAIL);
            }

        } catch (Exception e) {
            throw new RuntimeException("S3 삭제 실패: " + e.getMessage(), e);
        }
    }

}
