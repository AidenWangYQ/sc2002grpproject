# SC2002 Object-Oriented Design Project  
## Turn-Based Battle Arena System  

---

## 📌 Overview  

This project implements a modular **turn-based battle arena system** using object-oriented design principles. The system simulates combat between a player-controlled character and AI-driven enemies with configurable behaviours.

The architecture is designed with strong emphasis on:
- **Extensibility** – new actions, effects, and enemy behaviours can be added easily  
- **Maintainability** – clear separation of responsibilities across components  
- **Scalability** – supports future expansion with minimal code changes  

---

## 🎯 Objectives  

- Apply **Object-Oriented Programming (OOP)** concepts in a complete system  
- Design a **flexible and scalable combat engine**  
- Demonstrate usage of **software design patterns**  
- Ensure **high cohesion and low coupling** across modules  

---

## 🧱 Project Structure  

### Packages  

- **action/**  
  Handles all action-related logic (attack, defend, skills, item usage).  
  Each action is encapsulated as a class for modularity.  

- **combatant/**  
  Defines core entities such as `Player`, `Enemy`, and base combatant logic.  

- **common/**  
  Contains shared utilities such as `DamageCalculator` and constants.  

- **controller/**  
  Acts as the intermediary between UI and engine, coordinating interactions.  

- **engine/**  
  Core battle logic including `BattleEngine`, `TurnManager`, and effect processing.  

- **level/**  
  Manages game progression, including levels, waves, and enemy spawning.  

- **strategy/**  
  Implements AI decision-making and turn-order strategies using the Strategy Pattern.  

- **Main.java**  
  Entry point of the application.  

---

## ⚔️ Core Features  

### Turn-Based Combat System  
- Alternating turns between player and enemy  
- Turn order determined using strategy-based logic  

### Action System  
- Supports multiple action types:  
  - Basic Attack  
  - Defend  
  - Skills (e.g., Shield Bash)  
  - Item Usage (e.g., Potion)  
- Each action is encapsulated as a class (**Command Pattern**)  

### Status Effects  
- Includes effects such as:  
  - Defense buffs  
  - Stun effects  
- Effects persist across turns and are centrally managed  

### Enemy AI  
- Enemy decisions handled through strategy classes  
- Enables flexible and extendable AI behaviour  

### Level & Wave System  
- Structured gameplay progression via:  
  - Levels  
  - Enemy waves  
  - Spawn management  

---

## 🧠 Design Overview  

### Separation of Concerns  

- **UI Layer** → Handles user interaction  
- **Controller Layer** → Coordinates system flow  
- **Engine Layer** → Processes battle logic  
- **Domain Layer** → Represents entities (combatants, actions, effects)  

This layered architecture improves readability and maintainability.

---

## 🧩 Design Patterns Used  

### Strategy Pattern  
Used for:
- Enemy action selection  
- Turn order determination  

Allows dynamic swapping of behaviours without modifying core logic.  

---

### Command Pattern (Action-Based Design)  
- Each action is encapsulated as an object  
- Supports easy addition of new actions without modifying existing code  

---

### Manager-Based Design  
Used in:
- Turn management  
- Effect handling  
- Cooldown tracking  

Improves modularity by isolating responsibilities into dedicated components.  

---

## 🔄 System Flow (Simplified)  

1. Player selects an action via the UI  
2. Controller forwards action to the battle engine  
3. Engine processes the action  
4. Damage and effects are applied  
5. Turn transitions to next entity  
6. Loop continues until battle ends  

---

## 🧪 Testing  

Testing focuses on validating both core gameplay mechanics and edge cases, including:

- Damage calculations and defense interactions  
- Status effects (e.g., stun preventing actions)  
- Action execution correctness  
- Item usage and consumption  
- Turn handling and invalid scenarios  

These tests ensure system reliability under different conditions.

---

## 📊 UML & Documentation  

The project includes:
- UML Class Diagram  
- UML Sequence Diagram  
- Supporting documentation  

These artifacts illustrate system structure and runtime interactions.

---

## ▶️ How to Run  

Run the application using:
Main.java

in your IDE.  

This starts the battle simulation via the console interface.

---

## 🚀 Future Improvements  

- Graphical User Interface (GUI) implementation  
- Additional enemy types and behaviours  
- Expanded skill and effect systems  
- More advanced battle mechanics  

---

## 👥 Team Members  

- Aiden Wang Yugi  
- Elliot Heng Zheng Yang  
- Ng Yan En  
- Ramachandran Saichandar  
- Sooraj Senthil  

---
