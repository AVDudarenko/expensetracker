# Expense Tracker

A Spring Boot REST API for tracking personal expenses with user registration, JWT-based authentication, role-based access control, and CRUD operations for managing expenses.

## 📌 Features

- User registration and login with email and password
- JWT authentication
- Role-based access (USER / ADMIN)
- Expense management:
  - `POST /api/expenses` — create a new expense
  - `GET /api/expenses/mine` — get current user's expenses
  - `GET /api/admin/expenses` — get all expenses (ADMIN only)

## 🧱 Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Security + JWT
- PostgreSQL
- Maven
- Docker
- Hibernate / JPA

## 📁 Project Structure

- config/ Security configuration
- controller/ REST controllers
- dto/ Request and response DTOs
- exception/ Custom exceptions
- model/ JPA entities
- repository/ Spring Data JPA repositories
- security/ JWT utilities and filters
- service/ business logic
- ExpenseTrackerApplication.java

## 🔐 Authentication
After registering and logging in via /api/auth/register and /api/auth/login:

- A JWT token will be returned in the AuthResponse

- Include the token in the Authorization header:
  - `Authorization: Bearer <your_token>`

## 🧑‍💻 Roles & Access
- Endpoint	Role	Description
- POST /api/expenses	USER, ADMIN	Create a new expense
- GET /api/expenses/mine	USER, ADMIN	Get your own expenses
- GET /api/admin/expenses	ADMIN	Get all expenses (admin only)

## 📌 Planned Features
- Expense categories

- Filtering by date and amount

- Docker support

- Email verification during registration

- RabbitMQ for sending notifications

## 🧑‍💼 Author
Aliaksandr Dudarenka
