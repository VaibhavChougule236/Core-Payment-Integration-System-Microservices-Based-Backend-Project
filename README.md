# 🚀 Distributed Payment Processing System  
### **Secure | Scalable | Microservices | Java Spring Boot | AWS**

A highly secure and scalable **Core Payment Integration System** built using **Java, Spring Boot, Microservices, Redis, AWS**, and strong cryptographic standards (**HmacSHA256, RSA encryption, JWT**).  
Designed for real-time payment validation, processing, external provider integration, and reliable transaction lifecycle management.

---

# 📌 1. **System Architecture**

<img width="1024" height="1536" alt="image" src="https://github.com/user-attachments/assets/4c41138d-2774-4ff2-a42c-ca8946cefdb1" />


> The diagram represents a fully distributed microservices architecture with layered security, caching, AWS infrastructure, and external provider integration.

---

# 📌 2. **Key Features**

### 🔐 **Secure Payment Integration**
- HmacSHA256 hashing for request signing  
- RSA encryption/decryption for Trustly provider API  
- JWT-based service authentication  
- HTTPS enforced for all service communication  
- Secrets stored in **AWS Secrets Manager**  
- Centralized validation framework  
- Global exception handling (avoids sensitive data leaks)

### ⚙️ **Scalable Microservices Architecture**
- 3 independently deployable microservices:
  - **Validation Service**
  - **Processing Service**
  - **Trustly Provider Service**
- Stateless REST APIs (easy horizontal scaling)
- Redis cache for rules, tokens & configuration
- AWS Load Balancer for traffic distribution
- Auto-Scaling Groups for peak load handling
- Loose coupling via clean API contracts

### 💳 **Payment Integration**
- Trustly Deposit API Integration  
- End-to-end payment lifecycle tracking  
- Idempotency support for duplicate request handling  
- Retry mechanism for provider API failures  
- PCI DSS–aligned data confidentiality practices  
- Custom business rule engine  
- Validation of all incoming payment requests  

### 🧩 **Java Spring Boot Engineering**
- Spring Boot REST APIs  
- Spring Security for authentication  
- Spring JDBC for MySQL persistence  
- Redis Caching  
- Global exception handling using `@ControllerAdvice`  
- Configuration management using `@ConfigurationProperties`  
- Unit tests with **JUnit & Mockito**

### ☁️ **AWS Deployment**
- AWS EC2 (service deployment)  
- AWS RDS (MySQL database)  
- AWS Secrets Manager (key/credential storage)  
- AWS S3 (secure configuration storage)  
- AWS CloudWatch (metrics + logs)  
- Elastic Load Balancer  
- IAM roles with least privilege access  

---

# 📌 3. **Microservices Breakdown**

### **1️⃣ Validation Service**
- Validates request format & required fields  
- Uses Modular Validation Framework (Factory + Builder patterns)  
- Loads rules dynamically (Redis-cached)  
- Ensures request integrity & safety  

### **2️⃣ Processing Service**
- Handles core payment workflow  
- Idempotency support for duplicate transactions  
- Status tracking (Pending → Processing → Success/Failed)  
- Retry logic for downstream failures  

### **3️⃣ Trustly Provider Service**
- Communicates with Trustly Deposit API  
- Applies **RSA encryption** & **HmacSHA256** signing  
- Provider-specific payment orchestration  
- Retry + fallback error handling  

---

# 📌 4. **API Flow Diagram**

<img width="1024" height="1536" alt="image" src="https://github.com/user-attachments/assets/b78de9b5-30d6-4a88-9e0b-c83f64c1b50c" />

---

# 📌 6. **Tech Stack**

### **Backend**
- Java 17  
- Spring Boot  
- Spring Security  
- Spring JDBC  
- Spring Data  
- Redis Cache  
- JUnit, Mockito  

### **Architecture**
- Microservices  
- REST APIs  
- Factory & Builder Design Patterns  

### **Security**
- JWT Authentication  
- RSA Encryption  
- HmacSHA256 Signing  
- HTTPS  
- AWS Secrets Manager  

### **Infrastructure**
- AWS EC2  
- AWS RDS (MySQL)  
- AWS S3  
- AWS CloudWatch  
- AWS Load Balancer  
- Docker  

---

# 📌 7. **How to Run Locally**

### **1. Clone repository**

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


