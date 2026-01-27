# 🚀 Distributed Payment Processing System  
### Secure • Scalable • Microservices • Java Spring Boot • AWS

A **production-grade distributed payment processing system** built using **Java 17, Spring Boot, Microservices, Redis, and AWS**, designed to handle **secure, real-time payment validation and processing**.

The system integrates with an external payment provider (**Trustly**) and follows **industry-standard security practices** such as **HmacSHA256 signing, RSA encryption, JWT authentication**, and **AWS-managed secrets**.

---

## 📌 1. System Architecture
<p align="center">
  <img src="https://github.com/user-attachments/assets/4c41138d-2774-4ff2-a42c-ca8946cefdb1"
       alt="System Architecture Diagram"
       width="75%">
</p>


---

## 📌 2. Key Features

### 🔐 Security & Compliance
- HmacSHA256 request signing for integrity validation  
- RSA encryption/decryption for provider communication   
- Centralized validation & exception handling  
- No sensitive data exposure in logs or errors  

---

### ⚙️ Scalable Microservices Architecture
- Independently deployable services:
  - **Validation Service**
  - **Processing Service**
  - **Trustly Provider Service**
- Stateless REST APIs (horizontal scaling ready)
- AWS Load Balancer for traffic distribution
- Auto Scaling Groups for high-traffic scenarios
- Clean API contracts for loose coupling

---

### 💳 Payment Processing Capabilities
- Trustly Deposit API integration
- End-to-end payment lifecycle tracking
- Idempotency handling for duplicate requests
- Custom business rule engine

---

### 🧩 Java & Spring Boot Engineering
- Spring Boot RESTful services
- Spring Security integration
- Spring JDBC for MySQL persistence
- Global exception handling using `@ControllerAdvice`
- Centralized configuration via `@ConfigurationProperties`
- Unit testing with **JUnit**

---

### ☁️ AWS Infrastructure
- AWS EC2 (microservices deployment)
- AWS RDS (MySQL)
- AWS Secrets Manager (keys & credentials)
- Elastic Load Balancer
- IAM roles with least-privilege access

---

## 📌 3. Microservices Breakdown

### 1️⃣ Validation Service
- Validates request schema and mandatory fields
- Modular validation framework (Factory + Builder patterns)
- Ensures request integrity before processing

---

### 2️⃣ Processing Service
- Core payment orchestration logic
- Idempotency control for duplicate transactions
- Transaction state management:
  `PENDING → PROCESSING → SUCCESS / FAILED`

---

### 3️⃣ Trustly Provider Service
- External Trustly API communication
- RSA encryption & HmacSHA256 signing
- Provider-specific orchestration logic
- Fallback and retry handling for failures

---

## 📌 4. API Flow
<p align="center">
  <img src="https://github.com/user-attachments/assets/b78de9b5-30d6-4a88-9e0b-c83f64c1b50c"
       alt="API Flow Diagram"
       width="75%">
</p>

---

## 📌 5. Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Security
- Spring JDBC
- Spring Data
- Redis
- JUnit

### Architecture
- Microservices
- REST APIs
- Factory & Builder Design Patterns

### Security
- JWT Authentication
- RSA Encryption
- HmacSHA256 Signing
- HTTPS
- AWS Secrets Manager

### Infrastructure
- AWS EC2
- AWS RDS (MySQL)
- AWS Load Balancer
- Docker

---

## 📌 6. How to Run Locally

### 1️⃣ Clone Repository

git clone https://github.com/VaibhavChougule236/Core-Payment-Integration-System-Microservices-Based-Backend-Project.git
cd Core-Payment-Integration-System-Microservices-Based-Backend-Project



### **2. Run each microservice**

cd validation-service  
mvn spring-boot:run  

cd processing-service  
mvn spring-boot:run

cd trustly-provider-service  
mvn spring-boot:run


### **3. Test using Postman**

POST /payment/validate  
POST /payment/process  
GET  /payment/status/{id}  


---

## 📬 Contact

If you have any questions, suggestions, or would like to collaborate, feel free to reach out:

**👤 Vaibhav Chougule**  
📧 Email: *vaibhavchougule236@gmail.com*  
🔗 LinkedIn: https://www.linkedin.com/in/vaibhavchougule124  
🐙 GitHub: https://github.com/VaibhavChougule236  

---

## 🏷️ Created By

This project was designed and developed by **Vaibhav Chougule** as part of a microservices-based distributed payment processing system focusing on scalability, security, and real-time financial transaction handling.

© 2025 Vaibhav Chougule — All Rights Reserved.




