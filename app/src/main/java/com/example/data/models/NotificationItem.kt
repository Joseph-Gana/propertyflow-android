package com.example.data.models

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val timeAgo: String,
    val isRead: Boolean = false,
    val type: NotificationType = NotificationType.GENERAL
)

enum class NotificationType {
    PAYMENT,
    MAINTENANCE,
    LEASE,
    GENERAL
}
