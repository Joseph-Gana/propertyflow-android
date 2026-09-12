package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.CurrencyOption
import com.example.data.models.SupportedCountry
import com.example.ui.components.SectionHeader
import com.example.ui.viewmodel.PropertyFlowViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: PropertyFlowViewModel,
    onNavigateBack: () -> Unit
) {
    val selectedCountry by viewModel.selectedCountry.collectAsState()
    val currencyCode by viewModel.currencyCode.collectAsState()
    val currencySymbol by viewModel.currencySymbol.collectAsState()

    var showMarketDialog by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var biometricLock by remember { mutableStateOf(true) }
    var autoSync by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings & Preferences", fontWeight = FontWeight.Bold) },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .testTag("settings_screen"),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Market / Region Configuration
            item {
                SectionHeader(
                    title = "Region & Currency Configuration",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
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
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Active Market",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { showMarketDialog = true }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(text = selectedCountry.flagEmoji, fontSize = 28.sp)
                                Column {
                                    Text(
                                        text = selectedCountry.displayName,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Currency: ${selectedCountry.currencyCode} (${selectedCountry.currencySymbol})",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                            TextButton(onClick = { showMarketDialog = true }) {
                                Text("Switch")
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                        )

                        Text(
                            text = "Supported Currencies",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(
                                CurrencyOption("NGN", "₦", "Nigerian Naira"),
                                CurrencyOption("USD", "$", "US Dollar"),
                                CurrencyOption("GBP", "£", "British Pound"),
                                CurrencyOption("CAD", "$", "Canadian Dollar"),
                                CurrencyOption("GHS", "GH₵", "Ghana Cedi")
                            ).forEach { opt ->
                                val isSelected = currencyCode == opt.code
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { viewModel.setCurrency(opt.code, opt.symbol) },
                                    label = { Text("${opt.code} (${opt.symbol})") },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // App Preferences
            item {
                Spacer(modifier = Modifier.height(16.dp))
                SectionHeader(
                    title = "App Preferences",
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
                    Column(modifier = Modifier.padding(16.dp)) {
                        PreferenceSwitchRow(
                            title = "Push Notifications",
                            subtitle = "Alerts for rent payments and maintenance tickets",
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it }
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                        )

                        PreferenceSwitchRow(
                            title = "Biometric Security",
                            subtitle = "Require fingerprint or face authentication on app open",
                            checked = biometricLock,
                            onCheckedChange = { biometricLock = it }
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                        )

                        PreferenceSwitchRow(
                            title = "Offline Persistence Cache",
                            subtitle = "Save portfolio changes offline and auto-sync",
                            checked = autoSync,
                            onCheckedChange = { autoSync = it }
                        )
                    }
                }
            }

            // Quick reset button to Nigeria
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    OutlinedButton(
                        onClick = { viewModel.switchCountry(SupportedCountry.NIGERIA) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Reset to Nigeria Default 🇳🇬 (₦ NGN)")
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "PropertyFlow v1.0.0 Enterprise\n\"Smart Property Management, Simplified.\"",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Market Switch Dialog
        if (showMarketDialog) {
            AlertDialog(
                onDismissRequest = { showMarketDialog = false },
                title = { Text("Select Active Market") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Switching markets adapts currency symbols, sample properties, and financial valuations dynamically.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        SupportedCountry.values().forEach { country ->
                            Surface(
                                onClick = {
                                    viewModel.switchCountry(country)
                                    showMarketDialog = false
                                },
                                shape = RoundedCornerShape(10.dp),
                                color = if (country == selectedCountry) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Text(text = country.flagEmoji, fontSize = 24.sp)
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = country.displayName,
                                            fontWeight = FontWeight.SemiBold,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Text(
                                            text = "${country.currencyCode} (${country.currencySymbol}) • e.g. ${country.defaultLocation}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    if (country == selectedCountry) {
                                        Icon(
                                            imageVector = Icons.Filled.Check,
                                            contentDescription = "Selected",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showMarketDialog = false }) {
                        Text("Done")
                    }
                }
            )
        }
    }
}

@Composable
private fun PreferenceSwitchRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}
