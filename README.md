# Event Management System (Movie Booking System)

## Problem Statement

The **Movie Booking System** is a web-based application designed to simplify the process of booking movie tickets online. The platform enables users to:

- Browse upcoming movie events
- Check real-time seat availability
- Reserve seats and order food combos
- Make payments through multiple gateways
- Access their booking history and cancel bookings if needed

This system ensures a **seamless and engaging user experience**, eliminating the need to stand in queues at theaters or event counters.

---

## Key Features

- **User Authentication:** Secure login/signup with role-based access (Admin/User).
- **Event Catalog:** Dynamic listing of movies and events with details like date, time, and price.
- **Real-Time Seat Selection:** Interactive seat map showing real-time availability.
- **Food Combo Ordering:** Option to pre-order snacks and combo offers during booking.
- **Payment Integration:** Supports multiple gateways like CARD, UPI, and CASH.
- **Booking History:** Users can view and manage their past bookings.
- **Invoice Generation:** Users can view and download their invoice with booking,food order and billing information.
- **MVC Architecture:** Implements the **Model-View-Controller (MVC)** pattern to ensure separation of concerns and modular development.

---

## Design Patterns Used

The Event Management System incorporates **four key design patterns** to improve scalability, maintainability, and flexibility:

### Builder Pattern
Used for the **construction of complex Event objects**. It provides a fluent interface to set various properties while ensuring immutability and validation. This makes it easier to create diverse events with optional fields.

### Chain of Responsibility Pattern
Implemented in the **payment processing pipeline**. Validation steps like checking transaction amount, card/UPI verification, and fraud detection are handled in a decoupled, sequential chain of handlers for flexibility and reusability.

### Command Pattern
Used for managing **booking actions** such as creation and cancellation. These actions are encapsulated as objects to support **undo/redo functionality** and decouple UI logic from backend operations.

### Prototype Pattern
Optimizes **food combo ordering** by enabling cloning of frequently ordered or predefined combo templates. This reduces database calls and enhances performance, especially under heavy load.

---

## Project Structure (MVC)

