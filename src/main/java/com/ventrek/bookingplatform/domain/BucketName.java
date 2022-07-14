package com.ventrek.bookingplatform.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    PACKAGE_PAYMENT_PROOFS("delivery-package-payment-proofs-bucket"),
    PACKAGE_IMAGES("delivery-package-images-bucket");
    private final String bucketName;
}
