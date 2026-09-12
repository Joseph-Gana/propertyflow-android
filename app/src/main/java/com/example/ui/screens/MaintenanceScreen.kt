package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.models.MaintenancePriority
import com.example.data.models.MaintenanceRequest
import com.example.data.models.MaintenanceStatus
import com.example.ui.components.*
import com.example.ui.viewmodel.PropertyFlowViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaintenanceScreen(
    viewModel: PropertyFlowViewModel,
    onNavigateToNewRequest: () -> Unit
) {
    val requests by viewModel.filteredMaintenance.collectAsState()
    val allRequests by viewModel.maintenanceRequests.collectAsState()
    val filterStatus by viewModel.maintenanceFilterStatus.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToNewRequest,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.testTag("fab_new_maintenance")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "New Request")
                    Text("New Request", fontWeight = FontWeight.SemiBold)
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .testTag("maintenance_screen"),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = "Maintenance",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Track property repairs and service tickets",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Priority counts summary
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MetricCard(
                            title = "Open",
                            value = "${allRequests.count { it.status == MaintenanceStatus.OPEN }}",
                            subtitle = "Requires action",
                            accentColor = Color(0xFFF59E0B),
                            modifier = Modifier.weight(1f)
                        )
                        MetricCard(
                            title = "In Progress",
                            value = "${allRequests.count { it.status == MaintenanceStatus.IN_PROGRESS }}",
                            subtitle = "Being resolved",
                            accentColor = Color(0xFF3B82F6),
                            modifier = Modifier.weight(1f)
                        )
                        MetricCard(
                            title = "Completed",
                            value = "${allRequests.count { it.status == MaintenanceStatus.COMPLETED }}",
                            subtitle = "Resolved tickets",
                            accentColor = Color(0xFF10B981),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Filter chips
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        item {
                            FilterChip(
                                selected = filterStatus == null,
                                onClick = { viewModel.setMaintenanceFilterStatus(null) },
                                label = { Text("All (${allRequests.size})") },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                        }
                        items(MaintenanceStatus.values()) { status ->
                            FilterChip(
                                selected = filterStatus == status,
                                onClick = {
                                    viewModel.setMaintenanceFilterStatus(if (filterStatus == status) null else status)
                                },
                                label = { Text(status.label) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                        }
                    }
                }
            }

            if (requests.isEmpty()) {
                item {
                    EmptyStateView(
                        icon = Icons.Filled.Build,
                        title = "No maintenance tickets",
                        message = "No tickets match your filter criteria.",
                        actionButtonText = "Create Request",
                        onActionClick = onNavigateToNewRequest,
                        modifier = Modifier.padding(top = 40.dp)
                    )
                }
            } else {
                items(requests, key = { it.id }) { item ->
                    MaintenanceTicketCard(
                        request = item,
                        onStatusChange = { newStatus ->
                            viewModel.updateMaintenanceStatus(item.id, newStatus)
                        },
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MaintenanceTicketCard(
    request: MaintenanceRequest,
    onStatusChange: (MaintenanceStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    var showStatusMenu by remember { mutableStateOf(false) }

    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .testTag("maintenance_item_${request.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
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
                    text = request.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                PriorityBadge(priority = request.priority)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${request.propertyName} • ${request.unitNumber}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = request.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Surface(
                        onClick = { showStatusMenu = true },
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            MaintenanceStatusBadge(status = request.status)
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = "Change Status",
                                modifier = Modifier.size(18.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = showStatusMenu,
                        onDismissRequest = { showStatusMenu = false }
                    ) {
                        MaintenanceStatus.values().forEach { status ->
                            DropdownMenuItem(
                                text = { Text(status.label) },
                                onClick = {
                                    onStatusChange(status)
                                    showStatusMenu = false
                                }
                            )
                        }
                    }
                }

                Text(
                    text = "Reported: ${request.reportedDate}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}
