package com.example.ui.navigation

import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.ui.components.PropertyFlowBottomNavBar
import com.example.ui.screens.*
import com.example.ui.viewmodel.PropertyFlowViewModel

@Composable
fun PropertyFlowApp(
    viewModel: PropertyFlowViewModel = remember { PropertyFlowViewModel() }
) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val userFeedback by viewModel.userFeedback.collectAsState()

    LaunchedEffect(userFeedback) {
        userFeedback?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearUserFeedback()
        }
    }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Show bottom bar on primary tabs
    val showBottomBar = currentRoute in listOf(
        Screen.Dashboard.route,
        Screen.Properties.route,
        Screen.Tenants.route,
        Screen.Payments.route,
        Screen.More.route
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                PropertyFlowBottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { targetRoute ->
                        if (currentRoute != targetRoute) {
                            navController.navigate(targetRoute) {
                                popUpTo(Screen.Dashboard.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            // Splash Screen
            composable(Screen.Splash.route) {
                SplashScreen(
                    onNavigateToDashboard = {
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                )
            }

            // Dashboard Screen
            composable(Screen.Dashboard.route) {
                DashboardScreen(
                    viewModel = viewModel,
                    onNavigateToAddProperty = { navController.navigate(Screen.AddProperty.route) },
                    onNavigateToAddTenant = { navController.navigate(Screen.Tenants.route) },
                    onNavigateToRecordPayment = { navController.navigate(Screen.RecordPayment.route) },
                    onNavigateToNewMaintenance = { navController.navigate(Screen.NewMaintenance.route) },
                    onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) },
                    onNavigateToProperties = { navController.navigate(Screen.Properties.route) },
                    onNavigateToPayments = { navController.navigate(Screen.Payments.route) },
                    onNavigateToMaintenance = { navController.navigate(Screen.Maintenance.route) }
                )
            }

            // Properties Screen
            composable(Screen.Properties.route) {
                PropertiesScreen(
                    viewModel = viewModel,
                    onPropertyClick = { propId ->
                        navController.navigate(Screen.PropertyDetail.createRoute(propId))
                    },
                    onNavigateToAddProperty = { navController.navigate(Screen.AddProperty.route) }
                )
            }

            // Property Detail Screen
            composable(
                route = Screen.PropertyDetail.route,
                arguments = listOf(navArgument("propertyId") { type = NavType.StringType })
            ) { backStackEntry ->
                val propId = backStackEntry.arguments?.getString("propertyId") ?: ""
                PropertyDetailScreen(
                    propertyId = propId,
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToAddTenant = { navController.navigate(Screen.Tenants.route) },
                    onNavigateToRecordPayment = { navController.navigate(Screen.RecordPayment.route) },
                    onNavigateToMaintenance = { navController.navigate(Screen.Maintenance.route) }
                )
            }

            // Add Property Screen
            composable(Screen.AddProperty.route) {
                AddPropertyScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // Tenants Screen
            composable(Screen.Tenants.route) {
                TenantsScreen(
                    viewModel = viewModel,
                    onTenantClick = { tenantId ->
                        navController.navigate(Screen.TenantDetail.createRoute(tenantId))
                    }
                )
            }

            // Tenant Detail Screen
            composable(
                route = Screen.TenantDetail.route,
                arguments = listOf(navArgument("tenantId") { type = NavType.StringType })
            ) { backStackEntry ->
                val tenantId = backStackEntry.arguments?.getString("tenantId") ?: ""
                TenantDetailScreen(
                    tenantId = tenantId,
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToRecordPayment = { navController.navigate(Screen.RecordPayment.route) },
                    onNavigateToCreateMaintenance = { navController.navigate(Screen.NewMaintenance.route) }
                )
            }

            // Payments Screen
            composable(Screen.Payments.route) {
                PaymentsScreen(
                    viewModel = viewModel,
                    onNavigateToRecordPayment = { navController.navigate(Screen.RecordPayment.route) }
                )
            }

            // Record Payment Screen
            composable(Screen.RecordPayment.route) {
                RecordPaymentScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // Maintenance Screen
            composable(Screen.Maintenance.route) {
                MaintenanceScreen(
                    viewModel = viewModel,
                    onNavigateToNewRequest = { navController.navigate(Screen.NewMaintenance.route) }
                )
            }

            // New Maintenance Screen
            composable(Screen.NewMaintenance.route) {
                NewMaintenanceScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // Reports Screen
            composable(Screen.Reports.route) {
                ReportsScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // Profile Screen
            composable(Screen.Profile.route) {
                ProfileScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
                )
            }

            // Settings Screen
            composable(Screen.Settings.route) {
                SettingsScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // Notifications Screen
            composable(Screen.Notifications.route) {
                NotificationsScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // More Screen
            composable(Screen.More.route) {
                MoreScreen(
                    viewModel = viewModel,
                    onNavigateToMaintenance = { navController.navigate(Screen.Maintenance.route) },
                    onNavigateToReports = { navController.navigate(Screen.Reports.route) },
                    onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
                    onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                    onNavigateToAbout = { navController.navigate(Screen.About.route) }
                )
            }

            // About Screen
            composable(Screen.About.route) {
                AboutScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
