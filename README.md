# 📦 Order Inventory System (OIS)

An ultra-premium, interactive, and high-performance full-stack enterprise management platform built with **Spring Boot** and **Thymeleaf**. The platform coordinates complex supply chains, managing store profiles, real-time inventory tracking, customer accounts, order lifecycles, and shipments in a highly secure, automated environment.

---

## 🌟 Core Features

- **🌐 Dynamic Premium Dashboard**: An immersive landing page featuring full-width interactive gradients, a cosmic grid texture overlay, real-time system metrics, and unified dark/light themes.
- **👥 Dev Leadership & Domain Owner Cards**: A beautiful interactive developer grid showcasing the project owners. Hovering over a card dynamically shrinks their avatar and reveals direct dashboard shortcuts to their assigned domains.
- **💼 Comprehensive Domain Modules**:
  - 🏪 **Store Module**: Manage store locations, coordinates, physical/web addresses, and logo assets.
  - 👥 **Customer Module**: Orchestrate customer profiles, credit ratings, and relationships.
  - 📦 **Product Module**: Manage catalogs, price tiers, and classifications.
  - 📊 **Inventory Module**: Track real-time warehouse stock levels, pipelines, and replenishment rules.
  - 🛒 **Order & OrderItem Modules**: Seamless checkout, line items tracking, and lifecycle management.
  - 🚚 **Shipment Module**: Automate logistics, coordinates, tracking statuses, and carrier handoffs.
- **🔒 Advanced Enterprise Security**: Role-based access control (RBAC) enforced via **Spring Security** paired with secure, centralized credential management using **HashiCorp Vault**.
- **⚡ Interactive REST API Testing & Documentation**: Fully integrated **OpenAPI 3.0 / Swagger UI** playground inside the browser. Try out live endpoints and view formatted JSON payloads with custom tab controls.

---

## 🛠️ Technology Stack

| Component | Technology | Description |
| :--- | :--- | :--- |
| **Core Framework** | Spring Boot 3.5.0 | High-performance full-stack MVC backend |
| **View Engine** | Thymeleaf 3.0 | Modern server-side HTML template rendering |
| **Database** | MySQL Connector/J | Persistent relational storage engine |
| **ORM / JPA** | Hibernate & Data JPA | Data persistence & transactional mapping |
| **Secret Management**| HashiCorp Vault Config | Secured key/value properties engine |
| **API Docs** | Springdoc OpenAPI UI | Dynamic Swagger interactive API testing |
| **Aesthetics** | Vanilla CSS3 | Custom translucent glassmorphism & responsive neon grids |

---

## 📁 Project Architecture & Assignments

The application distributes core business domains across specialized engineering owners:

- **Kumar Aditya Pratap**
  - `Order`, `OrderItem`, `Shipment`
- **Akshaya Priya D**
  - `Customer`, `Security Configuration`
- **Sneha Angapparaj**
  - `Product`, `Report`
- **Akhil Puri**
  - `Store`, `Inventory`

---

## ⚙️ System Setup & Prerequisites

Ensure you have the following installed on your local machine before starting the installation:

1. **Java Development Kit (JDK)**: Version 17 or higher.
2. **Apache Maven**: Version 3.6 or higher.
3. **MySQL Server**: Running instance (local or hosted).
4. **HashiCorp Vault Server**: Centralized credentials manager.

---

## 🚀 Setup & Installation Steps

### 1. Database Setup
Log into your MySQL server and create the database specified in the configurations:
```sql
CREATE DATABASE order_inventory_db;
```

### 2. HashiCorp Vault Setup
Ensure your local or cloud HashiCorp Vault instance is running and kv secret engine is enabled:
```bash
# Enable KV Secrets Engine (v1 or v2)
vault secrets enable -path=secret kv

# Store project passwords/credentials under context name
vault kv put secret/order-inventory-system Password="your_mysql_password_here"
```

### 3. Environment Variables Configuration
Set the required database and vault authentication tokens in your local terminal or system environment:

**Windows (PowerShell):**
```powershell
$env:Password = "your_mysql_password_here"
$env:VAULT_TOKEN = "your_hashicorp_vault_token_here"
```

**macOS / Linux (Bash/Zsh):**
```bash
export Password="your_mysql_password_here"
export VAULT_TOKEN="your_hashicorp_vault_token_here"
```

---

## 🏃 Running the Application

### 1. Compile & Build the Project
Use the included Maven wrapper script to compile all Java source files and templates:

**Windows:**
```powershell
.\mvnw.cmd compile
```

**macOS / Linux:**
```bash
chmod +x mvnw
./mvnw compile
```

### 2. Start the Application
Launch the Spring Boot development server locally:

**Windows:**
```powershell
.\mvnw.cmd spring-boot:run
```

**macOS / Linux:**
```bash
./mvnw spring-boot:run
```

---

## 🔌 Accessing the System & API Playground

Once the console outputs `Started Application in ... seconds`, navigate to:

- **🌍 Frontend Landing Page**: [http://localhost:8080](http://localhost:8080)
- **📋 Swagger / OpenAPI UI Documentation**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **🏪 Store Operations CRUD Dashboard**: [http://localhost:8080/store-module/stores](http://localhost:8080/store-module/stores)

*Note: Accessing operational modules requires authentication. Log in with your configured spring-security user roles.*
```
```
