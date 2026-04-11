# SC2002 Turn-Based Combat Arena

## 1. Introduction
This project implements a turn-based combat arena game. The system allows a player to battle against enemies using actions, items, and special abilities.

---

## 2. Objectives
- Develop a turn-based combat system
- Apply object-oriented programming concepts
- Build a modular and extensible design
- Ensure clear separation between components

---

## 3. System Overview
The system consists of multiple components working together:
- The user interacts through a command-line interface
- The controller manages game flow
- The battle engine executes combat logic
- Core game entities define actions, effects, and combatants

---

## 4. System Architecture
The system follows a layered structure:

- UI Layer: Handles input and output
- Controller Layer: Manages interaction between UI and engine
- Engine Layer: Executes battle logic
- Domain Layer: Represents combatants, actions, effects, and items

---

## 5. Key Components

### 5.1 Combatants
Represents all entities involved in battle, including player and enemies.

### 5.2 Actions
Defines different actions such as attacking, defending, and using abilities.

### 5.3 Effects
Represents status effects that affect combatants over time.

### 5.4 Items
Handles consumable items used during battle.

### 5.5 Battle Engine
Controls turn order, executes actions, and determines battle outcomes.

---

## 6. Game Flow
The game follows a turn-based structure:
- Player selects an action
- Engine processes the action
- Effects are applied
- Enemy takes its turn
- The loop continues until a win or loss condition is met

---

## 7. Sequence Diagram
Illustrates how different components interact during gameplay.

---

## 8. Testing Strategy
The system is tested using edge cases to ensure correct behavior:

- HP boundaries
- Item usage limits
- Status effect duration
- Cooldown behavior
- Enemy spawning logic

---

## 9. Challenges Faced
- Ensuring clear separation between components
- Managing interactions between multiple classes
- Maintaining a scalable and extendable design

---

## 10. Conclusion
The project demonstrates a structured and modular approach to building a turn-based combat system using object-oriented design.