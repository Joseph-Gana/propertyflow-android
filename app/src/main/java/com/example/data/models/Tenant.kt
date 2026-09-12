package com.example.data.models

enum class PaymentStatus(val label: String) {
    PAID("Paid"),
    PENDING("Pending"),
    OVERDUE("Overdue")
}

data class Tenant(
    val id: String,
    val name: String,
    val phone: String,
    val email: String,
    val propertyId: String,
    val propertyName: String,
    val unitNumber: String,
    val monthlyRent: Long,
    val leaseStart: String,
    val leaseEnd: String,
    val paymentStatus: PaymentStatus,
    val deposit: Long,
    val paymentDueDate: String,
    val avatarResId: Int? = null
)
