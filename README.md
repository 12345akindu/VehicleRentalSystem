# VehicleRentalSystem
Java CLI-based Vehicle Rental Management System demonstrating OOP principles (abstraction, inheritance, polymorphism), with add/view/rent/return/search features, validation, and total rental income tracking.
# Vehicle Rental Management System (CLI) - Java

A command-line based Vehicle Rental Management System built using Java to demonstrate core Object-Oriented Programming concepts such as **abstraction**, **inheritance**, **polymorphism**, and **encapsulation**.

## Features
- Add vehicles (supports multiple vehicle types)
- View all vehicles
- Rent a vehicle (prevents renting if already rented)
- Return a vehicle
- Search vehicle by ID (IDs are unique)
- Track and display **total rental income**
- Input validation & exception handling (try-catch for invalid numeric inputs)

## OOP Concepts Demonstrated
- **Abstract Class**: `Vehicle`
- **Inheritance**: `Car`, `Bike`, `Van` extend `Vehicle`
- **Polymorphism**: Uses `Vehicle` references to call overridden methods at runtime
- **Encapsulation**: Private fields with getters/setters

## Rental Cost Rules
- **Car**: `baseRatePerDay * days + (numberOfSeats * 200 * days)`
- **Bike**: `baseRatePerDay * days + (engineCapacityCC * 0.5 * days)`
- **Van**: `baseRatePerDay * days + (cargoCapacityKg * 0.2 * days)`

## Requirements
- Java JDK (recommended: JDK 11 or newer)
- IntelliJ IDEA (optional, but recommended)

## How to Run (Command Line)
1. Open terminal inside the project folder.
2. Compile:
   ```bash
   javac -d out src/*.java
