package com.sopt.collaboration.global.s3.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@Service
@Profile("s3")
@RequiredArgsConstructor
public class S3Service {

	private final S3Presigner s3Presigner;

	@Value("${spring.cloud.aws.s3.bucket}")
	private String bucketName;

	@Value("${aws.s3.presigned-url-expiration:3600}")
	private long presignedUrlExpiration;

	/**
	 * S3 객체 키로 PreSigned URL 생성
	 *
	 * @param objectKey S3 객체 키 (예: "images/book-cover/12345.jpg")
	 * @return PreSigned URL (1시간 유효)
	 */
	public String generatePresignedUrl(String objectKey) {
		GetObjectRequest getObjectRequest = GetObjectRequest.builder()
			.bucket(bucketName)
			.key(objectKey)
			.build();

		GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
			.signatureDuration(Duration.ofSeconds(presignedUrlExpiration))
			.getObjectRequest(getObjectRequest)
			.build();

		PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
		return presignedRequest.url().toString();
	}

	/**
	 * S3 객체 키가 null이거나 빈 문자열인 경우 null 반환, 아니면 PreSigned URL 생성
	 *
	 * @param objectKey S3 객체 키
	 * @return PreSigned URL 또는 null
	 */
	public String generatePresignedUrlOrNull(String objectKey) {
		if (objectKey == null || objectKey.isBlank()) {
			return null;
		}
		return generatePresignedUrl(objectKey);
	}
}
