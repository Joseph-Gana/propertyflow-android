# PropertyFlow

### Smart Property Management, Simplified.

PropertyFlow is a modern Android property management application designed to simplify the way property owners and managers manage properties, tenants, payments, maintenance, and reporting from one centralized platform.

The application is designed with a Nigeria-first approach while supporting multiple regional markets and currencies.

## Overview

PropertyFlow provides a clean and intuitive mobile experience for managing day-to-day property operations.

The current version is a functional prototype demonstrating the core product experience and application architecture.

## Key Features

- Property management
- Property details and portfolio overview
- Tenant management
- Tenant profiles and records
- Payment recording and tracking
- Maintenance request management
- Maintenance status tracking
- Reports and property insights
- Notifications
- Search and filtering
- Regional market and currency settings
- User profile and application settings
- Responsive mobile interface
- Offline-ready prototype experience

## Supported Markets

The prototype includes support for:

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
│       │           ├── ui/
│       │           └── MainActivity.kt
│       └── res/
├── build.gradle.kts
└── proguard-rules.pro

gradle/
build.gradle.kts
settings.gradle.kts
gradle.properties

Prototype Scope

This version uses local/mock data to demonstrate the application's functionality and user experience.

No external backend or production payment infrastructure is required to run the current prototype.

The architecture is structured to allow future integration with backend services, authentication, databases, payment providers, notifications, and other production services.

Getting Started
Requirements
Android Studio
Android SDK
JDK compatible with the project
Android device or emulator
Installation
Clone the repository.
Open the project in Android Studio.
Allow Gradle to synchronize and download the required dependencies.
Select an Android emulator or connected Android device.
Build and run the application.
Development

The project is structured using Kotlin and Jetpack Compose with separate layers for:

Data models
Repository and data handling
UI components
Application screens
Navigation
ViewModel and application state
Theme and styling

This structure provides a foundation for extending the prototype into a production-ready application.

About

PropertyFlow

Smart Property Management, Simplified.

Designed and developed by Joseph Gana.

Version: 1.0.0 — Prototype

© 2026 Joseph Gana. PropertyFlow.
