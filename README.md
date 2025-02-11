# SOA-App

## Customer-Restaurant Application for Service Oriented Architecture Class

This project is a microservice-based system designed to handle various functionalities related to authentication, customer, and restaurant management. The application is fully containerized using Docker, with all its components orchestrated and managed through Docker Compose.

The system integrates several backend services developed in Java Spring, a microfrontend architecture built with Angular and Module Federation (for more details, refer to this repository), and Docker Swarm for load-balancing the API gateway to ensure efficient routing. The application also includes support for serverless functions using AWS Lambda. Message-based communication between microservices is managed through ActiveMQ, and event-streaming is handled using Apache Kafka. The REST API is secured using JWT tokens to ensure safe authentication and authorization.

---

## Architecture Overview

### Backend Services

- **Auth Service:** Manages user authentication and token generation.
- **Customer Service:** Handles customer-related data and operations such as ordering products from different restaurants.
- **Restaurant Service:** Provides restaurant management functionalities, like creating, deleting, updating, and fetching items.
- **Transaction Service:** Handles transactions between customers and restaurants.

### Database Layer

Each microservice has a dedicated PostgreSQL instance for data isolation and integrity.

### Message Brokers and Event Streaming

- **Message Brokering:** Developed using ActiveMQ to facilitate asynchronous communication between the Customer Service and the Restaurant Service, ensuring data synchronization between customer orders and restaurant processing. When a customer places an order, the Customer Service sends a message to the Restaurant Service via ActiveMQ to notify it about the order.

- **Event Streaming:** Built using Apache Kafka to manage data streaming and real-time processing. Kafka enables event-driven communication between the Customer Service and the Transaction Service. When a customer places an order, the Customer Service emits an event via Kafka, which the Transaction Service consumes to store transaction details.

### FaaS (Function as a Service)

A Lambda function (`get-user-discount`) is used to demonstrate serverless computing. This function, built using AWS Lambda, calculates the customer's discount based on their rating and the price of the items they want to order. This approach allows the system to handle user-specific queries without requiring a full-fledged backend service. The function is deployed and managed using the Serverless Framework for efficient deployment to AWS.

### System Architecture

The following diagram illustrates the overall system architecture:

<img width="803" alt="image" src="https://github.com/user-attachments/assets/4ec5d615-6412-474f-8941-4815ffbf5e05" />

---

## Microfrontend Architecture

The front-end of the restaurant management application is built using a microfrontend architecture with Angular. It consists of a host (shell) and two microfrontends (auth and customer) to provide a modular and scalable interface.

The microfrontend architecture is illustrated in the following diagram:

<img width="586" alt="image" src="https://github.com/user-attachments/assets/8fa90e4b-9cc0-4a2c-a46b-0293f444038b" />

## In more detail about the microservices
### Authentication
The Authentication Microservice microservice handles user authentication and registration. It provides secure access management using a repository-based approach.

Key Features
- User Registration: Registers new users and stores credentials securely.
- User Authentication: Validates login credentials and returns authentication tokens.
- Repository-Based User Management: Supports querying users by username and password.
- Encapsulation of User Entities: Ensures modular and maintainable user entity management.

![authentication-diagram](https://github.com/user-attachments/assets/7cf9dddd-4602-43cf-8117-af688862ec68)

### Customer
The Customer Microservice manages customers and their orders. It allows users to register as customers and place orders.

Key Features
- Customer Registration: Associates a user with a customer profile.
- Order Placement: Enables customers to create new orders.
- User-Customer Association: Links customers to their respective user accounts.
- Order Management: Facilitates order creation and tracking.
- Service Layer Abstraction: Uses a service layer to handle business logic separately from controllers.
![customer-diagram](https://github.com/user-attachments/assets/b292e1df-0c89-4527-a2f3-22d80bf97caa)

### Restuarant
This microservice manages restaurants, their menus, and customer orders. It provides endpoints for handling restaurant-related operations.

Key Features
- Restaurant Registration: Enables the creation and management of restaurants.
- Menu Item Management: Allows restaurants to add, update, and remove menu items.
- Order Processing: Supports placing and managing customer orders.
- Integration with Order Queue: Listens for incoming order messages and processes them accordingly.
- Repository-Based Data Access: Efficiently retrieves restaurant and item data from the database.

![restaurant-diagram](https://github.com/user-attachments/assets/3417da85-9cce-441c-a0d0-8fe0a1d298ef)

### Transaction
This microservice handles financial transactions related to customer orders.

Key Features
- Transaction Logging: Records transactions associated with customer orders.
- Repository-Based Storage: Stores and retrieves transaction data efficiently.
- Event-Driven Architecture: Listens for incoming transaction-related events via message queues.
- Customer Transaction Association: Links transactions to customers for financial tracking
![transaction-diagram](https://github.com/user-attachments/assets/b0c2522e-0b3a-41a0-af68-5e1bc019c35d)

---

## Deployment & Setup

### Prerequisites
- Docker & Docker Compose
- Java 17 (for backend services)
- Node.js & Angular CLI (for frontend)
- ActiveMQ & Apache Kafka
- AWS Lambda & Serverless Framework (for FaaS deployment)

### Running the Application

1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/SOA-App.git
   cd SOA-App
   ```

2. Start the microservices and dependencies using Docker Compose:
   ```sh
   docker-compose up --build
   ```

3. Access the frontend at:
   ```sh
   http://localhost:4200
   ```

4. API Gateway runs at:
   ```sh
   http://localhost:8030
   ```
