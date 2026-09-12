package com.example.data.models

enum class PaymentMethod(val label: String) {
    BANK_TRANSFER("Bank Transfer"),
    CASH("Cash"),
    POS("POS"),
    CARD("Card"),
    DIRECT_DEBIT("Direct Debit")
}

data class Payment(
    val id: String,
    val tenantId: String = "",
    val tenantName: String,
    val propertyId: String = "",
    val propertyName: String,
    val unitNumber: String,
    val amount: Long,
    val date: String,
    val paymentMethod: PaymentMethod,
    val reference: String,
    val status: PaymentStatus,
    val notes: String = ""
)
