package com.example.data.models

enum class SupportedCountry(
    val code: String,
    val displayName: String,
    val flagEmoji: String,
    val currencyCode: String,
    val currencySymbol: String,
    val defaultLocation: String
) {
    NIGERIA("NG", "Nigeria", "🇳🇬", "NGN", "₦", "Lagos, Nigeria"),
    UNITED_KINGDOM("GB", "United Kingdom", "🇬🇧", "GBP", "£", "London, UK"),
    UNITED_STATES("US", "United States", "🇺🇸", "USD", "$", "New York, USA"),
    CANADA("CA", "Canada", "🇨🇦", "CAD", "C$", "Toronto, Canada"),
    GHANA("GH", "Ghana", "🇬🇭", "GHS", "GH₵", "Accra, Ghana")
}

data class CurrencyOption(
    val code: String,
    val symbol: String,
    val name: String
)
