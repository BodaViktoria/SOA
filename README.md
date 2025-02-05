SOA-App

Customer-Restaurant Application for Service Oriented Architecture class

Introduction

This project is a microservice-based system designed to handle various functionalities related to authentication, customer and restaurant management. The application is fully containerized using Docker, with all its components orchestrated and managed through Docker Compose.

The system integrates several backend services developed in Java Spring, a microfrontend architecture built with Angular and Module Federation (for more details refer to this repository), and docker swarm used for load-balancing API gateway for efficient routing. The application also contains support for serverless functions using AWS Lambda. Message-based communication between micro-services is managed through ActiveMQ, and event-streaming using Apache Kafka. The REST API is secured using JWT tokens to ensure safe authentication and authorization.

Here is an overview of each main component of the application:

Backend Services

Auth Service: Manages user authentication and token generation.
Customer Service: Handles customer-related data and operations such as ordering products from different Restarants.
Restaurant Service: Provides restaurant management functionalities, like creating, deleting, updating and fetching items.
Transaction Service: Handles the transaction of the customer to the restaurant.

Database Layer

Each microservice has a dedicated PostgreSQL instance for data isolation and integrity.

Message brokers and event streaming

Message brokering is developed using ActiveMQ. This facilitates asynchronous communication between the Customer Service and the Restaurant Service to handle data syncronization between the orders placed by a customer to a restaurant. When a customer wants to order items from a restaurant, the Customer Service sends a message to the Restaurant Service via ActiveMQ to notify the Restaurant Service about the order that has been placed.

Event streaming is built using Apache Kafka to manage data streaming and real-time processing. In this context, Kafka is used for event-driven communication between the Customer Service and the Transaction Service. When a customer attempts to order items from a restaurant, the Customer Service emits an event about the order via Kafka, which the Transaction Service consumes to save the transaction details.

FaaS

A Lambda function (get-user-discount) is used to demonstrate serverless computing. This function is built using AWS Lambda, and is responsible for calculating the customer's discount based on the customer rating and the price of the items the user wants to order. This allows the system to handle user-specific data queries without needing a full-fledged backend service. The function is deployed and managed using the Serverless Framework, for efficient deployment to AWS.

All of the above are illustrated in the following architecture diagram:

---






