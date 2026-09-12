package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.data.models.PaymentMethod
import com.example.ui.viewmodel.PropertyFlowViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordPaymentScreen(
    viewModel: PropertyFlowViewModel,
    onNavigateBack: () -> Unit
) {
    val tenants by viewModel.tenants.collectAsState()
    val properties by viewModel.properties.collectAsState()
    val currencySymbol by viewModel.currencySymbol.collectAsState()

    var selectedTenant by remember { mutableStateOf(tenants.firstOrNull()?.name ?: "") }
    var propertyName by remember { mutableStateOf(tenants.firstOrNull()?.propertyName ?: properties.firstOrNull()?.name ?: "") }
    var unitNumber by remember { mutableStateOf(tenants.firstOrNull()?.unitNumber ?: "Flat 1A") }
    var amountText by remember { mutableStateOf(tenants.firstOrNull()?.monthlyRent?.toString() ?: "850000") }
    var paymentDate by remember { mutableStateOf("Today, March 2025") }
    var selectedMethod by remember { mutableStateOf(PaymentMethod.BANK_TRANSFER) }
    var referenceNumber by remember { mutableStateOf("TRX-${System.currentTimeMillis().toString().takeLast(6)}") }
    var notes by remember { mutableStateOf("") }

    var tenantDropdownExpanded by remember { mutableStateOf(false) }
    var methodDropdownExpanded by remember { mutableStateOf(false) }

    var amountError by remember { mutableStateOf(false) }
    var tenantError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Record Payment", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
                .testTag("record_payment_screen"),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Transaction Details",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Tenant Selector Dropdown
            ExposedDropdownMenuBox(
                expanded = tenantDropdownExpanded,
                onExpandedChange = { tenantDropdownExpanded = !tenantDropdownExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedTenant,
                    onValueChange = {
                        selectedTenant = it
                        if (tenantError) tenantError = false
                    },
                    label = { Text("Tenant Name *") },
                    isError = tenantError,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = tenantDropdownExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .testTag("input_payment_tenant"),
                    shape = RoundedCornerShape(12.dp)
                )

                ExposedDropdownMenu(
                    expanded = tenantDropdownExpanded,
                    onDismissRequest = { tenantDropdownExpanded = false }
                ) {
                    tenants.forEach { t ->
                        DropdownMenuItem(
                            text = { Text("${t.name} (${t.propertyName} — ${t.unitNumber})") },
                            onClick = {
                                selectedTenant = t.name
                                propertyName = t.propertyName
                                unitNumber = t.unitNumber
                                amountText = t.monthlyRent.toString()
                                tenantDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            // Property and Unit
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = propertyName,
                    onValueChange = { propertyName = it },
                    label = { Text("Property *") },
                    modifier = Modifier
                        .weight(1.5f)
                        .testTag("input_payment_property"),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = unitNumber,
                    onValueChange = { unitNumber = it },
                    label = { Text("Unit *") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("input_payment_unit"),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            // Amount
            OutlinedTextField(
                value = amountText,
                onValueChange = {
                    amountText = it.filter { ch -> ch.isDigit() }
                    if (amountError) amountError = false
                },
                label = { Text("Amount Paid ($currencySymbol) *") },
                isError = amountError,
                supportingText = if (amountError) { { Text("Please enter a valid amount") } } else null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_payment_amount"),
                shape = RoundedCornerShape(12.dp)
            )

            // Payment Date
            OutlinedTextField(
                value = paymentDate,
                onValueChange = { paymentDate = it },
                label = { Text("Payment Date *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_payment_date"),
                shape = RoundedCornerShape(12.dp)
            )

            // Payment Method Selector
            ExposedDropdownMenuBox(
                expanded = methodDropdownExpanded,
                onExpandedChange = { methodDropdownExpanded = !methodDropdownExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedMethod.label,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Payment Method *") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = methodDropdownExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .testTag("select_payment_method"),
                    shape = RoundedCornerShape(12.dp)
                )

                ExposedDropdownMenu(
                    expanded = methodDropdownExpanded,
                    onDismissRequest = { methodDropdownExpanded = false }
                ) {
                    PaymentMethod.values().forEach { method ->
                        DropdownMenuItem(
                            text = { Text(method.label) },
                            onClick = {
                                selectedMethod = method
                                methodDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            // Reference Number
            OutlinedTextField(
                value = referenceNumber,
                onValueChange = { referenceNumber = it },
                label = { Text("Transaction Reference") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_payment_reference"),
                shape = RoundedCornerShape(12.dp)
            )

            // Notes
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes / Remarks") },
                placeholder = { Text("e.g. Annual rent payment confirmed via GTBank transfer") },
                minLines = 2,
                maxLines = 4,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_payment_notes"),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Save Payment Button
            Button(
                onClick = {
                    val amount = amountText.toLongOrNull() ?: 0L
                    tenantError = selectedTenant.isBlank()
                    amountError = amount <= 0

                    if (!tenantError && !amountError) {
                        val success = viewModel.recordPayment(
                            tenantName = selectedTenant,
                            propertyName = propertyName,
                            unitNumber = unitNumber,
                            amount = amount,
                            paymentDate = paymentDate,
                            paymentMethod = selectedMethod,
                            reference = referenceNumber,
                            notes = notes
                        )
                        if (success) {
                            onNavigateBack()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("btn_save_payment"),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(imageVector = Icons.Filled.Receipt, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Confirm & Save Payment",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
