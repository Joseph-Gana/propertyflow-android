package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.MaintenanceRequest
import com.example.data.models.Payment
import com.example.data.models.PaymentStatus
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.PropertyFlowViewModel

@Composable
fun DashboardScreen(
    viewModel: PropertyFlowViewModel,
    onNavigateToAddProperty: () -> Unit,
    onNavigateToAddTenant: () -> Unit,
    onNavigateToRecordPayment: () -> Unit,
    onNavigateToNewMaintenance: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToProperties: () -> Unit,
    onNavigateToPayments: () -> Unit,
    onNavigateToMaintenance: () -> Unit
) {
    val currencySymbol by viewModel.currencySymbol.collectAsState()
    val portfolioValue by viewModel.portfolioValue.collectAsState()
    val collectedRent by viewModel.collectedRentThisMonth.collectAsState()
    val outstandingRent by viewModel.outstandingRent.collectAsState()
    val expectedRent by viewModel.expectedRent.collectAsState()
    val occupancyPercent by viewModel.overallOccupancyPercentage.collectAsState()
    val totalUnits by viewModel.totalUnitsCount.collectAsState()
    val occupiedUnits by viewModel.occupiedUnitsCount.collectAsState()
    val availableUnits by viewModel.availableUnitsCount.collectAsState()
    val properties by viewModel.properties.collectAsState()
    val payments by viewModel.payments.collectAsState()
    val maintenanceRequests by viewModel.maintenanceRequests.collectAsState()
    val unreadNotifs by viewModel.unreadNotificationCount.collectAsState()
    val selectedCountry by viewModel.selectedCountry.collectAsState()

    val rentCollectionProgress = if (expectedRent > 0) {
        (collectedRent.toFloat() / expectedRent.toFloat()).coerceIn(0f, 1f)
    } else 0.87f

    val animatedCollectionProgress by animateFloatAsState(targetValue = rentCollectionProgress, label = "progress")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("dashboard_screen"),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // 1. Header with Greeting and Notification
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Good morning, David",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(text = selectedCountry.flagEmoji, fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Here's your property overview",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Notification Bell with Badge
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable(onClick = onNavigateToNotifications)
                        .testTag("dashboard_notif_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(24.dp)
                    )
                    if (unreadNotifs > 0) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.error)
                                .align(Alignment.TopEnd)
                        )
                    }
                }
            }
        }

        // 2. Large Portfolio Summary Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF0F172A),
                                    Color(0xFF1E293B),
                                    Color(0xFF0F766E)
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Portfolio Value",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF94A3B8)
                            )
                            Surface(
                                shape = CircleShape,
                                color = Color(0xFF10B981).copy(alpha = 0.2f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                                        contentDescription = null,
                                        tint = Color(0xFF34D399),
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = "+8.4% this year",
                                        color = Color(0xFF34D399),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = formatCurrency(portfolioValue, currencySymbol),
                            style = MaterialTheme.typography.displayLarge.copy(fontSize = 30.sp),
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Managed Properties",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF94A3B8)
                                )
                                Text(
                                    text = "${properties.size} Active",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "Monthly Gross Run-rate",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF94A3B8)
                                )
                                Text(
                                    text = formatCompactCurrency(properties.sumOf { it.monthlyRentalIncome }, currencySymbol),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3. Financial Summary: 3 Cards (Rent Collected, Outstanding, Occupancy)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricCard(
                    title = "Rent Collected",
                    value = formatCompactCurrency(collectedRent, currencySymbol),
                    subtitle = "This month",
                    icon = Icons.Filled.CheckCircle,
                    accentColor = Color(0xFF10B981),
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Outstanding",
                    value = formatCompactCurrency(outstandingRent, currencySymbol),
                    subtitle = "Due soon",
                    icon = Icons.Filled.Schedule,
                    accentColor = Color(0xFFF59E0B),
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Occupancy",
                    value = "$occupancyPercent%",
                    subtitle = "$occupiedUnits/$totalUnits units",
                    icon = Icons.Filled.Home,
                    accentColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // 4. Portfolio Overview Section with Occupancy Progress
        item {
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.outlinedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Portfolio Overview",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "$occupancyPercent% Occupied",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        PortfolioStatItem(label = "Properties", value = "${properties.size}")
                        PortfolioStatItem(label = "Total Units", value = "$totalUnits")
                        PortfolioStatItem(label = "Occupied", value = "$occupiedUnits", valueColor = Color(0xFF10B981))
                        PortfolioStatItem(label = "Available", value = "$availableUnits", valueColor = Color(0xFF3B82F6))
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LinearProgressIndicator(
                        progress = { (occupiedUnits.toFloat() / totalUnits.toFloat()).coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }

        // 5. Rent Collection Progress Card
        item {
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.outlinedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Rent Collection",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${(animatedCollectionProgress * 100).toInt()}% collected",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF10B981)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${formatCompactCurrency(collectedRent, currencySymbol)} collected",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "${formatCompactCurrency(expectedRent, currencySymbol)} expected",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LinearProgressIndicator(
                        progress = { animatedCollectionProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = Color(0xFF10B981),
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }

        // 6. Quick Actions (4 Attractive Buttons)
        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                Text(
                    text = "Quick Actions",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionButton(
                        label = "Add Property",
                        icon = Icons.Filled.AddBusiness,
                        onClick = onNavigateToAddProperty,
                        modifier = Modifier.weight(1f),
                        testTag = "quick_action_add_property"
                    )
                    QuickActionButton(
                        label = "Add Tenant",
                        icon = Icons.Filled.PersonAdd,
                        onClick = onNavigateToAddTenant,
                        modifier = Modifier.weight(1f),
                        testTag = "quick_action_add_tenant"
                    )
                    QuickActionButton(
                        label = "Record Payment",
                        icon = Icons.Filled.Receipt,
                        onClick = onNavigateToRecordPayment,
                        modifier = Modifier.weight(1f),
                        testTag = "quick_action_record_payment"
                    )
                    QuickActionButton(
                        label = "New Maintenance",
                        icon = Icons.Filled.Build,
                        onClick = onNavigateToNewMaintenance,
                        modifier = Modifier.weight(1f),
                        testTag = "quick_action_new_maintenance"
                    )
                }
            }
        }

        // 7. Recent Transactions
        item {
            SectionHeader(
                title = "Recent Transactions",
                actionText = "See all",
                onActionClick = onNavigateToPayments,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
            )
        }

        items(payments.take(3)) { payment ->
            TransactionDashboardItem(
                payment = payment,
                currencySymbol = currencySymbol,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
            )
        }

        // 8. Maintenance Alerts
        item {
            Spacer(modifier = Modifier.height(8.dp))
            SectionHeader(
                title = "Maintenance Alerts",
                actionText = "View all",
                onActionClick = onNavigateToMaintenance,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
            )
        }

        items(maintenanceRequests.take(2)) { request ->
            MaintenanceAlertDashboardItem(
                request = request,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun PortfolioStatItem(
    label: String,
    value: String,
    valueColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Column {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = valueColor
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun QuickActionButton(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface,
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
        ),
        modifier = modifier.testTag(testTag)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun TransactionDashboardItem(
    payment: Payment,
    currencySymbol: String,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(
                            if (payment.status == PaymentStatus.PAID) Color(0xFFECFDF5) else Color(0xFFFEF2F2)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (payment.status == PaymentStatus.PAID) Icons.Filled.Check else Icons.Filled.PriorityHigh,
                        contentDescription = null,
                        tint = if (payment.status == PaymentStatus.PAID) Color(0xFF10B981) else Color(0xFFEF4444),
                        modifier = Modifier.size(20.dp)
                    )
                }

                Column {
                    Text(
                        text = payment.tenantName,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${payment.propertyName} — ${payment.unitNumber}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = payment.date,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                val amountPrefix = if (payment.status == PaymentStatus.PAID) "+" else ""
                Text(
                    text = "$amountPrefix${formatCurrency(payment.amount, currencySymbol)}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (payment.status == PaymentStatus.PAID) Color(0xFF10B981) else Color(0xFFEF4444)
                )
                Spacer(modifier = Modifier.height(4.dp))
                StatusBadge(status = payment.status)
            }
        }
    }
}

@Composable
private fun MaintenanceAlertDashboardItem(
    request: MaintenanceRequest,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = request.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                PriorityBadge(priority = request.priority)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${request.propertyName} — ${request.unitNumber}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MaintenanceStatusBadge(status = request.status)
                Text(
                    text = "Reported: ${request.reportedDate}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}
