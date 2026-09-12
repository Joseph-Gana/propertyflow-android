package com.example.data.datasource

import com.example.R
import com.example.data.models.*

object SampleData {

    val defaultCountry = SupportedCountry.NIGERIA

    val currencyOptions = listOf(
        CurrencyOption("NGN", "₦", "Nigerian Naira (₦)"),
        CurrencyOption("GBP", "£", "British Pound (£)"),
        CurrencyOption("USD", "$", "US Dollar ($)"),
        CurrencyOption("CAD", "C$", "Canadian Dollar (C$)"),
        CurrencyOption("GHS", "GH₵", "Ghanaian Cedi (GH₵)")
    )

    fun getPropertiesForCountry(country: SupportedCountry): List<Property> {
        return when (country) {
            SupportedCountry.NIGERIA -> nigerianProperties
            SupportedCountry.UNITED_KINGDOM -> ukProperties
            SupportedCountry.UNITED_STATES -> usProperties
            SupportedCountry.CANADA -> canadaProperties
            SupportedCountry.GHANA -> ghanaProperties
        }
    }

    fun getTenantsForCountry(country: SupportedCountry): List<Tenant> {
        return when (country) {
            SupportedCountry.NIGERIA -> nigerianTenants
            SupportedCountry.UNITED_KINGDOM -> ukTenants
            SupportedCountry.UNITED_STATES -> usTenants
            SupportedCountry.CANADA -> canadaTenants
            SupportedCountry.GHANA -> ghanaTenants
        }
    }

    fun getPaymentsForCountry(country: SupportedCountry): List<Payment> {
        return when (country) {
            SupportedCountry.NIGERIA -> nigerianPayments
            SupportedCountry.UNITED_KINGDOM -> ukPayments
            SupportedCountry.UNITED_STATES -> usPayments
            SupportedCountry.CANADA -> canadaPayments
            SupportedCountry.GHANA -> ghanaPayments
        }
    }

    fun getMaintenanceForCountry(country: SupportedCountry): List<MaintenanceRequest> {
        return when (country) {
            SupportedCountry.NIGERIA -> nigerianMaintenance
            SupportedCountry.UNITED_KINGDOM -> ukMaintenance
            SupportedCountry.UNITED_STATES -> usMaintenance
            SupportedCountry.CANADA -> canadaMaintenance
            SupportedCountry.GHANA -> ghanaMaintenance
        }
    }

