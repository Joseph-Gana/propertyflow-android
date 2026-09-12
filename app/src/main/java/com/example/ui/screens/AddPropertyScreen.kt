package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.data.models.PropertyType
import com.example.ui.viewmodel.PropertyFlowViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPropertyScreen(
    viewModel: PropertyFlowViewModel,
    onNavigateBack: () -> Unit
) {
    val currencySymbol by viewModel.currencySymbol.collectAsState()
    val selectedCountry by viewModel.selectedCountry.collectAsState()

    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf(if (selectedCountry.code == "NG") "Lagos" else "") }
    var state by remember { mutableStateOf(if (selectedCountry.code == "NG") "Lagos" else "") }
    var selectedType by remember { mutableStateOf(PropertyType.APARTMENT) }
    var totalUnitsText by remember { mutableStateOf("4") }
    var monthlyRentText by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var typeDropdownExpanded by remember { mutableStateOf(false) }

    // Validation errors
    var nameError by remember { mutableStateOf(false) }
    var addressError by remember { mutableStateOf(false) }
    var cityError by remember { mutableStateOf(false) }
    var stateError by remember { mutableStateOf(false) }
    var rentError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Property", fontWeight = FontWeight.Bold) },
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
                .testTag("add_property_screen"),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "New Property Details",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Property Name
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    if (nameError) nameError = false
                },
                label = { Text("Property Name *") },
                placeholder = { Text("e.g. Victoria Crest Court") },
                isError = nameError,
                supportingText = if (nameError) { { Text("Property name is required") } } else null,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_property_name"),
                shape = RoundedCornerShape(12.dp)
            )

            // Property Type Dropdown
            ExposedDropdownMenuBox(
                expanded = typeDropdownExpanded,
                onExpandedChange = { typeDropdownExpanded = !typeDropdownExpanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedType.label,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Property Type *") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = typeDropdownExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .testTag("select_property_type"),
                    shape = RoundedCornerShape(12.dp)
                )

                ExposedDropdownMenu(
                    expanded = typeDropdownExpanded,
                    onDismissRequest = { typeDropdownExpanded = false }
                ) {
                    PropertyType.values().forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type.label) },
                            onClick = {
                                selectedType = type
                                typeDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            // Address
            OutlinedTextField(
                value = address,
                onValueChange = {
                    address = it
                    if (addressError) addressError = false
                },
                label = { Text("Street Address *") },
                placeholder = { Text("e.g. 25 Bourdillon Road, Ikoyi") },
                isError = addressError,
                supportingText = if (addressError) { { Text("Address is required") } } else null,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_property_address"),
                shape = RoundedCornerShape(12.dp)
            )

            // City and State Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = city,
                    onValueChange = {
                        city = it
                        if (cityError) cityError = false
                    },
                    label = { Text("City *") },
                    placeholder = { Text("e.g. Lagos / Abuja") },
                    isError = cityError,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("input_property_city"),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = state,
                    onValueChange = {
                        state = it
                        if (stateError) stateError = false
                    },
                    label = { Text("State *") },
                    placeholder = { Text("e.g. Lagos") },
                    isError = stateError,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("input_property_state"),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            // Total Units & Monthly Rental Income Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = totalUnitsText,
                    onValueChange = { totalUnitsText = it.filter { ch -> ch.isDigit() } },
                    label = { Text("Total Units *") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("input_property_units"),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = monthlyRentText,
                    onValueChange = {
                        monthlyRentText = it.filter { ch -> ch.isDigit() }
                        if (rentError) rentError = false
                    },
                    label = { Text("Monthly Rent ($currencySymbol) *") },
                    placeholder = { Text("e.g. 3500000") },
                    isError = rentError,
                    supportingText = if (rentError) { { Text("Invalid rent") } } else null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1.5f)
                        .testTag("input_property_rent"),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            // Description
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Property Description") },
                placeholder = { Text("Describe amenities, power supply, security, facilities...") },
                minLines = 3,
                maxLines = 5,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_property_description"),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Save Property Button
            Button(
                onClick = {
                    val units = totalUnitsText.toIntOrNull() ?: 1
                    val rent = monthlyRentText.toLongOrNull() ?: 0L

                    nameError = name.isBlank()
                    addressError = address.isBlank()
                    cityError = city.isBlank()
                    stateError = state.isBlank()
                    rentError = rent <= 0

                    if (!nameError && !addressError && !cityError && !stateError && !rentError) {
                        val success = viewModel.addProperty(
                            name = name,
                            address = address,
                            city = city,
                            state = state,
                            type = selectedType,
                            totalUnits = units,
                            monthlyRent = rent,
                            description = description
                        )
                        if (success) {
                            onNavigateBack()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("btn_save_property"),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(imageVector = Icons.Filled.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Save Property",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
