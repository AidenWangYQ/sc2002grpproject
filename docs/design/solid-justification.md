# SOLID Justification Notes (Final Version)

## SRP — Single Responsibility Principle

Each class in the system is designed to have a single, well-defined responsibility.

- `ConsoleBattleUI` handles only user interaction and display logic.
- `BattleEngine` orchestrates the overall battle lifecycle (rounds, win/loss, spawning).
- `TurnManager` manages turn sequencing and action execution.
- `ActionResolver` handles all combat resolution logic (damage calculation, effect application).
- `StatusEffectManager` manages the lifecycle of active effects.
- Individual item classes (`HealPotion`, `PowerStone`, `SmokeBomb`) each encapsulate a single behaviour.
- Individual effect classes (`StunEffect`, `DefendEffect`, `RageEffect`) each encapsulate one effect type.
- `LevelFactory` is responsible only for constructing level configurations.

👉 This separation improves maintainability and prevents logic duplication.

---

## OCP — Open/Closed Principle

The system is designed to allow extension without modifying existing code.

- New actions can be added by implementing `CombatAction` without modifying `TurnManager`.
- New items can be introduced by implementing `Item` without changing inventory or engine logic.
- New status effects can be added by extending `StatusEffect` without modifying existing effects.
- New enemy behaviours can be implemented via `EnemyActionStrategy` without modifying `Enemy`.
- New turn-order rules can be added by implementing `TurnOrderStrategy`.

👉 Core engine components remain unchanged while behaviour is extended through new classes.

---

## LSP — Liskov Substitution Principle

Subtypes are fully substitutable for their base types.

- `Player` and `Enemy` are both treated uniformly as `Combatant` in battle logic.
- Methods such as turn execution and damage handling operate on `Combatant` without needing to distinguish subtype.
- Concrete classes (`Warrior`, `Wizard`, `Goblin`, `Wolf`, `Dragon`) can be used wherever their parent abstraction is expected.

👉 This enables polymorphic handling throughout the battle system.

---

## ISP — Interface Segregation Principle

The system uses small, focused interfaces to avoid forcing classes to implement unnecessary behaviour.

- `CombatAction`, `Item`, `SpecialSkill`, and `EnemyActionStrategy` are all narrowly defined interfaces.
- The `SpecialSkillUser` interface ensures that only entities capable of using skills implement skill-related methods.
- Enemy AI behaviour is separated into `EnemyActionStrategy`, so players are not forced to implement AI logic.

👉 This prevents bloated interfaces and reduces unnecessary dependencies between components.

---

## DIP — Dependency Inversion Principle

High-level modules depend on abstractions rather than concrete implementations.

- `TurnManager` depends on `TurnOrderStrategy` rather than a fixed ordering implementation.
- `Enemy` depends on `EnemyActionStrategy` rather than hardcoded behaviour.
- The engine interacts with actions through the `CombatAction` interface.
- UI interaction is abstracted through `BattleUI`, allowing different UI implementations.

👉 This reduces coupling and improves flexibility of the system.

---

## Intended Design Patterns

- **Strategy Pattern**
  - `TurnOrderStrategy` (turn ordering)
  - `EnemyActionStrategy` (enemy AI behaviour)

- **Command-like Pattern**
  - `CombatAction` hierarchy encapsulates executable actions

- **Factory Pattern**
  - `LevelFactory` centralises level creation

- **Polymorphic Effect System**
  - `StatusEffect` hierarchy enables extensible combat effects

👉 These patterns work together to support extensibility and maintainability.