    // --- NIGERIA DEMO DATA ---
    val nigerianProperties = listOf(
        Property(
            id = "prop-ng-1",
            name = "Lekki Gardens Residence",
            address = "Plot 14, Admiralty Way, Lekki Phase 1",
            city = "Lekki",
            state = "Lagos",
            countryCode = "NG",
            type = PropertyType.APARTMENT,
            totalUnits = 12,
            occupiedUnits = 11,
            monthlyRentalIncome = 8_400_000L,
            status = PropertyStatus.ACTIVE,
            imageResId = R.drawable.img_property_lekki,
            description = "High-end residential serviced apartments in prime Lekki Phase 1 with 24/7 security, dual generators, swimming pool, and dedicated parking spaces.",
            totalValuation = 55_000_000L,
            units = listOf(
                PropertyUnit("u-ng-1", "Flat 1A", "James Okafor", 850_000L, "Paid"),
                PropertyUnit("u-ng-2", "Flat 2A", "Daniel Adekunle", 700_000L, "Paid"),
                PropertyUnit("u-ng-3", "Flat 3B", "Sarah Adeyemi", 720_000L, "Pending"),
                PropertyUnit("u-ng-4", "Flat 4A", null, 800_000L, "Available"),
                PropertyUnit("u-ng-5", "Flat 5B", "Emeka Nnamdi", 750_000L, "Paid"),
                PropertyUnit("u-ng-6", "Flat 6A", "Bolanle Cole", 850_000L, "Paid")
            )
        ),
        Property(
            id = "prop-ng-2",
            name = "Ikeja Central Apartments",
            address = "18 Allen Avenue, Ikeja",
            city = "Ikeja",
            state = "Lagos",
            countryCode = "NG",
            type = PropertyType.APARTMENT,
            totalUnits = 8,
            occupiedUnits = 7,
            monthlyRentalIncome = 5_600_000L,
            status = PropertyStatus.ACTIVE,
            imageResId = R.drawable.img_property_ikeja,
            description = "Modern contemporary apartment building located near business hubs and the international airport. Equipped with fiber internet and water treatment facility.",
            totalValuation = 42_000_000L,
            units = listOf(
                PropertyUnit("u-ng-21", "Flat 4A", "Sarah Adeyemi", 720_000L, "Paid"),
                PropertyUnit("u-ng-22", "Flat 5A", "Chidinma Eze", 700_000L, "Paid"),
                PropertyUnit("u-ng-23", "Flat 1B", null, 680_000L, "Available"),
                PropertyUnit("u-ng-24", "Flat 2B", "Femi Awolowo", 750_000L, "Paid")
            )
        ),
        Property(
            id = "prop-ng-3",
            name = "Riverside Court",
            address = "12 Trans Amadi Industrial Layout",
            city = "Port Harcourt",
            state = "Rivers",
            countryCode = "NG",
            type = PropertyType.DUPLEX,
            totalUnits = 6,
            occupiedUnits = 5,
            monthlyRentalIncome = 3_900_000L,
            status = PropertyStatus.ACTIVE,
            imageResId = R.drawable.img_property_abuja,
            description = "Executive duplexes nestled in a secure gated community with serene garden views, underground drainage, and perimeter CCTV surveillance.",
            totalValuation = 32_000_000L,
            units = listOf(
                PropertyUnit("u-ng-31", "Flat 2A", "Michael Williams", 650_000L, "Overdue"),
                PropertyUnit("u-ng-32", "Flat 1A", "Blessing Peters", 650_000L, "Paid"),
                PropertyUnit("u-ng-33", "Flat 3C", null, 650_000L, "Available")
            )
        ),
        Property(
            id = "prop-ng-4",
            name = "Abuja Heights",
            address = "45 Aminu Kano Crescent, Wuse 2",
            city = "Wuse 2",
            state = "Abuja",
            countryCode = "NG",
            type = PropertyType.DETACHED_HOUSE,
            totalUnits = 10,
            occupiedUnits = 9,
            monthlyRentalIncome = 7_200_000L,
            status = PropertyStatus.ACTIVE,
            imageResId = R.drawable.img_property_abuja,
            description = "Luxury detached villas with private boys' quarters, landscaped greenery, solar inverters, and automated electric gates in the heart of Abuja.",
            totalValuation = 65_000_000L,
            units = listOf(
                PropertyUnit("u-ng-41", "Villa 1", "Aliyu Abubakar", 800_000L, "Paid"),
                PropertyUnit("u-ng-42", "Villa 2", "Zainab Bello", 800_000L, "Paid"),
                PropertyUnit("u-ng-43", "Villa 3", null, 800_000L, "Available")
            )
        ),
        Property(
            id = "prop-ng-5",
            name = "Maple Estate",
            address = "Ring Road / Bodija Extension",
            city = "Ibadan",
            state = "Oyo",
            countryCode = "NG",
            type = PropertyType.ESTATE,
            totalUnits = 12,
            occupiedUnits = 12,
            monthlyRentalIncome = 4_800_000L,
            status = PropertyStatus.FULL,
            imageResId = R.drawable.img_property_lekki,
            description = "Family-friendly suburban estate with children's playground, central solar illumination, tarred access roads, and uniform security personnel.",
            totalValuation = 28_000_000L,
            units = listOf(
                PropertyUnit("u-ng-51", "Unit 1", "Tunde Balogun", 400_000L, "Paid"),
                PropertyUnit("u-ng-52", "Unit 2", "Kemi Oladipo", 400_000L, "Paid")
            )
        )
    )

