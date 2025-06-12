package com.day_walk.backend.domain.image.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final String bucketName = "team04-image-bucket";

    private final S3Client s3Client;

    String prefix = "images/userReview/";

    public String uploadImage(MultipartFile multipartFile) {
        try {
            // S3에 저장될 고유한 파일 이름 생성
            String randomName = UUID.randomUUID().toString();
            String fileName = prefix + randomName + "_" + multipartFile.getOriginalFilename();

            // S3 업로드 요청 객체 생성
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(multipartFile.getContentType())
                    .build();

            // 파일 업로드
            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize())
            );

            // S3에서 해당 파일의 URL 가져오기
            return s3Client.utilities().getUrl(GetUrlRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build()).toExternalForm();

        } catch (Exception e) {
            throw new RuntimeException("S3 업로드 실패: " + e.getMessage(), e);
        }
    }
}
