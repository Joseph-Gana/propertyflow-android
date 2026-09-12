# PropertyFlow

### Smart Property Management, Simplified.

PropertyFlow is a modern Android property management application designed to simplify the way property owners and managers manage properties, tenants, payments, maintenance, and reporting from one centralized platform.

The application is designed with a Nigeria-first approach while supporting multiple regional markets and currencies.

## Overview

PropertyFlow provides a clean and intuitive mobile experience for managing day-to-day property operations.

The current version is a functional prototype demonstrating the core product experience, user interface, application functionality, and underlying application structure.

## Key Features

- Property management
- Property portfolio overview
- Property details and records
- Tenant management
- Tenant profiles and records
- Payment recording and tracking
- Maintenance request management
- Maintenance status tracking
- Reports and property insights
- Notifications
- Search and filtering
- Regional market and currency settings
- User profile management
- Application settings
- Responsive mobile interface
- Offline-ready prototype experience

## Supported Markets

The current prototype includes support for the following markets:

- Nigeria — NGN (₦)
- United Kingdom — GBP (£)
- United States — USD ($)
- Canada — CAD ($)
- Ghana — GHS (₵)

## Technology Stack

- Kotlin
- Jetpack Compose
- Material 3
- Android SDK
- Navigation Compose
- ViewModel
- Repository pattern
- Clean architecture principles

## Project Structure

```text
app/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           ├── data/
│       │           │   ├── datasource/
│       │           │   ├── models/
│       │           │   └── repository/
│       │           ├── ui/
│       │           │   ├── components/
│       │           │   ├── navigation/
│       │           │   ├── screens/
│       │           │   ├── theme/
│       │           │   └── viewmodel/
│       │           └── MainActivity.kt
│       └── res/
├── build.gradle.kts
└── proguard-rules.pro

gradle/
build.gradle.kts
settings.gradle.kts
gradle.properties
