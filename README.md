# Dollar Book Shop

A desktop e-commerce bookstore application built with Java and JavaFX, implementing MVC architecture and OOP principles for complete inventory and transaction management.

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-007396?style=flat&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green.svg)

## 📋 Overview

Dollar Book Shop is a complete book store management system featuring role-based access for administrators and customers. Admins can manage book inventory while users can browse, add items to cart, and complete purchases with automatic stock management.

## 📥 Download

Clone this repository or download the latest release:
``` bash
git clone https://github.com/ItsSalims/DollarBookShop.git
```

## 🎯 Key Features

### Admin Features
- **Product Management** - Full CRUD operations for book inventory
- **Stock Control** - Real-time inventory adjustment
- **Price Management** - Dynamic pricing updates
- **Automatic ID Generation** - Sequential ID system (PD001, PD002, etc.)

### User Features
- **Browse Catalog** - View all available books with complete details
- **Shopping Cart** - Add books with customizable quantities
- **Cart Management** - Update or remove items before checkout
- **Checkout Process** - Secure purchase with automatic stock synchronization
- **Price Calculation** - Real-time total price computation

### Authentication System
- **Secure Login** - Email and password validation
- **Registration** - Multi-layer validation including:
  - Username length validation (> 5 characters)
  - Email uniqueness and format validation
  - Alphanumeric password requirement (≥ 8 characters)
  - Password confirmation matching
  - Age verification (≥ 18 years old)
- **Session Management** - Persistent user state tracking
- **Role-Based Access** - Automatic routing based on user privileges

## 🏗️ Architecture

### MVC Pattern Implementation

```
┌─────────────┐         ┌──────────────┐         ┌─────────────┐
│    Model    │ ◄────── │  Controller  │ ──────► │    View     │
│             │         │              │         │             │
│  - Users    │         │  - Login     │         │  - Login    │
│  - Products │         │  - Register  │         │  - Register │
│  - Carts    │         │  - Admin     │         │  - Admin    │
│  - Session  │         │  - User      │         │  - User     │
│             │         │  - Cart      │         │  - Cart     │
└─────────────┘         └──────────────┘         └─────────────┘
       │                       │                        │
       └───────────────────────┴────────────────────────┘
                         Database Layer
```

### OOP Principles Demonstrated
- **Encapsulation** - Private attributes with public accessor methods
- **Abstraction** - Controller layer abstracts database operations
- **Inheritance** - Extensible class structure for future enhancements
- **Polymorphism** - Event-driven behavior across different views

### Design Patterns
- **Singleton Pattern** - Database connection and session management
- **MVC Pattern** - Clear separation of concerns

## 💾 Database Structure

The application uses **MySQL** with 5 normalized tables:

| Table | Purpose | Primary Key |
|-------|---------|-------------|
| `users` | User accounts with role-based access | UserID (US001, US002, ...) |
| `products` | Book catalog with inventory | ProductID (PD001, PD002, ...) |
| `carts` | Temporary shopping cart | Composite (UserID, ProductID) |
| `transaction_headers` | Transaction metadata | TransactionID (TR001, TR002, ...) |
| `transaction_details` | Transaction line items | Composite (TransactionID, ProductID) |

## 🚀 Installation & Setup

### Prerequisites
- Java JDK 8 or higher
- MySQL Server 5.7+
- JavaFX SDK (if not bundled with JDK)
- IDE (IntelliJ IDEA, Eclipse, or NetBeans)
- MySQL Connector/J

### Setup Steps

1. **Database Configuration**
``` sql
CREATE DATABASE dollarbookshop;
USE dollarbookshop;
SOURCE path/to/dollarbookshop.sql;
```

2. **Configure Database Connection**

Open `src/Util/Connect.java` and update if needed:
``` java
private final String USER = "root";
private final String PASS = "";
private static final String CONNECTION = "jdbc:mysql://localhost:3306/dollarbookshop";
```

3. **Build & Run**
``` bash
# Using IDE: Run Main.java
# Or compile manually:
javac -d bin src/**/*.java
java -cp bin Main.Main
```

4. **Default Login Credentials**
- **Admin**: `admin@gmail.com` / `admin1234`
- **User**: `david@gmail.com` / `david1234`

## 📁 Project Structure

```
src/
├── Main/
│   └── Main.java                 # Application entry point
├── Controller/
│   ├── LoginController.java      # Authentication logic
│   ├── RegisterController.java   # Registration with validation
│   ├── AdminController.java      # Product CRUD operations
│   ├── UserController.java       # User shopping operations
│   └── CartController.java       # Cart & checkout management
├── Model/
│   ├── Users.java               # User entity
│   ├── Products.java            # Product entity
│   ├── Carts.java              # Cart item entity
│   └── Session.java            # Session management (Singleton)
├── View/
│   ├── LoginView.java          # Login interface
│   ├── RegisterView.java       # Registration interface
│   ├── AdminView.java          # Admin dashboard
│   ├── UserView.java           # Product catalog
│   └── CartView.java           # Shopping cart interface
└── Util/
    ├── Connect.java            # Database connection (Singleton)
    └── ShowError.java          # Alert utilities
```

## 🔒 Security Features

- **SQL Injection Prevention** - All queries use `PreparedStatement`
- **Input Validation** - Comprehensive server-side validation
- **Session Management** - Secure login/logout flow
- **Age Verification** - Enforces minimum age requirement

> **⚠️ Educational Note**: This application stores passwords in plain text for learning purposes. Production applications must use secure hashing algorithms (BCrypt, Argon2, PBKDF2).

## 🎓 Learning Objectives

This project demonstrates:
- Desktop application development with JavaFX
- MVC architectural pattern implementation
- Object-oriented programming principles
- Relational database design and normalization
- JDBC and SQL operations with PreparedStatement
- Event-driven programming
- User input validation and error handling
- Session and state management
- Software design patterns (Singleton)

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2024 [Eric Tantoso Salim]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## 📧 Contact

Eric Tantoso Salim - [@salims05](https://instagram.com/salims05) - erictantoso84@gmail.com

Project Link: [https://github.com/ItsSalims/DollarBookShop](https://github.com/ItsSalims/DollarBookShop)

---

**Note**: This application was developed as a course project to fulfill the requirements of one of my subjects at Bina Nusantara University (BINUS), focusing on object-oriented programming and database integration. In addition, I implemented the MVC architecture on my own initiative, as I was already familiar with the concept and wanted to enhance the project’s structure.
