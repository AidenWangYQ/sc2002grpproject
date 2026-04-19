# Responsibility Chart

| Class / Abstraction | Responsibility | Design Rationale |
|---|---|---|
| `BattleUI` | Defines interface for all UI interactions | Decouples UI from core logic (DIP) |
| `ConsoleBattleUI` | Handles CLI display and user interaction | Concrete UI implementation |
| `GameController` | Coordinates game setup and system initialisation | Acts as entry point and orchestrator |
| `BattleEngine` | Manages battle lifecycle (rounds, win/loss, spawning) | Central control component for battle flow |
| `TurnManager` | Determines turn order and executes actions per turn | Separates turn logic from engine (SRP) |
| `ActionResolver` | Handles combat resolution (damage, effects) | Centralises combat logic for consistency |
| `BattleState` | Stores current battle state (players, enemies, rounds) | Provides shared context across system |
| `Combatant` | Base abstraction for all battle participants | Enables polymorphism (LSP) |
| `Player` | Represents user-controlled combatant | Encapsulates inventory and skill logic |
| `Enemy` | Represents AI-controlled combatant | Delegates behaviour to strategy (OCP) |
| `Warrior` | Player class with Shield Bash skill | Encodes Warrior-specific configuration |
| `Wizard` | Player class with Arcane Blast skill | Encodes Wizard-specific behaviour |
| `Goblin` | Basic enemy type | Defines simple enemy stats |
| `Wolf` | Fast attack-oriented enemy | Defines alternative enemy stats |
| `Dragon` | Boss enemy with special skill | Demonstrates advanced enemy behaviour |
| `CombatAction` | Abstraction for all turn actions | Enables polymorphic execution (OCP) |
| `BasicAttackAction` | Executes standard attack | Encapsulates damage logic |
| `DefendAction` | Applies defensive effect | Encapsulates defence behaviour |
| `UseItemAction` | Executes item usage | Decouples item logic from engine |
| `UseSpecialSkillAction` | Executes skill usage | Integrates skill system into action flow |
| `ActionContext` | Carries data required for action execution | Reduces parameter coupling |
| `ActionResult` | Stores results of an action | Enables consistent output handling |
| `Item` | Abstraction for all usable items | Supports extensibility (OCP) |
| `Inventory` | Manages item storage and usage | Encapsulates item lifecycle |
| `HealPotion` | Restores HP | Encapsulates healing logic |
| `PowerStone` | Triggers skill without cooldown | Extends skill interaction |
| `SmokeBomb` | Applies temporary protection effect | Encapsulates defensive utility |
| `StatusEffect` | Base abstraction for effects | Enables extensible buff/debuff system |
| `StatusEffectManager` | Manages effect lifecycle | Centralises effect handling (SRP) |
| `StunEffect` | Prevents actions for duration | Encodes stun logic |
| `DefendEffect` | Reduces incoming damage | Encodes defensive buff |
| `RageEffect` | Increases critical chance | Encodes offensive buff |
| `SpecialSkill` | Abstraction for skills | Supports multiple skill types |
| `SpecialSkillUser` | Marks entities capable of using skills | Demonstrates ISP |
| `ShieldBashSkill` | Warrior skill (damage + stun) | Encapsulates Warrior behaviour |
| `ArcaneBlastSkill` | Wizard skill (AOE + buff) | Encapsulates Wizard behaviour |
| `DragonBreathSkill` | Dragon skill (high damage effect) | Encapsulates boss behaviour |
| `EnemyActionStrategy` | Determines enemy action logic | Enables extensible AI (Strategy pattern) |
| `BasicAttackEnemyActionStrategy` | Simple enemy AI | Default enemy behaviour |
| `DragonEnemyActionStrategy` | Boss enemy AI | More complex decision logic |
| `TurnOrderStrategy` | Determines turn order | Supports extensibility (OCP) |
| `SpeedBasedTurnOrderStrategy` | Orders by speed | Current rule implementation |
| `Level` | Represents game difficulty setup | Encapsulates configuration |
| `Wave` | Represents group of enemies | Supports multi-wave design |
| `SpawnManager` | Handles enemy spawning | Separates spawning from engine |
| `LevelFactory` | Creates predefined levels | Centralises configuration logic |