    val nigerianTenants = listOf(
        Tenant(
            id = "t-ng-1",
            name = "James Okafor",
            phone = "+234 803 123 4567",
            email = "james.okafor@example.ng",
            propertyId = "prop-ng-1",
            propertyName = "Lekki Gardens Residence",
            unitNumber = "Flat 3B",
            monthlyRent = 850_000L,
            leaseStart = "01 Jan 2026",
            leaseEnd = "31 Dec 2026",
            paymentStatus = PaymentStatus.PAID,
            deposit = 1_700_000L,
            paymentDueDate = "1st of every month",
            avatarResId = null
        ),
        Tenant(
            id = "t-ng-2",
            name = "Sarah Adeyemi",
            phone = "+234 802 987 6543",
            email = "sarah.adeyemi@example.ng",
            propertyId = "prop-ng-2",
            propertyName = "Ikeja Central Apartments",
            unitNumber = "Flat 4A",
            monthlyRent = 720_000L,
            leaseStart = "15 Mar 2025",
            leaseEnd = "14 Mar 2027",
            paymentStatus = PaymentStatus.PAID,
            deposit = 1_440_000L,
            paymentDueDate = "15th of every month",
            avatarResId = null
        ),
        Tenant(
            id = "t-ng-3",
            name = "Michael Williams",
            phone = "+234 805 456 7890",
            email = "m.williams@example.ng",
            propertyId = "prop-ng-3",
            propertyName = "Riverside Court",
            unitNumber = "Flat 2A",
            monthlyRent = 650_000L,
            leaseStart = "01 Jun 2025",
            leaseEnd = "31 May 2026",
            paymentStatus = PaymentStatus.OVERDUE,
            deposit = 1_300_000L,
            paymentDueDate = "1st of every month",
            avatarResId = null
        ),
        Tenant(
            id = "t-ng-4",
            name = "Daniel Adekunle",
            phone = "+234 807 334 2211",
            email = "daniel.adekunle@example.ng",
            propertyId = "prop-ng-1",
            propertyName = "Lekki Gardens Residence",
            unitNumber = "Flat 2A",
            monthlyRent = 700_000L,
            leaseStart = "01 Feb 2026",
            leaseEnd = "31 Jan 2027",
            paymentStatus = PaymentStatus.PAID,
            deposit = 1_400_000L,
            paymentDueDate = "1st of every month",
            avatarResId = null
        ),
        Tenant(
            id = "t-ng-5",
            name = "Chidinma Eze",
            phone = "+234 818 776 5544",
            email = "chidinma.eze@example.ng",
            propertyId = "prop-ng-2",
            propertyName = "Ikeja Central Apartments",
            unitNumber = "Flat 5A",
            monthlyRent = 700_000L,
            leaseStart = "01 Aug 2025",
            leaseEnd = "31 Jul 2026",
            paymentStatus = PaymentStatus.PENDING,
            deposit = 1_400_000L,
            paymentDueDate = "5th of every month",
            avatarResId = null
        ),
        Tenant(
            id = "t-ng-6",
            name = "Aliyu Abubakar",
            phone = "+234 809 112 3344",
            email = "aliyu.abubakar@example.ng",
            propertyId = "prop-ng-4",
            propertyName = "Abuja Heights",
            unitNumber = "Villa 1",
            monthlyRent = 800_000L,
            leaseStart = "01 Nov 2025",
            leaseEnd = "31 Oct 2026",
            paymentStatus = PaymentStatus.PAID,
            deposit = 1_600_000L,
            paymentDueDate = "1st of every month",
            avatarResId = null
        )
    )

