package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.PropertyUnit
import com.example.ui.components.*
import com.example.ui.viewmodel.PropertyFlowViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyDetailScreen(
    propertyId: String,
    viewModel: PropertyFlowViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToAddTenant: () -> Unit,
    onNavigateToRecordPayment: () -> Unit,
    onNavigateToMaintenance: () -> Unit
) {
    val properties by viewModel.properties.collectAsState()
    val currencySymbol by viewModel.currencySymbol.collectAsState()
    val property = properties.find { it.id == propertyId } ?: properties.firstOrNull()

    if (property == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Property not found")
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(property.name, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .testTag("property_detail_screen"),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Large Hero Image
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                ) {
                    if (property.imageResId != null) {
                        Image(
                            painter = painterResource(id = property.imageResId),
                            contentDescription = property.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Apartment,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(64.dp)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                                )
                            )
                    )

                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.primary
                            ) {
                                Text(
                                    text = property.type.label,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = property.name,
                                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 22.sp),
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.LocationOn,
                                    contentDescription = null,
                                    tint = Color(0xFF38BDF8),
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = property.address,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFFE2E8F0)
                                )
                            }
                        }

                        PropertyStatusBadge(status = property.status)
                    }
                }
            }

            // Quick Action Buttons
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilledTonalButton(
                        onClick = onNavigateToAddTenant,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(imageVector = Icons.Filled.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Add Tenant", style = MaterialTheme.typography.labelSmall)
                    }
                    FilledTonalButton(
                        onClick = onNavigateToRecordPayment,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(imageVector = Icons.Filled.Receipt, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Payment", style = MaterialTheme.typography.labelSmall)
                    }
                    FilledTonalButton(
                        onClick = onNavigateToMaintenance,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(imageVector = Icons.Filled.Build, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Maintenance", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            // Property Statistics Grid (Total Units, Occupied, Available, Monthly Rent)
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                    Text(
                        text = "Property Statistics",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MetricCard(
                            title = "Total Units",
                            value = "${property.totalUnits}",
                            icon = Icons.Filled.Apartment,
                            modifier = Modifier.weight(1f)
                        )
                        MetricCard(
                            title = "Occupied",
                            value = "${property.occupiedUnits}",
                            accentColor = Color(0xFF10B981),
                            icon = Icons.Filled.CheckCircle,
                            modifier = Modifier.weight(1f)
                        )
                        MetricCard(
                            title = "Available",
                            value = "${property.availableUnits}",
                            accentColor = Color(0xFF3B82F6),
                            icon = Icons.Filled.MeetingRoom,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    MetricCard(
                        title = "Monthly Rental Income",
                        value = formatCurrency(property.monthlyRentalIncome, currencySymbol),
                        subtitle = "Target gross revenue",
                        icon = Icons.Filled.Payments,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Property Performance (Occupancy Rate, Monthly Rent, Outstanding Rent)
            item {
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Property Performance",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Occupancy Rate", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${property.occupancyRate}%", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFF10B981))
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Monthly Income", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(formatCompactCurrency(property.monthlyRentalIncome, currencySymbol), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Outstanding Rent", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(formatCompactCurrency(720_000L, currencySymbol), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFFEF4444))
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        LinearProgressIndicator(
                            progress = { (property.occupancyRate.toFloat() / 100f).coerceIn(0f, 1f) },
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

            // Description
            if (property.description.isNotBlank()) {
                item {
                    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                        Text(
                            text = "About this Property",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = property.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Units List Section
            item {
                SectionHeader(
                    title = "Units & Tenants (${property.units.size.coerceAtLeast(property.totalUnits)})",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
            }

            if (property.units.isNotEmpty()) {
                items(property.units) { unitItem ->
                    PropertyUnitRow(unitItem = unitItem, currencySymbol = currencySymbol)
                }
            } else {
                item {
                    // Default display if units were dynamically generated
                    PropertyUnitRow(
                        unitItem = PropertyUnit("u1", "Flat 1A", "James Okafor", 850_000L, "Paid"),
                        currencySymbol = currencySymbol
                    )
                    PropertyUnitRow(
                        unitItem = PropertyUnit("u2", "Flat 2A", "Daniel Adekunle", 700_000L, "Paid"),
                        currencySymbol = currencySymbol
                    )
                    PropertyUnitRow(
                        unitItem = PropertyUnit("u3", "Flat 3B", "Sarah Adeyemi", 720_000L, "Pending"),
                        currencySymbol = currencySymbol
                    )
                    PropertyUnitRow(
                        unitItem = PropertyUnit("u4", "Flat 4A", null, 800_000L, "Available"),
                        currencySymbol = currencySymbol
                    )
                }
            }

            // Recent Activity Section
            item {
                Spacer(modifier = Modifier.height(10.dp))
                SectionHeader(
                    title = "Recent Activity",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                )

                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                    )
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        ActivityRowItem(icon = Icons.Filled.Receipt, title = "Rent payment received", desc = "James Okafor paid ${currencySymbol}850,000 for Flat 3B", time = "Today")
                        ActivityRowItem(icon = Icons.Filled.Build, title = "Maintenance request created", desc = "Water leakage reported at Flat 3B", time = "2 days ago")
                        ActivityRowItem(icon = Icons.Filled.PersonAdd, title = "Tenant added", desc = "Sarah Adeyemi signed lease for Flat 4A", time = "1 week ago")
                        ActivityRowItem(icon = Icons.Filled.Description, title = "Lease updated", desc = "Daniel Adekunle renewed annual lease", time = "2 weeks ago")
                    }
                }
            }
        }
    }
}

@Composable
private fun PropertyUnitRow(
    unitItem: PropertyUnit,
    currencySymbol: String
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.MeetingRoom,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Column {
                    Text(
                        text = unitItem.unitNumber,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = unitItem.tenantName ?: "Available",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (unitItem.tenantName != null) MaterialTheme.colorScheme.onSurfaceVariant else Color(0xFF3B82F6)
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = if (unitItem.monthlyRent > 0) "${formatCurrency(unitItem.monthlyRent, currencySymbol)}/mo" else "—",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = when (unitItem.status) {
                        "Paid" -> Color(0xFFECFDF5)
                        "Pending" -> Color(0xFFFFFBEB)
                        "Overdue" -> Color(0xFFFEF2F2)
                        else -> Color(0xFFEFF6FF)
                    }
                ) {
                    Text(
                        text = unitItem.status,
                        style = MaterialTheme.typography.labelSmall,
                        color = when (unitItem.status) {
                            "Paid" -> Color(0xFF065F46)
                            "Pending" -> Color(0xFF92400E)
                            "Overdue" -> Color(0xFF991B1B)
                            else -> Color(0xFF1D4ED8)
                        },
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ActivityRowItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    desc: String,
    time: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = time,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.outline
        )
    }
}
