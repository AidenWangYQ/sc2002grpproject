# 🛡️ SC2002 Turn-Based Combat Arena

A modular, extensible turn-based combat system built in Java, designed using **Object-Oriented Design Principles (OODP)** and **SOLID principles**.

---

## 📌 Overview

This project implements a command-line turn-based battle game where a player-controlled character fights AI-controlled enemies using:

- ⚔️ Combat Actions (Attack, Defend, Item, Skill)
- 🧪 Items & Inventory System
- ✨ Status Effects (Stun, Buffs, Invulnerability)
- 🔥 Special Skills with Cooldowns
- 🧠 Extensible Enemy Behaviour (Strategy Pattern)
- 🌊 Multi-wave Level System

The system is designed with a strong focus on **modularity, extensibility, and clean architecture**.

---

## 🏗️ Architecture

The system follows a **layered Boundary–Control–Entity (BCE)** architecture:

### 🔹 Boundary (UI Layer)
- `BattleUI` (interface)
- `ConsoleBattleUI`

👉 Handles all user interaction  
👉 Fully decoupled from core logic (**DIP**)

---

### 🔹 Control (Engine Layer)
- `GameController` → setup + orchestration
- `BattleEngine` → battle lifecycle
- `TurnManager` → turn order + flow
- `ActionResolver` → damage & effect resolution
- `SpawnManager` → enemy spawning

👉 Clear separation of responsibilities (**SRP**)  
👉 Centralised combat resolution for consistency

---

### 🔹 Entity (Domain Layer)
- `Combatant` (abstract)
- `Player`, `Enemy`
- `Item`, `SpecialSkill`, `StatusEffect`

👉 Core game logic and state  
👉 Supports polymorphic behaviour (**LSP, OCP**)

---

## 🎮 Key Features

### ⚔️ Combat System
- Turn-based gameplay using `TurnOrderStrategy`
- Damage formula:

```text
max(0, Attack - Defense)
```

---

### 🧠 Strategy Pattern (Extensibility)
- `EnemyActionStrategy`
- `TurnOrderStrategy`

👉 New behaviours can be added **without modifying existing code** (**OCP**)

---

### ✨ Status Effect System
- `StatusEffect` abstraction
- Managed via `StatusEffectManager`

Examples:
- Stun → skip turns
- Defend → reduce incoming damage
- Smoke Bomb → temporary invulnerability

👉 Decoupled effect lifecycle (**SRP + OCP**)

---

### 🔥 Skill System
- `SpecialSkill` interface
- Cooldown-based execution
- Implemented only by capable entities via `SpecialSkillUser` (**ISP**)

---

### 🎒 Item & Inventory System
- `Item` abstraction
- `Inventory` manages usage + consumption

Examples:
- Potion → heal
- Power Stone → free skill usage
- Smoke Bomb → negate damage

---

### 🌊 Level & Wave System
- `Level` + `LevelFactory`
- `SpawnManager` handles runtime spawning

👉 Supports:
- Multiple difficulty levels
- Backup enemy waves

---

## 🧩 UML Diagrams

### 📊 Class Diagrams
- System Overview
- Core Architecture
- Extensibility Mechanisms

👉 Show:
- Layered design
- Inheritance hierarchy
- Strategy + abstraction usage

---

### 🔄 Sequence Diagram

Models required scenario:

1. Enemy attacks
2. Player defends
3. Reduced damage
4. Skill (Stun)
5. Enemy skips turn
6. Player uses item
7. Enemy skips again
8. Final attack

👉 Demonstrates full interaction between:
- UI → Control → Entity layers

---

## 🧠 SOLID Principles Applied

| Principle | Implementation |
|----------|---------------|
| **SRP** | TurnManager, ActionResolver, StatusEffectManager |
| **OCP** | Actions, Items, Skills, Effects via interfaces |
| **LSP** | Player & Enemy interchangeable as Combatant |
| **ISP** | `SpecialSkillUser` avoids bloated interfaces |
| **DIP** | Engine depends on abstractions (UI, strategies) |

---

## ⚖️ Design Trade-offs

### ✅ Strategy Pattern vs Hardcoding
✔ Extensible  
❌ More classes

### ✅ Centralised ActionResolver
✔ Consistent logic  
❌ Strong dependency on one component

### ✅ StatusEffectManager
✔ Clean separation  
❌ Slight complexity overhead

---

## 🧪 Testing

Edge cases tested:

- HP boundary (no negative values)
- Potion cap (no overheal)
- Stun duration correctness
- Smoke Bomb duration
- Skill cooldown logic
- Backup spawning behaviour

---

## ⚠️ Limitations

- CLI-based UI (no GUI)
- Simple enemy AI (basic attack only)
- No save/load system
- Limited game content

---

## 🚀 Future Improvements

- GUI implementation
- Advanced AI strategies
- More characters / skills / effects
- Save & load system
- Game balancing enhancements

---

## 🚀 How to Run

### 📦 Prerequisites

Ensure the following are installed:

- Java JDK **25**
- Maven

Check versions:

```bash
java -version
mvn -version
```

---

### 🛠️ Compile the Project

From the project root directory (where `pom.xml` is located), run:

```bash
mvn clean compile
```

---

### ▶️ Run the Application

```bash
mvn exec:java
```

---

### 🔁 Alternative: Run without Maven (Fallback)

If Maven execution does not work, you can run the program manually:

#### Step 1: Compile (if not already done)

```bash
mvn clean compile
```

#### Step 2: Run using Java

```bash
java -cp target/classes com.sc2002.arena.Main
```

---

### 🎮 How to Play

1. Choose your character:
   - Warrior
   - Wizard

2. Select two starting items:
   - Heal Potion
   - Power Stone
   - Smoke Bomb
   - Rage Potion

3. Choose a difficulty level:
   - Easy
   - Medium
   - Hard
   - Boss

4. During battle, choose one action per turn:
   - Basic Attack
   - Defend
   - Use Item
   - Use Special Skill

---

### ⚠️ Notes

- The game runs entirely in the command line (CLI).
- Input must be numeric; invalid input will prompt the user to re-enter a value.
- Commands should be run from the project root directory.
- This project is configured for **Java 25**. Using an older Java version may cause compilation or runtime errors.

---
## 👨‍💻 Authors

SC2002 Object-Oriented Design & Programming  
NTU AY25/26 Semester 2

- Aiden Wang Yuqi
- Ng Yan En
- Ramachandran Saichandar
- Sooraj Senthil
- Elliot Heng Zheng Yang