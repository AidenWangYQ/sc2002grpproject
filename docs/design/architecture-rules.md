# Architecture Rules (v1)

## 1. Layer separation
- UI classes in the `ui` package must only handle user input, display, and interaction flow.
- UI classes must not contain battle logic, damage calculations, cooldown handling, or status-effect logic.
- Control classes coordinate flow but should not contain presentation logic.

## 2. Battle engine responsibility
- `BattleEngine` is responsible for orchestrating battle rounds, turn progression, win/loss checks, and backup spawn triggering.
- `BattleEngine` must not hardcode the detailed logic of every action, item, or status effect.
- New mechanics should integrate via abstractions instead of large `if-else` branches in the engine.

## 3. Combatant design
- All battle participants must be handled through the `Combatant` abstraction where possible.
- `Player` and `Enemy` must remain substitutable as `Combatant`.
- Shared combatant logic should stay in `AbstractCombatant` to reduce duplication.

## 4. Action design
- Every turn action must implement `Action`.
- `BattleEngine` should execute actions polymorphically through the `Action` abstraction.
- New actions should be addable without changing the main battle loop.

## 5. Status effect design
- Persistent effects must implement `StatusEffect`.
- Duration and turn-based behaviour should remain inside effect classes.
- Effect logic should not be scattered across engine and combatant classes.

## 6. Item design
- All items must implement the `Item` abstraction.
- Item-specific behaviour must remain in concrete item classes.
- The engine should not directly implement Potion, Power Stone, or Smoke Bomb logic.

## 7. Turn order design
- Turn order must be determined through `TurnOrderStrategy`.
- The current implementation uses speed-based ordering, but alternative strategies should be addable later without changing `BattleEngine`.

## 8. Level and spawning
- Level setup should be created through `LevelFactory`.
- Initial wave and backup wave logic should be separated from the main battle loop where possible.

## 9. Documentation consistency
- UML diagrams must reflect the actual implementation.
- If code structure changes significantly, the class diagram, sequence diagram, and design notes must be updated.

## 10. Git discipline
- No direct commits to `main`.
- All feature branches branch from `dev`.
- Team members should work mainly within their assigned packages/files to reduce merge conflicts.