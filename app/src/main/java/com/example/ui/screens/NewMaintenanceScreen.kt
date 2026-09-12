package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.models.MaintenancePriority
import com.example.ui.viewmodel.PropertyFlowViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMaintenanceScreen(
    viewModel: PropertyFlowViewModel,
    onNavigateBack: () -> Unit
) {
    val properties by viewModel.properties.collectAsState()

    var propertyName by remember { mutableStateOf(properties.firstOrNull()?.name ?: "") }
    var unitNumber by remember { mutableStateOf("Flat 3B") }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedPriority by remember { mutableStateOf(MaintenancePriority.HIGH) }

    var propertyDropdownExpanded by remember { mutableStateOf(false) }
    var priorityDropdownExpanded by remember { mutableStateOf(false) }

    var titleError by remember { mutableStateOf(false) }
    var descError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Maintenance Ticket", fontWeight = FontWeight.Bold) },
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
                .testTag("new_maintenance_screen"),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Ticket Details",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Property Selector Dropdown
            ExposedDropdownMenuBox(
                expanded = propertyDropdownExpanded,
                onExpandedChange = { propertyDropdownExpanded = !propertyDropdownExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = propertyName,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Property *") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = propertyDropdownExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .testTag("select_maint_property"),
                    shape = RoundedCornerShape(12.dp)
                )

                ExposedDropdownMenu(
                    expanded = propertyDropdownExpanded,
                    onDismissRequest = { propertyDropdownExpanded = false }
                ) {
                    properties.forEach { p ->
                        DropdownMenuItem(
                            text = { Text(p.name) },
                            onClick = {
                                propertyName = p.name
                                propertyDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            // Unit Number
            OutlinedTextField(
                value = unitNumber,
                onValueChange = { unitNumber = it },
                label = { Text("Unit / Area *") },
                placeholder = { Text("e.g. Flat 3B or Compound / Generator House") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_maint_unit"),
                shape = RoundedCornerShape(12.dp)
            )

            // Issue Title
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    if (titleError) titleError = false
                },
                label = { Text("Issue Title *") },
                placeholder = { Text("e.g. Water plumbing leakage in master bathroom") },
                isError = titleError,
                supportingText = if (titleError) { { Text("Title is required") } } else null,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_maint_title"),
                shape = RoundedCornerShape(12.dp)
            )

            // Priority Selector Dropdown
            ExposedDropdownMenuBox(
                expanded = priorityDropdownExpanded,
                onExpandedChange = { priorityDropdownExpanded = !priorityDropdownExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedPriority.label,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Priority Level *") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = priorityDropdownExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .testTag("select_maint_priority"),
                    shape = RoundedCornerShape(12.dp)
                )

                ExposedDropdownMenu(
                    expanded = priorityDropdownExpanded,
                    onDismissRequest = { priorityDropdownExpanded = false }
                ) {
                    MaintenancePriority.values().forEach { priority ->
                        DropdownMenuItem(
                            text = { Text(priority.label) },
                            onClick = {
                                selectedPriority = priority
                                priorityDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            // Description
            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                    if (descError) descError = false
                },
                label = { Text("Detailed Description *") },
                placeholder = { Text("Provide technical specifics or technician observations...") },
                minLines = 3,
                maxLines = 6,
                isError = descError,
                supportingText = if (descError) { { Text("Description is required") } } else null,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_maint_desc"),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Submit Button
            Button(
                onClick = {
                    titleError = title.isBlank()
                    descError = description.isBlank()

                    if (!titleError && !descError) {
                        val success = viewModel.createMaintenanceRequest(
                            propertyName = propertyName,
                            unitNumber = unitNumber,
                            title = title,
                            description = description,
                            priority = selectedPriority,
                            reportedDate = "Today"
                        )
                        if (success) {
                            onNavigateBack()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("btn_save_maintenance"),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(imageVector = Icons.Filled.Build, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Submit Maintenance Ticket",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