    val nigerianPayments = listOf(
        Payment(
            id = "pay-ng-1",
            tenantName = "James Okafor",
            propertyName = "Lekki Gardens Residence",
            unitNumber = "Flat 3B",
            amount = 850_000L,
            date = "Today, 10:45 AM",
            paymentMethod = PaymentMethod.BANK_TRANSFER,
            reference = "TRX-NG-90821",
            status = PaymentStatus.PAID,
            notes = "September 2026 rent payment via GTBank transfer."
        ),
        Payment(
            id = "pay-ng-2",
            tenantName = "Sarah Adeyemi",
            propertyName = "Ikeja Central Apartments",
            unitNumber = "Flat 4A",
            amount = 720_000L,
            date = "Yesterday, 03:20 PM",
            paymentMethod = PaymentMethod.BANK_TRANSFER,
            reference = "TRX-NG-88942",
            status = PaymentStatus.PAID,
            notes = "Direct transfer from Zenith Bank."
        ),
        Payment(
            id = "pay-ng-3",
            tenantName = "Michael Williams",
            propertyName = "Riverside Court",
            unitNumber = "Flat 2A",
            amount = 650_000L,
            date = "01 Sep 2026",
            paymentMethod = PaymentMethod.DIRECT_DEBIT,
            reference = "TRX-NG-77120",
            status = PaymentStatus.OVERDUE,
            notes = "Direct debit attempt failed due to insufficient balance."
        ),
        Payment(
            id = "pay-ng-4",
            tenantName = "Daniel Adekunle",
            propertyName = "Lekki Gardens Residence",
            unitNumber = "Flat 2A",
            amount = 700_000L,
            date = "28 Aug 2026",
            paymentMethod = PaymentMethod.POS,
            reference = "POS-LK-44129",
            status = PaymentStatus.PAID,
            notes = "Paid at estate management facility office."
        ),
        Payment(
            id = "pay-ng-5",
            tenantName = "Chidinma Eze",
            propertyName = "Ikeja Central Apartments",
            unitNumber = "Flat 5A",
            amount = 700_000L,
            date = "27 Aug 2026",
            paymentMethod = PaymentMethod.BANK_TRANSFER,
            reference = "TRX-NG-66512",
            status = PaymentStatus.PAID,
            notes = "Advance rent settlement."
        )
    )

    val nigerianMaintenance = listOf(
        MaintenanceRequest(
            id = "maint-ng-1",
            title = "Water leakage",
            propertyName = "Lekki Gardens Residence",
            unitNumber = "Flat 3B",
            description = "Master bathroom overhead pipe connection leaking into ceiling drywall.",
            priority = MaintenancePriority.HIGH,
            status = MaintenanceStatus.IN_PROGRESS,
            reportedDate = "09 Sep 2026",
            tenantName = "James Okafor"
        ),
        MaintenanceRequest(
            id = "maint-ng-2",
            title = "Faulty air conditioner",
            propertyName = "Ikeja Central Apartments",
            unitNumber = "Flat 5A",
            description = "Living room 2HP split inverter AC compressor not cooling, fan blowing warm air.",
            priority = MaintenancePriority.MEDIUM,
            status = MaintenanceStatus.OPEN,
            reportedDate = "10 Sep 2026",
            tenantName = "Chidinma Eze"
        ),
        MaintenanceRequest(
            id = "maint-ng-3",
            title = "Broken Light",
            propertyName = "Riverside Court",
            unitNumber = "Flat 2A",
            description = "External security floodlight bulb burned out and fixture requires replacement.",
            priority = MaintenancePriority.LOW,
            status = MaintenanceStatus.COMPLETED,
            reportedDate = "04 Sep 2026",
            tenantName = "Michael Williams"
        ),
        MaintenanceRequest(
            id = "maint-ng-4",
            title = "Intercom and gate buzzer repair",
            propertyName = "Abuja Heights",
            unitNumber = "Villa 1",
            description = "Main gate intercom not ringing in kitchen receiver module.",
            priority = MaintenancePriority.MEDIUM,
            status = MaintenanceStatus.OPEN,
            reportedDate = "11 Sep 2026",
            tenantName = "Aliyu Abubakar"
        ),
        MaintenanceRequest(
            id = "maint-ng-5",
            title = "Central generator maintenance",
            propertyName = "Lekki Gardens Residence",
            unitNumber = "Facility",
            description = "Scheduled 250-hour diesel engine oil and filter change.",
            priority = MaintenancePriority.HIGH,
            status = MaintenanceStatus.COMPLETED,
            reportedDate = "02 Sep 2026",
            tenantName = "Estate Facility"
        )
    )

