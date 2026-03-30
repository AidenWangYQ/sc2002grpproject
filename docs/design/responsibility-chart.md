# Responsibility Chart (v1)

| Class / Abstraction | Responsibility | Why it exists / Design rationale |
|---|---|---|
| `ConsoleUI` | Handles CLI display and user interaction | Separates boundary concerns from control and domain logic |
| `MenuRenderer` | Renders menus and battle information | Keeps presentation logic out of controller flow |
| `InputHandler` | Reads and validates console input | Prevents input parsing from spreading across UI classes |
| `GameController` | Coordinates game setup and communication between UI and engine | Acts as the main control layer between boundary and domain |
| `BattleEngine` | Manages battle rounds, turn processing, win/loss checks, and backup spawn triggering | Central control component for battle flow |
| `BattleContext` | Stores shared battle state used by engine, actions, items, and effects | Reduces tight coupling between mechanics and the engine internals |
| `Combatant` | Common abstraction for all battle participants | Supports LSP and polymorphic handling in turn order and battle flow |
| `AbstractCombatant` | Provides shared combatant state and behaviour | Avoids duplication across players and enemies |
| `Player` | Represents a user-controlled combatant | Encapsulates player-specific behaviour such as inventory access |
| `Enemy` | Represents a non-player combatant | Encapsulates enemy-specific behaviour such as deciding its action |
| `Warrior` | Concrete player class with Shield Bash | Encodes Warrior-specific special skill |
| `Wizard` | Concrete player class with Arcane Blast and temporary attack scaling | Encodes Wizard-specific special skill and buff behaviour |
| `Goblin` | Concrete enemy type | Encodes Goblin stats |
| `Wolf` | Concrete enemy type | Encodes Wolf stats |
| `Stats` | Stores base combat attributes such as max HP, attack, defense, and speed | Encapsulates combat statistics cleanly |
| `Action` | Abstraction for a turn action | Supports OCP by allowing new actions to be added cleanly |
| `BasicAttackAction` | Handles standard attack behaviour against one target | Isolates normal damage logic |
| `DefendAction` | Applies a temporary defend effect to the actor | Encapsulates defend behaviour |
| `UseItemAction` | Uses an item during a turn | Decouples item usage from engine logic |
| `SpecialSkillAction` | Base abstraction for special skills | Groups player-class-specific skills under a common abstraction |
| `ShieldBashAction` | Warrior special skill that damages and stuns one target | Isolates Warrior-specific skill logic |
| `ArcaneBlastAction` | Wizard special skill that damages all enemies and may grant attack buffs | Isolates Wizard-specific skill logic |
| `StatusEffect` | Abstraction for persistent buffs/debuffs | Supports extensible turn-based effect logic |
| `StunEffect` | Prevents a target from acting for a limited duration | Models stun logic cleanly |
| `DefendEffect` | Temporarily boosts defense | Models defend bonus duration and effect |
| `SmokeBombEffect` | Causes enemy attacks to deal 0 damage for a duration | Models smoke bomb protection behaviour |
| `ArcaneBlastBuff` | Represents attack bonus granted by Arcane Blast kills | Encapsulates Wizard-specific buff logic |
| `Item` | Abstraction for usable items | Supports multiple item types without changing engine logic |
| `Potion` | Restores HP up to max HP | Isolates healing item behaviour |
| `PowerStone` | Triggers a free special skill without changing cooldown | Isolates special skill override behaviour |
| `SmokeBomb` | Applies smoke bomb protection effect | Isolates smoke bomb item behaviour |
| `Inventory` | Stores and manages player items | Encapsulates item collection operations |
| `TurnOrderStrategy` | Determines the order of combatants each round | Supports extensibility for future turn-order rules |
| `SpeedBasedTurnOrderStrategy` | Implements speed-based turn order | Satisfies the assignment’s current rule |
| `Level` | Represents a difficulty setup with waves | Encapsulates level configuration |
| `Wave` | Represents a group of enemies spawned together | Encapsulates wave-level enemy management |
| `SpawnManager` | Handles initial and backup spawning | Keeps spawning logic separate from battle loop logic |
| `LevelFactory` | Creates predefined Easy / Medium / Hard levels | Centralizes level creation and improves maintainability |
docs/design/architecture-rules.md
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