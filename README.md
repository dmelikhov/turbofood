# Turbo Food

A scalable and reliable platform that allows users to order food from various restaurants
and have it delivered to their doorstep.

The application consists of multiple microservices that handle different aspects of the system,
ensuring modularity and ease of maintenance.
Key features include restaurant and menu management, order processing, payment handling,
delivery logistics.

---

### **[Restaurant Service](restaurant-service/)**
Manages restaurant information and menu items.

### **[Order Service](order-service/)**
Manages the creation and tracking of orders.

### **[Payment Service](payment-mock/)**
Manages payment processing.

### **[Delivery Service](delivery-service/)**
Manages delivery logistics.

---

To run the entire application stack locally, you can use Docker Compose. Ensure you have
Docker and Docker Compose installed on your machine. Then, run the following command
from the root directory of the project:
```
docker compose up
```