    val initialNotifications = listOf(
        NotificationItem(
            id = "notif-1",
            title = "Rent Payment Received",
            message = "Rent payment of ₦850,000 received from James Okafor (Lekki Gardens - Flat 3B).",
            timeAgo = "15m ago",
            isRead = false,
            type = NotificationType.PAYMENT
        ),
        NotificationItem(
            id = "notif-2",
            title = "Maintenance Attention Needed",
            message = "Maintenance request at Lekki Gardens requires immediate attention (Water leakage).",
            timeAgo = "2h ago",
            isRead = false,
            type = NotificationType.MAINTENANCE
        ),
        NotificationItem(
            id = "notif-3",
            title = "Upcoming Rent Due",
            message = "Sarah Adeyemi's rent is due in 3 days (Ikeja Central - Flat 4A).",
            timeAgo = "5h ago",
            isRead = false,
            type = NotificationType.LEASE
        ),
        NotificationItem(
            id = "notif-4",
            title = "Payment Overdue Alert",
            message = "Michael Williams has an overdue payment of ₦650,000 at Riverside Court.",
            timeAgo = "1d ago",
            isRead = true,
            type = NotificationType.PAYMENT
        )
    )

    // --- UK DEMO DATA ---
    val ukProperties = listOf(
        Property(
            id = "prop-uk-1",
            name = "Oakwood Residence",
            address = "24 Richmond Hill, Richmond",
            city = "London",
            state = "Greater London",
            countryCode = "GB",
            type = PropertyType.APARTMENT,
            totalUnits = 8,
            occupiedUnits = 8,
            monthlyRentalIncome = 4_850L,
            status = PropertyStatus.FULL,
            imageResId = R.drawable.img_property_lekki,
            description = "Victorian conversion luxury apartments close to Richmond Park and tube station.",
            totalValuation = 2_800_000L
        ),
        Property(
            id = "prop-uk-2",
            name = "Kensington Mews",
            address = "7 Queen's Gate Mews, South Kensington",
            city = "London",
            state = "Greater London",
            countryCode = "GB",
            type = PropertyType.DUPLEX,
            totalUnits = 4,
            occupiedUnits = 3,
            monthlyRentalIncome = 7_200L,
            status = PropertyStatus.ACTIVE,
            imageResId = R.drawable.img_property_ikeja,
            description = "Quiet cobblestone mews houses with private garage and rooftop terrace.",
            totalValuation = 4_500_000L
        ),
        Property(
            id = "prop-uk-3",
            name = "Canary Wharf Suites",
            address = "32 Marsh Wall, Isle of Dogs",
            city = "London",
            state = "Greater London",
            countryCode = "GB",
            type = PropertyType.APARTMENT,
            totalUnits = 10,
            occupiedUnits = 9,
            monthlyRentalIncome = 11_500L,
            status = PropertyStatus.ACTIVE,
            imageResId = R.drawable.img_property_abuja,
            description = "High-rise modern apartments overlooking the docks with concierge and gym.",
            totalValuation = 5_200_000L
        ),
        Property(
            id = "prop-uk-4",
            name = "Manchester Crest",
            address = "15 Deansgate Square",
            city = "Manchester",
            state = "Greater Manchester",
            countryCode = "GB",
            type = PropertyType.APARTMENT,
            totalUnits = 6,
            occupiedUnits = 5,
            monthlyRentalIncome = 3_400L,
            status = PropertyStatus.ACTIVE,
            imageResId = R.drawable.img_property_lekki,
            description = "City centre contemporary development with premium amenity deck.",
            totalValuation = 1_650_000L
        )
    )

