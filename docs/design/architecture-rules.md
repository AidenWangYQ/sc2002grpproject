# Architecture Rules 

## 1. Layer Separation
- UI classes (`BattleUI`, `ConsoleBattleUI`) must only handle user input and output.
- UI must not contain battle logic, damage calculations, or status-effect handling.
- Control components coordinate system flow but must not include presentation logic.

---

## 2. Battle Engine Responsibility
- `BattleEngine` orchestrates the overall battle lifecycle:
  - round progression
  - win/loss conditions
  - integration of turn and spawn systems
- `BattleEngine` must not implement detailed combat logic.
- All combat behaviour must be delegated to abstractions (e.g., `CombatAction`, `ActionResolver`).

---

## 3. Turn Management
- `TurnManager` is responsible for:
  - determining turn order
  - executing actions per turn
- Turn ordering must be delegated to `TurnOrderStrategy`.
- New turn ordering rules must be addable without modifying `TurnManager`.

---

## 4. Combatant Design
- All battle participants must be treated as `Combatant`.
- `Player` and `Enemy` must remain substitutable under `Combatant` (LSP).
- Combat-related state (HP, attack, defense, effects) must be encapsulated within `Combatant`.
- Status-effect logic must not be embedded directly in `Combatant`.

---

## 5. Action Design
- All actions must implement the `CombatAction` interface.
- Actions must encapsulate their own execution behaviour.
- `TurnManager` must execute actions polymorphically via `CombatAction`.
- New actions must be addable without modifying existing control logic (OCP).

---

## 6. Action Resolution
- All combat calculations must be handled by `ActionResolver`.
- This includes:
  - damage calculation
  - effect application
- Combat logic must not be duplicated across action classes.

---

## 7. Status Effect Design
- All effects must extend the `StatusEffect` abstraction.
- Effect lifecycle (duration, triggers) must be managed by `StatusEffectManager`.
- Effects must modify behaviour through polymorphic hooks (e.g., damage modifiers).
- Effect logic must not be scattered across engine or combatant classes.

---

## 8. Skill System Design
- Skills must implement the `SpecialSkill` interface.
- Only entities capable of using skills should implement `SpecialSkillUser` (ISP).
- Cooldown logic must remain within the skill-user abstraction.

---

## 9. Enemy Behaviour Design
- Enemy decision-making must be delegated to `EnemyActionStrategy`.
- `Enemy` must not contain hardcoded behaviour logic.
- New enemy behaviours must be implemented via new strategy classes (OCP).

---

## 10. Item Design
- All items must implement the `Item` abstraction.
- Item behaviour must be encapsulated within concrete item classes.
- Inventory management must be handled by the `Inventory` class.
- The engine must not directly implement item-specific logic.

---

## 11. Level and Spawning
- Level configuration must be created through `LevelFactory`.
- Runtime spawning logic must be handled by `SpawnManager`.
- Enemy wave logic must remain separate from the battle loop.

---

## 12. Documentation Consistency
- UML diagrams must accurately reflect the implemented system.
- Any architectural changes must be reflected in:
  - class diagrams
  - sequence diagrams
  - design documentation

---

## 13. Design Principles Enforcement
- The system must adhere to SOLID principles:
  - **SRP**: Each class has a single responsibility
  - **OCP**: New features added via extension, not modification
  - **LSP**: Subclasses must be substitutable for base classes
  - **ISP**: Interfaces must remain focused and minimal
  - **DIP**: High-level modules depend on abstractions