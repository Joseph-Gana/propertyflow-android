package com.example.data.models

enum class MaintenancePriority(val label: String) {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    URGENT("Urgent")
}

enum class MaintenanceStatus(val label: String) {
    OPEN("Open"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed")
}

data class MaintenanceRequest(
    val id: String,
    val title: String,
    val propertyId: String = "",
    val propertyName: String,
    val unitNumber: String,
    val description: String,
    val priority: MaintenancePriority,
    val status: MaintenanceStatus,
    val reportedDate: String,
    val tenantName: String = ""
)