    val ukTenants = listOf(
        Tenant(
            id = "t-uk-1",
            name = "Oliver Smith",
            phone = "+44 7700 900123",
            email = "oliver.smith@example.co.uk",
            propertyId = "prop-uk-1",
            propertyName = "Oakwood Residence",
            unitNumber = "Flat 2",
            monthlyRent = 1_850L,
            leaseStart = "01 Jan 2026",
            leaseEnd = "31 Dec 2026",
            paymentStatus = PaymentStatus.PAID,
            deposit = 2_100L,
            paymentDueDate = "1st of every month"
        ),
        Tenant(
            id = "t-uk-2",
            name = "Emma Watson",
            phone = "+44 7700 900456",
            email = "emma.watson@example.co.uk",
            propertyId = "prop-uk-2",
            propertyName = "Kensington Mews",
            unitNumber = "Mews 3",
            monthlyRent = 2_400L,
            leaseStart = "01 Feb 2026",
            leaseEnd = "31 Jan 2027",
            paymentStatus = PaymentStatus.PAID,
            deposit = 3_000L,
            paymentDueDate = "1st of every month"
        ),
        Tenant(
            id = "t-uk-3",
            name = "Harry Davies",
            phone = "+44 7700 900789",
            email = "harry.davies@example.co.uk",
            propertyId = "prop-uk-3",
            propertyName = "Canary Wharf Suites",
            unitNumber = "Suite 8A",
            monthlyRent = 1_950L,
            leaseStart = "15 Mar 2026",
            leaseEnd = "14 Mar 2027",
            paymentStatus = PaymentStatus.OVERDUE,
            deposit = 2_250L,
            paymentDueDate = "15th of every month"
        )
    )

    val ukPayments = listOf(
        Payment(
            id = "pay-uk-1",
            tenantName = "Oliver Smith",
            propertyName = "Oakwood Residence",
            unitNumber = "Flat 2",
            amount = 1_850L,
            date = "Today, 09:15 AM",
            paymentMethod = PaymentMethod.BANK_TRANSFER,
            reference = "BACS-UK-1092",
            status = PaymentStatus.PAID,
            notes = "Direct BACS payment Barclays Bank."
        ),
        Payment(
            id = "pay-uk-2",
            tenantName = "Emma Watson",
            propertyName = "Kensington Mews",
            unitNumber = "Mews 3",
            amount = 2_400L,
            date = "Yesterday, 04:30 PM",
            paymentMethod = PaymentMethod.DIRECT_DEBIT,
            reference = "DD-UK-7711",
            status = PaymentStatus.PAID,
            notes = "Standing order."
        ),
        Payment(
            id = "pay-uk-3",
            tenantName = "Harry Davies",
            propertyName = "Canary Wharf Suites",
            unitNumber = "Suite 8A",
            amount = 1_950L,
            date = "01 Sep 2026",
            paymentMethod = PaymentMethod.BANK_TRANSFER,
            reference = "BACS-UK-0981",
            status = PaymentStatus.OVERDUE,
            notes = "Pending tenant authorization."
        )
    )

    val ukMaintenance = listOf(
        MaintenanceRequest(
            id = "maint-uk-1",
            title = "Boiler heating pressure drop",
            propertyName = "Oakwood Residence",
            unitNumber = "Flat 2",
            description = "Combi boiler error code F22 indicating low hydraulic pressure.",
            priority = MaintenancePriority.HIGH,
            status = MaintenanceStatus.IN_PROGRESS,
            reportedDate = "08 Sep 2026",
            tenantName = "Oliver Smith"
        ),
        MaintenanceRequest(
            id = "maint-uk-2",
            title = "Radiator valve leak",
            propertyName = "Kensington Mews",
            unitNumber = "Mews 3",
            description = "Thermostatic radiator valve dripping onto parquet flooring.",
            priority = MaintenancePriority.MEDIUM,
            status = MaintenanceStatus.OPEN,
            reportedDate = "10 Sep 2026",
            tenantName = "Emma Watson"
        )
    )

