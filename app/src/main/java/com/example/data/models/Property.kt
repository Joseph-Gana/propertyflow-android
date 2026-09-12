package com.example.data.models

enum class PropertyType(val label: String) {
    APARTMENT("Apartment"),
    DUPLEX("Duplex"),
    DETACHED_HOUSE("Detached House"),
    SEMI_DETACHED("Semi-Detached House"),
    SELF_CONTAINED("Self-Contained"),
    COMMERCIAL("Commercial"),
    ESTATE("Estate"),
    SHORT_LET("Short-let")
}

enum class PropertyStatus(val label: String) {
    ACTIVE("Active"),
    MAINTENANCE("In Maintenance"),
    VACANT("Vacant"),
    FULL("Fully Occupied")
}

data class PropertyUnit(
    val id: String,
    val unitNumber: String,
    val tenantName: String? = null,
    val monthlyRent: Long,
    val status: String // "Paid", "Pending", "Overdue", "Available"
)

data class Property(
    val id: String,
    val name: String,
    val address: String,
    val city: String,
    val state: String,
    val countryCode: String,
    val type: PropertyType,
    val totalUnits: Int,
    val occupiedUnits: Int,
    val monthlyRentalIncome: Long,
    val status: PropertyStatus = PropertyStatus.ACTIVE,
    val imageResId: Int? = null,
    val description: String = "",
    val units: List<PropertyUnit> = emptyList(),
    val totalValuation: Long = 0L
) {
    val availableUnits: Int
        get() = (totalUnits - occupiedUnits).coerceAtLeast(0)

    val occupancyRate: Int
        get() = if (totalUnits > 0) ((occupiedUnits.toFloat() / totalUnits.toFloat()) * 100).toInt() else 0
}
