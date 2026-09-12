package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.datasource.SampleData
import com.example.data.models.*
import com.example.data.repository.PropertyFlowRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

class PropertyFlowViewModel(
    private val repository: PropertyFlowRepository = PropertyFlowRepository.instance
) : ViewModel() {

    val selectedCountry: StateFlow<SupportedCountry> = repository.selectedCountry
    val currencySymbol: StateFlow<String> = repository.currencySymbol
    val currencyCode: StateFlow<String> = repository.currencyCode

    val properties: StateFlow<List<Property>> = repository.properties
    val tenants: StateFlow<List<Tenant>> = repository.tenants
    val payments: StateFlow<List<Payment>> = repository.payments
    val maintenanceRequests: StateFlow<List<MaintenanceRequest>> = repository.maintenanceRequests
    val notifications: StateFlow<List<NotificationItem>> = repository.notifications

    // Search and filter states
    private val _propertySearchQuery = MutableStateFlow("")
    val propertySearchQuery = _propertySearchQuery.asStateFlow()

    private val _propertyFilterType = MutableStateFlow<PropertyType?>(null)
    val propertyFilterType = _propertyFilterType.asStateFlow()

    private val _tenantSearchQuery = MutableStateFlow("")
    val tenantSearchQuery = _tenantSearchQuery.asStateFlow()

    private val _tenantFilterStatus = MutableStateFlow<PaymentStatus?>(null)
    val tenantFilterStatus = _tenantFilterStatus.asStateFlow()

    private val _paymentFilterStatus = MutableStateFlow<PaymentStatus?>(null)
    val paymentFilterStatus = _paymentFilterStatus.asStateFlow()

    private val _maintenanceFilterStatus = MutableStateFlow<MaintenanceStatus?>(null)
    val maintenanceFilterStatus = _maintenanceFilterStatus.asStateFlow()

    // Snackbar / Feedback message
    private val _userFeedback = MutableStateFlow<String?>(null)
    val userFeedback = _userFeedback.asStateFlow()

    // Filtered lists
    val filteredProperties = combine(properties, _propertySearchQuery, _propertyFilterType) { list, query, type ->
        list.filter { prop ->
            val matchesQuery = query.isBlank() ||
                    prop.name.contains(query, ignoreCase = true) ||
                    prop.city.contains(query, ignoreCase = true) ||
                    prop.state.contains(query, ignoreCase = true) ||
                    prop.address.contains(query, ignoreCase = true)
            val matchesType = type == null || prop.type == type
            matchesQuery && matchesType
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredTenants = combine(tenants, _tenantSearchQuery, _tenantFilterStatus) { list, query, status ->
        list.filter { tenant ->
            val matchesQuery = query.isBlank() ||
                    tenant.name.contains(query, ignoreCase = true) ||
                    tenant.propertyName.contains(query, ignoreCase = true) ||
                    tenant.unitNumber.contains(query, ignoreCase = true)
            val matchesStatus = status == null || tenant.paymentStatus == status
            matchesQuery && matchesStatus
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredPayments = combine(payments, _paymentFilterStatus) { list, status ->
        if (status == null) list else list.filter { it.status == status }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredMaintenance = combine(maintenanceRequests, _maintenanceFilterStatus) { list, status ->
        if (status == null) list else list.filter { it.status == status }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val unreadNotificationCount = notifications.map { list ->
        list.count { !it.isRead }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // Calculated Dashboard Metrics
    val totalUnitsCount = properties.map { it.sumOf { p -> p.totalUnits } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 48)

    val occupiedUnitsCount = properties.map { it.sumOf { p -> p.occupiedUnits } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 44)

    val availableUnitsCount = properties.map { it.sumOf { p -> p.availableUnits } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 4)

    val portfolioValue = combine(properties, selectedCountry) { props, country ->
        when (country) {
            SupportedCountry.NIGERIA -> 185_500_000L
            SupportedCountry.UNITED_KINGDOM -> 4_250_000L
            SupportedCountry.UNITED_STATES -> 20_500_000L
            SupportedCountry.CANADA -> 9_800_000L
            SupportedCountry.GHANA -> 18_000_000L
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 185_500_000L)

    val collectedRentThisMonth = combine(payments, selectedCountry) { pList, country ->
        val paidSum = pList.filter { it.status == PaymentStatus.PAID }.sumOf { it.amount }
        if (paidSum > 0) paidSum else when (country) {
            SupportedCountry.NIGERIA -> 8_450_000L
            SupportedCountry.UNITED_KINGDOM -> 21_800L
            SupportedCountry.UNITED_STATES -> 66_000L
            SupportedCountry.CANADA -> 28_000L
            SupportedCountry.GHANA -> 78_000L
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 8_450_000L)

    val outstandingRent = combine(payments, selectedCountry) { pList, country ->
        val overdueSum = pList.filter { it.status != PaymentStatus.PAID }.sumOf { it.amount }
        if (overdueSum > 0) overdueSum else when (country) {
            SupportedCountry.NIGERIA -> 1_250_000L
            SupportedCountry.UNITED_KINGDOM -> 2_400L
            SupportedCountry.UNITED_STATES -> 7_600L
            SupportedCountry.CANADA -> 3_500L
            SupportedCountry.GHANA -> 13_000L
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 1_250_000L)

    val expectedRent = combine(collectedRentThisMonth, outstandingRent) { collected, outstanding ->
        collected + outstanding
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 9_700_000L)

    val overallOccupancyPercentage = combine(totalUnitsCount, occupiedUnitsCount) { total, occupied ->
        if (total > 0) ((occupied.toFloat() / total.toFloat()) * 100).toInt() else 92
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 92)

    // Actions
    fun setPropertySearchQuery(query: String) {
        _propertySearchQuery.value = query
    }

    fun setPropertyFilterType(type: PropertyType?) {
        _propertyFilterType.value = type
    }

    fun setTenantSearchQuery(query: String) {
        _tenantSearchQuery.value = query
    }

    fun setTenantFilterStatus(status: PaymentStatus?) {
        _tenantFilterStatus.value = status
    }

    fun setPaymentFilterStatus(status: PaymentStatus?) {
        _paymentFilterStatus.value = status
    }

    fun setMaintenanceFilterStatus(status: MaintenanceStatus?) {
        _maintenanceFilterStatus.value = status
    }

    fun clearUserFeedback() {
        _userFeedback.value = null
    }

    fun switchCountry(country: SupportedCountry) {
        repository.switchCountry(country)
        _userFeedback.value = "Market switched to ${country.displayName} ${country.flagEmoji} (${country.currencySymbol})"
    }

    fun setCurrency(currencyCode: String, symbol: String) {
        repository.setCurrency(currencyCode, symbol)
        _userFeedback.value = "Currency set to $currencyCode ($symbol)"
    }

    fun addProperty(
        name: String,
        address: String,
        city: String,
        state: String,
        type: PropertyType,
        totalUnits: Int,
        monthlyRent: Long,
        description: String
    ): Boolean {
        if (name.isBlank() || address.isBlank() || city.isBlank() || state.isBlank() || totalUnits <= 0 || monthlyRent <= 0) {
            _userFeedback.value = "Please complete all required fields."
            return false
        }

        val newProp = Property(
            id = "prop-${UUID.randomUUID()}",
            name = name.trim(),
            address = address.trim(),
            city = city.trim(),
            state = state.trim(),
            countryCode = selectedCountry.value.code,
            type = type,
            totalUnits = totalUnits,
            occupiedUnits = 0,
            monthlyRentalIncome = monthlyRent,
            status = PropertyStatus.ACTIVE,
            imageResId = com.example.R.drawable.img_property_lekki,
            description = description.trim(),
            totalValuation = monthlyRent * 12 * 8, // ~8x gross yield estimate
            units = (1..totalUnits).map { unitNum ->
                PropertyUnit(
                    id = "u-${UUID.randomUUID()}",
                    unitNumber = "Unit $unitNum",
                    tenantName = null,
                    monthlyRent = monthlyRent / totalUnits,
                    status = "Available"
                )
            }
        )

        repository.addProperty(newProp)
        _userFeedback.value = "Property added successfully."
        return true
    }

    fun recordPayment(
        tenantName: String,
        propertyName: String,
        unitNumber: String,
        amount: Long,
        paymentDate: String,
        paymentMethod: PaymentMethod,
        reference: String,
        notes: String
    ): Boolean {
        if (tenantName.isBlank() || propertyName.isBlank() || unitNumber.isBlank() || amount <= 0) {
            _userFeedback.value = "Please fill in all required payment details."
            return false
        }

        val newPayment = Payment(
            id = "pay-${UUID.randomUUID()}",
            tenantName = tenantName.trim(),
            propertyName = propertyName.trim(),
            unitNumber = unitNumber.trim(),
            amount = amount,
            date = if (paymentDate.isBlank()) "Today, Just now" else paymentDate.trim(),
            paymentMethod = paymentMethod,
            reference = if (reference.isBlank()) "TRX-${System.currentTimeMillis().toString().takeLast(6)}" else reference.trim(),
            status = PaymentStatus.PAID,
            notes = notes.trim()
        )

        repository.recordPayment(newPayment)
        _userFeedback.value = "Payment recorded successfully."
        return true
    }

    fun createMaintenanceRequest(
        propertyName: String,
        unitNumber: String,
        title: String,
        description: String,
        priority: MaintenancePriority,
        reportedDate: String
    ): Boolean {
        if (propertyName.isBlank() || title.isBlank() || description.isBlank()) {
            _userFeedback.value = "Please enter all required maintenance details."
            return false
        }

        val request = MaintenanceRequest(
            id = "maint-${UUID.randomUUID()}",
            title = title.trim(),
            propertyName = propertyName.trim(),
            unitNumber = if (unitNumber.isBlank()) "General" else unitNumber.trim(),
            description = description.trim(),
            priority = priority,
            status = MaintenanceStatus.OPEN,
            reportedDate = if (reportedDate.isBlank()) "Today" else reportedDate.trim()
        )

        repository.addMaintenanceRequest(request)
        _userFeedback.value = "Maintenance request created successfully."
        return true
    }

    fun updateMaintenanceStatus(id: String, status: MaintenanceStatus) {
        repository.updateMaintenanceStatus(id, status)
        _userFeedback.value = "Maintenance status updated to ${status.label}."
    }

    fun markNotificationAsRead(id: String) {
        repository.markNotificationAsRead(id)
    }

    fun markAllNotificationsAsRead() {
        repository.markAllNotificationsAsRead()
        _userFeedback.value = "All notifications marked as read."
    }
}
