package com.sopt.collaboration.global.s3.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sopt.collaboration.global.response.CommonApiResponse;
import com.sopt.collaboration.global.response.success.SuccessCode;
import com.sopt.collaboration.global.service.S3Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;

@RestController
@RequestMapping("/api/test/s3")
@RequiredArgsConstructor
@Profile("s3")
public class S3TestController {

    private final S3Service s3Service;
    private final S3Client s3Client;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    /**
     * S3 버킷 연결 테스트
     */
    @GetMapping("/connection")
    public CommonApiResponse<Map<String, Object>> testConnection() {
        Map<String, Object> result = new HashMap<>();

        try {
            // 버킷 존재 여부 확인
            s3Client.headBucket(builder -> builder.bucket(bucketName));

            result.put("status", "SUCCESS");
            result.put("message", "S3 버킷 연결 성공");
            result.put("bucketName", bucketName);
            result.put("region", "ap-northeast-2");

            return CommonApiResponse.success(SuccessCode.SUCCESS, result);
        } catch (Exception e) {
            result.put("status", "FAILED");
            result.put("message", "S3 버킷 연결 실패: " + e.getMessage());
            result.put("bucketName", bucketName);

            return CommonApiResponse.success(SuccessCode.SUCCESS, result);
        }
    }

    /**
     * S3 버킷 객체 목록 조회 (최대 10개)
     */
    @GetMapping("/list")
    public CommonApiResponse<Map<String, Object>> listObjects(
            @RequestParam(defaultValue = "") String prefix
    ) {
        Map<String, Object> result = new HashMap<>();

        try {
            ListObjectsV2Request request = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .prefix(prefix)
                    .maxKeys(10)
                    .build();

            ListObjectsV2Response response = s3Client.listObjectsV2(request);

            List<Map<String, Object>> objects = response.contents().stream()
                    .map(s3Object -> {
                        Map<String, Object> obj = new HashMap<>();
                        obj.put("key", s3Object.key());
                        obj.put("size", s3Object.size() + " bytes");
                        obj.put("lastModified", s3Object.lastModified().toString());
                        return obj;
                    })
                    .collect(Collectors.toList());

            result.put("status", "SUCCESS");
            result.put("bucketName", bucketName);
            result.put("prefix", prefix.isEmpty() ? "전체" : prefix);
            result.put("objectCount", objects.size());
            result.put("objects", objects);

            return CommonApiResponse.success(SuccessCode.SUCCESS, result);
        } catch (Exception e) {
            result.put("status", "FAILED");
            result.put("message", "객체 목록 조회 실패: " + e.getMessage());

            return CommonApiResponse.success(SuccessCode.SUCCESS, result);
        }
    }


    /**
     * PreSigned URL 생성 테스트
     */
    @GetMapping("/presigned-url")
    public CommonApiResponse<Map<String, Object>> generatePresignedUrl(
            @RequestParam String objectKey
    ) {
        Map<String, Object> result = new HashMap<>();

        try {
            String presignedUrl = s3Service.generatePresignedUrl(objectKey);

            result.put("status", "SUCCESS");
            result.put("objectKey", objectKey);
            result.put("presignedUrl", presignedUrl);
            result.put("expiresIn", "1시간");

            return CommonApiResponse.success(SuccessCode.SUCCESS, result);
        } catch (Exception e) {
            result.put("status", "FAILED");
            result.put("message", "PreSigned URL 생성 실패: " + e.getMessage());
            result.put("objectKey", objectKey);

            return CommonApiResponse.success(SuccessCode.SUCCESS, result);
        }
    }
}
