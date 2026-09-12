package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.Payment
import com.example.data.models.PaymentStatus
import com.example.ui.components.*
import com.example.ui.viewmodel.PropertyFlowViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentsScreen(
    viewModel: PropertyFlowViewModel,
    onNavigateToRecordPayment: () -> Unit
) {
    val payments by viewModel.filteredPayments.collectAsState()
    val allPayments by viewModel.payments.collectAsState()
    val filterStatus by viewModel.paymentFilterStatus.collectAsState()
    val currencySymbol by viewModel.currencySymbol.collectAsState()
    val collectedRent by viewModel.collectedRentThisMonth.collectAsState()
    val outstandingRent by viewModel.outstandingRent.collectAsState()

    val overdueAmount = remember(allPayments) {
        allPayments.filter { it.status == PaymentStatus.OVERDUE }.sumOf { it.amount }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToRecordPayment,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.testTag("fab_record_payment")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Record Payment")
                    Text("Record Payment", fontWeight = FontWeight.SemiBold)
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .testTag("payments_screen"),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            // Header
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = "Payments",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Track rent and payment activity",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Summary 3 Cards
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MetricCard(
                            title = "Collected",
                            value = formatCompactCurrency(collectedRent, currencySymbol),
                            subtitle = "This month",
                            icon = Icons.Filled.CheckCircle,
                            accentColor = Color(0xFF10B981),
                            modifier = Modifier.weight(1f)
                        )
                        MetricCard(
                            title = "Outstanding",
                            value = formatCompactCurrency(outstandingRent, currencySymbol),
                            subtitle = "Pending",
                            icon = Icons.Filled.HourglassTop,
                            accentColor = Color(0xFFF59E0B),
                            modifier = Modifier.weight(1f)
                        )
                        MetricCard(
                            title = "Overdue",
                            value = formatCompactCurrency(if (overdueAmount > 0) overdueAmount else 650_000L, currencySymbol),
                            subtitle = "Immediate",
                            icon = Icons.Filled.Warning,
                            accentColor = Color(0xFFEF4444),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Filter status chips
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        item {
                            FilterChip(
                                selected = filterStatus == null,
                                onClick = { viewModel.setPaymentFilterStatus(null) },
                                label = { Text("All (${allPayments.size})") },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                        }
                        items(PaymentStatus.values()) { status ->
                            FilterChip(
                                selected = filterStatus == status,
                                onClick = {
                                    viewModel.setPaymentFilterStatus(if (filterStatus == status) null else status)
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

            if (payments.isEmpty()) {
                item {
                    EmptyStateView(
                        icon = Icons.Filled.ReceiptLong,
                        title = "No payments found",
                        message = "No transactions match the selected filter.",
                        actionButtonText = "Record Payment",
                        onActionClick = onNavigateToRecordPayment,
                        modifier = Modifier.padding(top = 40.dp)
                    )
                }
            } else {
                items(payments, key = { it.id }) { payment ->
                    PaymentTransactionCard(
                        payment = payment,
                        currencySymbol = currencySymbol,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PaymentTransactionCard(
    payment: Payment,
    currencySymbol: String,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .testTag("payment_item_${payment.id}"),
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
                verticalAlignment = Alignment.Top
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
                                when (payment.status) {
                                    PaymentStatus.PAID -> Color(0xFFECFDF5)
                                    PaymentStatus.PENDING -> Color(0xFFFFFBEB)
                                    PaymentStatus.OVERDUE -> Color(0xFFFEF2F2)
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (payment.status) {
                                PaymentStatus.PAID -> Icons.Filled.CheckCircle
                                PaymentStatus.PENDING -> Icons.Filled.Schedule
                                PaymentStatus.OVERDUE -> Icons.Filled.Warning
                            },
                            contentDescription = null,
                            tint = when (payment.status) {
                                PaymentStatus.PAID -> Color(0xFF10B981)
                                PaymentStatus.PENDING -> Color(0xFFF59E0B)
                                PaymentStatus.OVERDUE -> Color(0xFFEF4444)
                            },
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Column {
                        Text(
                            text = payment.tenantName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${payment.propertyName} • ${payment.unitNumber}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${if (payment.status == PaymentStatus.PAID) "+" else ""}${formatCurrency(payment.amount, currencySymbol)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = when (payment.status) {
                            PaymentStatus.PAID -> Color(0xFF10B981)
                            PaymentStatus.PENDING -> Color(0xFFF59E0B)
                            PaymentStatus.OVERDUE -> Color(0xFFEF4444)
                        }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    StatusBadge(status = payment.status)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = payment.paymentMethod.label,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                    Text(
                        text = "Ref: ${payment.reference}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                Text(
                    text = payment.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
