package com.example.data.repository

import com.example.data.datasource.SampleData
import com.example.data.models.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class PropertyFlowRepository {

    private val _selectedCountry = MutableStateFlow(SampleData.defaultCountry)
    val selectedCountry: StateFlow<SupportedCountry> = _selectedCountry.asStateFlow()

    private val _currencySymbol = MutableStateFlow(SampleData.defaultCountry.currencySymbol)
    val currencySymbol: StateFlow<String> = _currencySymbol.asStateFlow()

    private val _currencyCode = MutableStateFlow(SampleData.defaultCountry.currencyCode)
    val currencyCode: StateFlow<String> = _currencyCode.asStateFlow()

    private val _properties = MutableStateFlow(SampleData.getPropertiesForCountry(SampleData.defaultCountry))
    val properties: StateFlow<List<Property>> = _properties.asStateFlow()

    private val _tenants = MutableStateFlow(SampleData.getTenantsForCountry(SampleData.defaultCountry))
    val tenants: StateFlow<List<Tenant>> = _tenants.asStateFlow()

    private val _payments = MutableStateFlow(SampleData.getPaymentsForCountry(SampleData.defaultCountry))
    val payments: StateFlow<List<Payment>> = _payments.asStateFlow()

    private val _maintenanceRequests = MutableStateFlow(SampleData.getMaintenanceForCountry(SampleData.defaultCountry))
    val maintenanceRequests: StateFlow<List<MaintenanceRequest>> = _maintenanceRequests.asStateFlow()

    private val _notifications = MutableStateFlow(SampleData.initialNotifications)
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    fun switchCountry(country: SupportedCountry) {
        _selectedCountry.value = country
        _currencySymbol.value = country.currencySymbol
        _currencyCode.value = country.currencyCode

        // Switch to the target market's data set
        _properties.value = SampleData.getPropertiesForCountry(country)
        _tenants.value = SampleData.getTenantsForCountry(country)
        _payments.value = SampleData.getPaymentsForCountry(country)
        _maintenanceRequests.value = SampleData.getMaintenanceForCountry(country)

        // Add a notification for market switch
        val notif = NotificationItem(
            id = UUID.randomUUID().toString(),
            title = "Market Switched",
            message = "Switched active market to ${country.displayName} ${country.flagEmoji} (${country.currencySymbol}).",
            timeAgo = "Just now",
            isRead = false,
            type = NotificationType.GENERAL
        )
        _notifications.update { listOf(notif) + it }
    }

    fun setCurrency(currencyCode: String, symbol: String) {
        _currencyCode.value = currencyCode
        _currencySymbol.value = symbol
    }

    fun addProperty(property: Property) {
        _properties.update { listOf(property) + it }
        val notif = NotificationItem(
            id = UUID.randomUUID().toString(),
            title = "New Property Added",
            message = "${property.name} (${property.city}) has been added to your portfolio.",
            timeAgo = "Just now",
            isRead = false,
            type = NotificationType.GENERAL
        )
        _notifications.update { listOf(notif) + it }
    }

    fun recordPayment(payment: Payment) {
        _payments.update { listOf(payment) + it }

        // Update tenant status if matching
        _tenants.update { currentTenants ->
            currentTenants.map { tenant ->
                if (tenant.name.equals(payment.tenantName, ignoreCase = true) ||
                    (tenant.propertyName.equals(payment.propertyName, ignoreCase = true) &&
                     tenant.unitNumber.equals(payment.unitNumber, ignoreCase = true))) {
                    tenant.copy(paymentStatus = payment.status)
                } else {
                    tenant
                }
            }
        }

        // Also update unit status in property
        _properties.update { currentProps ->
            currentProps.map { prop ->
                if (prop.name.equals(payment.propertyName, ignoreCase = true)) {
                    val updatedUnits = prop.units.map { unit ->
                        if (unit.unitNumber.equals(payment.unitNumber, ignoreCase = true)) {
                            unit.copy(status = payment.status.label)
                        } else {
                            unit
                        }
                    }
                    prop.copy(units = updatedUnits)
                } else {
                    prop
                }
            }
        }

        val notif = NotificationItem(
            id = UUID.randomUUID().toString(),
            title = "Rent Payment Recorded",
            message = "${_currencySymbol.value}${payment.amount} recorded for ${payment.tenantName} (${payment.unitNumber}).",
            timeAgo = "Just now",
            isRead = false,
            type = NotificationType.PAYMENT
        )
        _notifications.update { listOf(notif) + it }
    }

    fun addMaintenanceRequest(request: MaintenanceRequest) {
        _maintenanceRequests.update { listOf(request) + it }
        val notif = NotificationItem(
            id = UUID.randomUUID().toString(),
            title = "Maintenance Request Created",
            message = "${request.title} for ${request.propertyName} (${request.unitNumber}) marked as ${request.priority.label} priority.",
            timeAgo = "Just now",
            isRead = false,
            type = NotificationType.MAINTENANCE
        )
        _notifications.update { listOf(notif) + it }
    }

    fun updateMaintenanceStatus(id: String, newStatus: MaintenanceStatus) {
        _maintenanceRequests.update { list ->
            list.map { if (it.id == id) it.copy(status = newStatus) else it }
        }
    }

    fun markNotificationAsRead(id: String) {
        _notifications.update { list ->
            list.map { if (it.id == id) it.copy(isRead = true) else it }
        }
    }

    fun markAllNotificationsAsRead() {
        _notifications.update { list ->
            list.map { it.copy(isRead = true) }
        }
    }

    // Singleton instance for in-app demo shared state across ViewModels/Screens
    companion object {
        val instance = PropertyFlowRepository()
    }
}
