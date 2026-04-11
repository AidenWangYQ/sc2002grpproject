# SC2002 Object-Oriented Design Project  
## Turn-Based Battle Arena System

---

## 📌 Overview
This project implements a modular **turn-based battle arena system** using object-oriented design principles. The system simulates combat between a player-controlled character and AI-driven enemies using configurable strategy-based behavior.

The design emphasizes **extensibility, maintainability, and clear separation of concerns**, allowing new features such as actions, effects, or enemy behaviors to be added with minimal changes to existing code.

---

## 🎯 Objectives
- Apply **Object-Oriented Programming (OOP)** principles in a complete system  
- Design a flexible and scalable **combat engine**  
- Demonstrate usage of **software design patterns**  
- Ensure maintainability through **clean architecture and modular design**  

---

## 🧱 Project Structure

### 📦 Packages

- **action/**
  - Contains all action-related classes such as attacks, defense, item usage, and skills.

- **combatant/**
  - Defines core entities like `Player`, `Enemy`, and base combatant logic.

- **common/**
  - Shared utilities and helper classes (e.g., `DamageCalculator`, constants).

- **controller/**
  - Handles user input and coordinates interactions between UI and game logic.

- **engine/**
  - Core battle system logic including `BattleEngine`, `TurnManager`, and effect handling.

- **level/**
  - Manages game progression, including levels, waves, and enemy spawning.

- **strategy/**
  - Implements AI behavior and turn-order strategies.

- **Main.java**
  - Entry point of the application.


---

## ⚙️ Core Features

### 🔹 Turn-Based Combat System
- Alternating turns between player and enemy  
- Turn order determined using strategy-based logic  

### 🔹 Action System
- Supports multiple action types:
  - Basic Attack  
  - Defend  
  - Skill usage (e.g. Shield Bash)  
  - Item usage (e.g. Potion)  
- Each action is encapsulated as a separate class for extensibility  

### 🔹 Status Effects
- Includes effects such as:
  - Defense buffs  
  - Stun effects  
- Effects persist and are managed across turns  

### 🔹 Enemy AI
- Enemy decisions are handled through strategy classes  
- Enables flexible and extendable AI behavior  

### 🔹 Level & Wave System
- Supports structured gameplay progression via:
  - Levels  
  - Enemy waves  
  - Spawn management  

---

## 🧠 Design Overview

### Separation of Concerns
- **UI Layer** → Handles user interaction  
- **Controller Layer** → Coordinates flow between UI and engine  
- **Engine Layer** → Processes battle logic and resolves actions  
- **Domain Layer** → Represents core entities (combatants, actions, effects)  

---

### Design Patterns Used

#### Strategy Pattern
Used for:
- Enemy action selection  
- Turn order determination  

#### Command Pattern (Action-Based Design)
- Each action is encapsulated as an object  
- Enables easy extension of new actions without modifying existing logic  

#### Manager-Based Design
Used in engine components:
- Turn management  
- Effect handling  
- Cooldown tracking  

These components isolate responsibilities and improve maintainability.

---

## 🔄 System Flow (Simplified)

1. Player selects an action via the UI  
2. Controller forwards the action to the battle engine  
3. Engine processes the action  
4. Effects and damage are applied  
5. Turn transitions to the next entity  
6. Loop continues until battle ends  

---

## 🧪 Testing

Testing focuses on validating both core gameplay mechanics and edge cases, including:

- Damage calculations and defense interactions  
- Status effects (e.g. stun preventing actions)  
- Action execution correctness  
- Item usage and consumption  
- Turn handling and invalid scenarios  

These tests ensure the reliability and correctness of the battle system under different conditions.

---

## 📊 UML & Documentation

The project includes supporting design artefacts such as sequence diagrams and structured documentation to illustrate system behavior and architecture.

---

## ▶️ How to Run

Run the application directly using:

- `Main.java` in your IDE  

This will start the battle simulation through the console interface.

---

## 🚀 Future Improvements
- Graphical User Interface (GUI) implementation  
- Additional enemy types and behaviors  
- Expanded skill and effect systems  
- More advanced battle mechanics  

---

## 👥 Team Members
- Aiden Wang Yuqi  
- Elliot Heng Zheng Yang  
- Ng Yan En  
- Ramachandran Saichandar  
- Sooraj Senthil  

---

