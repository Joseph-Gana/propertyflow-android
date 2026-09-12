package com.example.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Dashboard : Screen("dashboard")
    object Properties : Screen("properties")
    object PropertyDetail : Screen("property_detail/{propertyId}") {
        fun createRoute(propertyId: String) = "property_detail/$propertyId"
    }
    object AddProperty : Screen("add_property")
    object Tenants : Screen("tenants")
    object TenantDetail : Screen("tenant_detail/{tenantId}") {
        fun createRoute(tenantId: String) = "tenant_detail/$tenantId"
    }
    object Payments : Screen("payments")
    object RecordPayment : Screen("record_payment")
    object Maintenance : Screen("maintenance")
    object NewMaintenance : Screen("new_maintenance")
    object Reports : Screen("reports")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
    object Notifications : Screen("notifications")
    object More : Screen("more")
    object About : Screen("about")
}