    // --- US DEMO DATA ---
    val usProperties = listOf(
        Property(
            id = "prop-us-1",
            name = "Hudson Skyline Lofts",
            address = "420 W 42nd St, Hell's Kitchen",
            city = "New York",
            state = "NY",
            countryCode = "US",
            type = PropertyType.APARTMENT,
            totalUnits = 12,
            occupiedUnits = 11,
            monthlyRentalIncome = 42_000L,
            status = PropertyStatus.ACTIVE,
            imageResId = R.drawable.img_property_lekki,
            description = "Luxury Manhattan high-rise with rooftop pool, doorman, and city views.",
            totalValuation = 14_000_000L
        ),
        Property(
            id = "prop-us-2",
            name = "Austin Tech Residences",
            address = "1100 S Congress Ave",
            city = "Austin",
            state = "TX",
            countryCode = "US",
            type = PropertyType.APARTMENT,
            totalUnits = 8,
            occupiedUnits = 8,
            monthlyRentalIncome = 24_000L,
            status = PropertyStatus.FULL,
            imageResId = R.drawable.img_property_ikeja,
            description = "South Congress smart-home lofts with EV charging and coworking lounge.",
            totalValuation = 6_500_000L
        )
    )

    val usTenants = listOf(
        Tenant(
            id = "t-us-1",
            name = "Alexander Reed",
            phone = "+1 (212) 555-0198",
            email = "alex.reed@example.com",
            propertyId = "prop-us-1",
            propertyName = "Hudson Skyline Lofts",
            unitNumber = "Unit 14B",
            monthlyRent = 3_800L,
            leaseStart = "01 Jan 2026",
            leaseEnd = "31 Dec 2026",
            paymentStatus = PaymentStatus.PAID,
            deposit = 3_800L,
            paymentDueDate = "1st of every month"
        )
    )

    val usPayments = listOf(
        Payment(
            id = "pay-us-1",
            tenantName = "Alexander Reed",
            propertyName = "Hudson Skyline Lofts",
            unitNumber = "Unit 14B",
            amount = 3_800L,
            date = "Today, 11:20 AM",
            paymentMethod = PaymentMethod.BANK_TRANSFER,
            reference = "ACH-US-4432",
            status = PaymentStatus.PAID,
            notes = "ACH transfer Chase Bank."
        )
    )

    val usMaintenance = listOf(
        MaintenanceRequest(
            id = "maint-us-1",
            title = "Smart thermostat calibration",
            propertyName = "Hudson Skyline Lofts",
            unitNumber = "Unit 14B",
            description = "Nest thermostat losing Wi-Fi sync.",
            priority = MaintenancePriority.LOW,
            status = MaintenanceStatus.OPEN,
            reportedDate = "10 Sep 2026",
            tenantName = "Alexander Reed"
        )
    )

    // --- CANADA DEMO DATA ---
    val canadaProperties = listOf(
        Property(
            id = "prop-ca-1",
            name = "Yorkville Maple Suites",
            address = "88 Bloor St W",
            city = "Toronto",
            state = "ON",
            countryCode = "CA",
            type = PropertyType.APARTMENT,
            totalUnits = 10,
            occupiedUnits = 9,
            monthlyRentalIncome = 28_000L,
            status = PropertyStatus.ACTIVE,
            imageResId = R.drawable.img_property_lekki,
            description = "Upscale downtown condominiums with subterranean transit access and concierge.",
            totalValuation = 9_800_000L
        )
    )

