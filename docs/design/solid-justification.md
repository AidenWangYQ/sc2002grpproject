# SOLID Justification Notes (v1)

## SRP — Single Responsibility Principle
- `ConsoleUI` is responsible only for CLI interaction.
- `BattleEngine` is responsible only for battle flow orchestration.
- `Potion`, `PowerStone`, and `SmokeBomb` each encapsulate one item behaviour.
- `StunEffect`, `DefendEffect`, and `SmokeBombEffect` each encapsulate one persistent combat effect.
- `LevelFactory` is responsible only for creating level configurations.

## OCP — Open/Closed Principle
- New actions can be added by implementing `Action` without changing the overall battle loop.
- New status effects can be added by implementing `StatusEffect` without modifying existing effect classes.
- New items can be added by implementing `Item` without changing item consumers.
- New turn-order rules can be introduced by implementing `TurnOrderStrategy` without modifying `BattleEngine`.

## LSP — Liskov Substitution Principle
- `Player` and `Enemy` are both treated as `Combatant` in battle flow and turn-order logic.
- Concrete classes such as `Warrior`, `Wizard`, `Goblin`, and `Wolf` can be used wherever their parent abstraction is expected.

## ISP — Interface Segregation Principle
- The design uses several small, focused interfaces instead of one bloated interface:
  - `Action`
  - `StatusEffect`
  - `Item`
  - `TurnOrderStrategy`
- This prevents unrelated classes from depending on methods they do not need.

## DIP — Dependency Inversion Principle
- `BattleEngine` depends on abstractions such as `TurnOrderStrategy` rather than concrete order implementations.
- Higher-level orchestration should interact with `Action`, `Item`, and `StatusEffect` through abstractions.
- Concrete item and effect behaviour remains in low-level implementations, not in the engine.

## Intended design patterns
- **Strategy Pattern**: `TurnOrderStrategy` and `SpeedBasedTurnOrderStrategy`
- **Command-like action abstraction**: `Action` hierarchy
- **Factory Pattern**: `LevelFactory`
- **Polymorphic effect model**: `StatusEffect` hierarchy