package com.ventrek.bookingplatform.domain;

public enum PackageDeliveryRequestStatus {
    PAYMENT_PENDING("PAYMENT_PENDING"),  // 1
    ADMIN_APPROVAL_PENDING("ADMIN_APPROVAL_PENDING"), // 2
    ADMIN_APPROVED("ADMIN_APPROVED"), // 3 - ready to application
    ADMIN_REJECTED("ADMIN_REJECTED"), //
    // status for allocated packages
    DELIVERY_IN_PROGRESS("DELIVERY_IN_PROGRESS"),
    DELIVERY_COMPLETED("DELIVERY_COMPLETED");

    private final String value;

    PackageDeliveryRequestStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
