# SC2002 Turn-Based Combat Arena

## Final Report

---

## 1. Introduction

This project implements a turn-based combat arena system where a player-controlled character engages in battles against AI-controlled enemies. The system supports multiple gameplay mechanics such as attacks, defensive actions, item usage, and special skills with cooldowns.

The design emphasizes modularity, extensibility, and maintainability through the use of object-oriented programming principles and clear separation of responsibilities between components.

---

## 2. System Overview

The system architecture follows a **Boundary–Control–Entity (BCE)** design.

### Boundary

* **BattleUI**: Handles all user interactions, including input and output.

### Control

* **GameController**: Manages overall game flow and initialization
* **BattleEngine**: Controls the battle loop and state updates
* **TurnManager**: Determines turn order and processes turns
* **ActionResolver**: Executes actions and applies effects

### Entity

* **Combatant (abstract)**: Base class for all characters
* **Player / Enemy**: Derived classes representing game actors
* **Item / Skill / Effect**: Represent gameplay mechanics

This separation ensures low coupling and high cohesion across the system.

---

## 3. UML Diagrams

### 3.1 Class Diagram

The class diagram illustrates the static structure of the system, including:

* Relationships between entity, control, and boundary classes
* Inheritance hierarchy (Combatant → Player/Enemy)
* Associations between components

Object-oriented principles such as encapsulation, inheritance, and polymorphism are highlighted within the diagram.



---

### 3.2 Sequence Diagram

The sequence diagram illustrates runtime interactions between system components.

It captures:

* Game initialization and setup
* Turn-based battle loop
* Player and enemy actions
* Action resolution and state updates
* Backup enemy spawning
* Battle termination

Interaction fragments used:

* **loop** → for rounds and turns
* **alt** → for conditional flows (player vs enemy, action types)
* **break** → for battle termination



---

## 4. Additional Features Implemented

The system includes several additional functionalities beyond basic combat:

* Special skill system with cooldown tracking
* Status effects (Stun, Smoke Bomb invulnerability)
* Item system (Potion, Power Stone, Smoke Bomb)
* Backup enemy spawning system
* Multiple difficulty levels with varying enemy configurations

These features enhance gameplay depth and demonstrate extensibility.

---

## 5. Design Considerations

### 5.1 Object-Oriented Principles

* **Encapsulation**
  The Combatant class encapsulates attributes such as HP, attack, defense, and status effects, ensuring controlled access.

* **Inheritance**
  Player and Enemy classes inherit from Combatant, allowing reuse of shared logic.

* **Polymorphism**
  Different actions (attack, item, skill) are handled uniformly through ActionResolver.

* **Abstraction**
  Complex logic is separated into components such as BattleEngine and TurnManager, simplifying system design.

---

### 5.2 Application of OO Principles in Implementation

Encapsulation is applied in the Combatant class where attributes such as HP and effects are managed internally.

Inheritance is used in the Combatant hierarchy, allowing Player and Enemy to extend shared functionality.

Polymorphism is implemented in the action handling system, where different action types are processed through a common interface in ActionResolver.

This ensures flexibility and ease of extension.

---

### 5.3 Coupling and Cohesion

* **Low Coupling**:
  UI, engine, and logic components operate independently, reducing dependencies.

* **High Cohesion**:
  Each class has a clearly defined responsibility, improving readability and maintainability.

---

### 5.4 Design Patterns and Trade-offs

The system adopts a controller-based design:

* **GameController** acts as the main coordinator
* **ActionResolver** centralizes action execution (similar to command pattern)

Alternative designs considered included distributing logic across multiple classes. However, this would increase coupling and complexity.

Trade-offs:

* Centralized logic simplifies debugging but increases reliance on ActionResolver
* Console UI simplifies implementation but reduces user experience

The chosen design balances simplicity and extensibility.

---

### 5.5 Extensibility and Maintainability

The system allows:

* Easy addition of new enemies
* Introduction of new items and skills
* Extension of combat mechanics without modifying core components

This demonstrates strong extensibility and maintainability.

---

## 6. Testing Strategy

Edge case testing was conducted to validate system robustness:

* **HP Boundary Test**
  Ensures HP does not fall below zero

* **Potion Cap Test**
  Ensures healing does not exceed maximum HP

* **Stun Effect Test**
  Verifies correct duration of status effects

* **Smoke Bomb Test**
  Confirms invulnerability and correct duration

* **Cooldown Test**
  Ensures cooldown decreases only during appropriate turns

* **Backup Spawn Test**
  Validates spawning logic based on difficulty and conditions

All test cases are documented in:
`docs/testing/testing-checklist.txt`

---

## 7. Reflection

Throughout the development of this project, several challenges were encountered.

One major difficulty was designing a clear interaction flow between components such as BattleEngine, TurnManager, and ActionResolver. Initially, responsibilities overlapped, leading to confusion. This was resolved by restructuring the system and enforcing separation of concerns.

Another challenge was handling edge cases such as status effects, cooldown logic, and backup spawning. These required careful testing and debugging to ensure correctness.

From this project, we gained a deeper understanding of object-oriented design principles, particularly modularity, abstraction, and separation of responsibilities. We also learned how UML diagrams can be used to effectively represent system design.

Overall, this project reinforced the importance of structured design and iterative testing in building reliable systems.

---

## 8. Limitations

* Console-based interface limits user experience
* Enemy AI uses simple decision-making logic
* No persistence (game state is not saved)
* Limited variety of enemies and gameplay features

---

## 9. Future Improvements

* Implement graphical user interface (GUI)
* Improve enemy AI with advanced strategies
* Add more characters, items, and skills
* Introduce save/load functionality
* Improve game balancing

---