    val canadaTenants = listOf(
        Tenant(
            id = "t-ca-1",
            name = "Chloe Tremblay",
            phone = "+1 (416) 555-0144",
            email = "chloe.t@example.ca",
            propertyId = "prop-ca-1",
            propertyName = "Yorkville Maple Suites",
            unitNumber = "Apt 502",
            monthlyRent = 2_950L,
            leaseStart = "01 Feb 2026",
            leaseEnd = "31 Jan 2027",
            paymentStatus = PaymentStatus.PAID,
            deposit = 2_950L,
            paymentDueDate = "1st of every month"
        )
    )

    val canadaPayments = listOf(
        Payment(
            id = "pay-ca-1",
            tenantName = "Chloe Tremblay",
            propertyName = "Yorkville Maple Suites",
            unitNumber = "Apt 502",
            amount = 2_950L,
            date = "Today, 08:30 AM",
            paymentMethod = PaymentMethod.BANK_TRANSFER,
            reference = "INT-CA-9921",
            status = PaymentStatus.PAID,
            notes = "Interac e-Transfer RBC."
        )
    )

    val canadaMaintenance = listOf(
        MaintenanceRequest(
            id = "maint-ca-1",
            title = "Balcony door weatherstripping",
            propertyName = "Yorkville Maple Suites",
            unitNumber = "Apt 502",
            description = "Cold draft coming through sliding balcony seal.",
            priority = MaintenancePriority.MEDIUM,
            status = MaintenanceStatus.IN_PROGRESS,
            reportedDate = "09 Sep 2026",
            tenantName = "Chloe Tremblay"
        )
    )

    // --- GHANA DEMO DATA ---
    val ghanaProperties = listOf(
        Property(
            id = "prop-gh-1",
            name = "Airport Residential Enclave",
            address = "15 Senchi St, Airport Residential Area",
            city = "Accra",
            state = "Greater Accra",
            countryCode = "GH",
            type = PropertyType.DUPLEX,
            totalUnits = 6,
            occupiedUnits = 6,
            monthlyRentalIncome = 78_000L,
            status = PropertyStatus.FULL,
            imageResId = R.drawable.img_property_abuja,
            description = "Diplomatic zone detached luxury homes with backup power, security, and pool.",
            totalValuation = 18_000_000L
        )
    )

    val ghanaTenants = listOf(
        Tenant(
            id = "t-gh-1",
            name = "Kwame Mensah",
            phone = "+233 24 123 4567",
            email = "kwame.mensah@example.gh",
            propertyId = "prop-gh-1",
            propertyName = "Airport Residential Enclave",
            unitNumber = "Villa 2",
            monthlyRent = 13_000L,
            leaseStart = "01 Jan 2026",
            leaseEnd = "31 Dec 2026",
            paymentStatus = PaymentStatus.PAID,
            deposit = 26_000L,
            paymentDueDate = "1st of every month"
        )
    )

    val ghanaPayments = listOf(
        Payment(
            id = "pay-gh-1",
            tenantName = "Kwame Mensah",
            propertyName = "Airport Residential Enclave",
            unitNumber = "Villa 2",
            amount = 13_000L,
            date = "Today, 10:00 AM",
            paymentMethod = PaymentMethod.BANK_TRANSFER,
            reference = "GH-MOMO-8821",
            status = PaymentStatus.PAID,
            notes = "MTN Mobile Money / Stanbic bank transfer."
        )
    )

    val ghanaMaintenance = listOf(
        MaintenanceRequest(
            id = "maint-gh-1",
            title = "Inverter backup battery check",
            propertyName = "Airport Residential Enclave",
            unitNumber = "Villa 2",
            description = "Solar hybrid inverter requiring quarterly battery cell inspection.",
            priority = MaintenancePriority.MEDIUM,
            status = MaintenanceStatus.OPEN,
            reportedDate = "10 Sep 2026",
            tenantName = "Kwame Mensah"
        )
    )
}
