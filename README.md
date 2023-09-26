# Ride-Sharing Application Readme

## Overview

Welcome to the README for our ride-sharing application, a high-level overview of our system architecture and communication flow between various components. This document provides insights into the architecture, modules, and the flow of data within our ride-sharing service.

## System Architecture

### Microservices Architecture

Our ride-sharing service is designed using a microservices architecture, which offers several advantages, including scalability, maintainability, and fault isolation. Each microservice is a self-contained unit with its own codebase, data storage, and dependencies.

Key benefits of the microservices architecture include:

- **Modularity:** Each microservice handles a specific business logic, making it easier to develop, test, deploy, and scale independently.

- **Fault Isolation:** Failure in one microservice does not impact the entire application, enhancing resilience and making issue identification and fixing more manageable.

## Application Layers

### 1. User Interface (UI)

At the topmost layer, we have the User Interface (UI). To ensure accessibility, we offer both a mobile application and a web application for users to interact with the service.

### 2. Routing Layer

In the second layer, we have the routing layer, responsible for managing communication between the UI and microservices. This layer includes:

- **API Gateway:** Ensures a single entry point to the application, bundled with security and load balancing features.

- **Service Discovery:** Facilitates service orchestration by registering microservice instances, allowing the load balancer to distribute requests evenly.

### 3. Microservices Layer

The third layer consists of microservices, each responsible for specific business logic within the application.

### 4. Persistence Layer

The fourth layer houses data persistence. Most microservices have their databases, except for the Notification service, which does not store data.

## Communication Flow

### UI Interaction

The communication flow between the UI, API Gateway, and microservices is as follows:

1. Users can access the service via web or mobile applications to sign up or log in.

2. The UI communicates with the API Gateway, which verifies user authentication and authorization through the security module.

3. Once validated, the API Gateway calls the Service Discovery to obtain the address of a Customer service instance.

4. All interactions between customers and the application are routed to the Customer service.

### Driver Interaction

For drivers using the mobile application, the flow is as follows:

1. Drivers change their availability status and allow location tracking via the mobile app.

2. The app sends a heartbeat to the Drivers service every minute to maintain driver availability.

3. The heartbeat includes trip status and current location information.

4. The Distribution service maintains in-memory data using a Quadtree data structure, allowing efficient retrieval of the nearest available drivers.

### Ride Request

When a customer initiates a ride request:

1. The Customer service validates the request and the customer's history.

2. Upon confirmation, the Customer service calls the Trip service to initiate a trip.

3. The Trip service contacts the Distribution service to find a matching driver.

4. The Notification service alerts the driver to accept or reject the request. If accepted, the driver's ID is sent back to the Trip service.

5. The Notification service informs the customer of the accepted request.

### Analytics Service

Lastly, we have an Analytics service that consumes logs from all services, providing technical, functional, and business analyses.

## Implementation

For details on the actual implementation of our ride-sharing service, please refer to our codebase and documentation.

Thank you for choosing our ride-sharing service. We hope this README provides a clear understanding of our system's architecture and functionality. If you have any questions or require further information, please don't hesitate to contact us.

## Installation 

To install and run the application locally make sure you have the following software installed on your system:
- Node.js and npm: Ensure you have Node.js and npm (Node Package Manager) installed. You can download them from the official website: https://nodejs.org/en/download
- JDK (Java Development Kit): Make sure you have JDK version 8 or higher. Download it from https://jdk.java.net/21/

Clone the repository to your local machine:
```
  git clone https://github.com/faeezumar/Ride-Sharing-Application.git
  cd Ride-Sharing-Application
```
 

To run the frontend of the application, navigate to the 'frontend' folder:
```
  cd frontend
  npm install
  ng serve
```

To run the backend services, navigate to the respective folder for each microservice within the project directory and run 
```
  mvn spring-boot:run
```
  

