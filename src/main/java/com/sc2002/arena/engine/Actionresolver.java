package com.sc2002.arena.engine;
import sc2002.battle.domain.ActionType;
import sc2002.battle.domain.Combatant;
import sc2002.battle.domain.Enemy;
import sc2002.battle.domain.Player;
import sc2002.battle.domain.DefendEffect;
import sc2002.battle.domain.*;
import sc2002.battle.domain.ActionType;
import sc2002.battle.domain.SmokeBomb;
import sc2002.battle.domain.SmokeBombInvulnerabilityEffect;
import java.util.List;
import com.sc2002.arena.engine.*;
import com.sc2002.arena.ui.BattleUI;
import com.sc2002.arena.engine.CooldownManager;
import com.sc2002.arena.model.action.ActionType;
import com.sc2002.arena.model.combatant.Combatant;
import com.sc2002.arena.model.combatant.Enemy;
import com.sc2002.arena.model.combatant.Player;
import com.sc2002.arena.model.effect.DefendEffect;
import com.sc2002.arena.model.item.Item;
import com.sc2002.arena.model.item.SkipsCooldown;
import com.sc2002.arena.ui.BattleUI;
 
import java.util.List;
 
/**
 * Executes combat actions for both players and enemies.
 *
 * SRP  – sole responsibility: translate an ActionType into its mechanical
 *         effect on the game state. No turn-order or win-condition logic here.
 * OCP  – new ActionTypes can be added by extending the switch in
 *         resolvePlayerAction() without modifying BattleEngine.
 * DIP  – depends on Combatant/Player/Enemy abstractions and the BattleUI
 *         interface, never on concrete combatant or UI classes.
 *
 * Damage formula
 *   damage = max(0, attackerEffectiveAttack − targetEffectiveDefense)
 *   HP post-damage clamped to 0.
 *
 * SmokeBomb interaction:
 *   If the player has SmokeBomb active, enemy attacks deal 0 damage.
 *   Checked here before applying any damage.
 */
public class ActionResolver {
 
    private final CooldownManager cooldownManager;
    private final BattleUI ui;
 
    public ActionResolver(CooldownManager cooldownManager, BattleUI ui) {
        this.cooldownManager = cooldownManager;
        this.ui = ui;
    }

    /**
     * Resolve the player's chosen action for this turn.
     *
     * @param player      the acting player
     * @param action      chosen action type
     * @param target      selected enemy target (may be null for AOE/self actions)
     * @param allEnemies  all currently living enemies (needed for AOE)
     * @param context     current battle state (for cooldown management)
     */
    public void resolvePlayerAction(
            Player player,
            ActionType action,
            Enemy target,
            List<Enemy> allEnemies,
            BattleContext context) {
 
        switch (action) {
            case BASIC_ATTACK -> executeBasicAttack(player, target);
            case DEFEND       -> executeDefend(player);
            case SPECIAL_SKILL -> executeSpecialSkill(player, target, allEnemies);
            case USE_ITEM     -> executeUseItem(player, target, allEnemies);
        }
    }
 
    private void executeBasicAttack(Combatant attacker, Combatant target) {
        int damage = computeDamage(attacker, target);
        target.takeDamage(damage);
        ui.printAttack(attacker, target, damage);
 
        if (!target.isAlive()) {
            ui.printEliminated(target);
        }
    }
 
    private void executeDefend(Player player) {
        // Only apply if not already defending (prevents stacking)
        if (!player.hasDefendActive()) {
            player.addEffect(new DefendEffect());
        }
        ui.printDefend(player);
    }
 
    /**
     * Execute the player's special skill.
     * After execution, sets cooldown via CooldownManager (SRP: cooldown
     * logic lives in CooldownManager, not here).
     *
     * Warrior  → single target (target parameter used)
     * Wizard   → all enemies   (allEnemies used, target ignored)
     */
    private void executeSpecialSkill(
            Player player,
            Enemy singleTarget,
            List<Enemy> allEnemies) {
 
        List<Combatant> targets = determineSkillTargets(player, singleTarget, allEnemies);
        player.executeSpecialSkill(targets);
        cooldownManager.startCooldown(player);
    }

    private List<Combatant> determineSkillTargets(
            Player player,
            Enemy singleTarget,
            List<Enemy> allEnemies) {
 
        // Wizard's Arcane Blast hits all; everything else is single-target.
        if ("Arcane Blast".equals(player.getSpecialSkillName())) {
            return List.copyOf(allEnemies);
        }
        return (singleTarget != null) ? List.of(singleTarget) : List.of();
    }
 

    public void resolveItemUse(
            Player player,
            Item selectedItem,
            Enemy target,
            List<Enemy> allEnemies) {
 
        List<Combatant> targets = List.copyOf(allEnemies);
        selectedItem.use(player, targets);
        selectedItem.consume();
 
        // Power Stone does NOT trigger or reset cooldown 
        // SkipsCooldown marker interface flags this without instanceof on
        // a concrete class.
        if (selectedItem instanceof SkipsCooldown) {
            ui.printMessage(selectedItem.getName() + " skipped cooldown.");
        } else {
            // Apply cooldown logic for other items
            cooldownManager.startCooldown(player);  // Start cooldown for regular items
        }
    }
 
    private void executeUseItem(Player player, Enemy target, List<Enemy> allEnemies) {
        // Item selection is handled by BattleEngine (via UI prompt).
        // This overload exists for internal routing; actual item resolution
        // goes through resolveItemUse() once BattleEngine has the item.
        ui.printMessage("  (Item selection handled by BattleEngine)");
    }
 
    /**
     * Enemies always execute BasicAttack 
     *
     * SmokeBomb check: if the player has SmokeBomb active, damage = 0.
     * The formula still runs for consistency but is overridden.
     *
     * Extensibility (OCP): future enemy AI would inject an EnemyActionStrategy
     * into Enemy and call it here instead of always calling executeBasicAttack.
     *
     * @param enemy  the attacking enemy
     * @param player the player target
     */
    public void resolveEnemyAction(Enemy enemy, Player player) {
        if (player.isSmokeBombActive()) {
            ui.printSmokeBombBlock(enemy, player);
            // Damage is 0; no HP change
        } else {
            executeBasicAttack(enemy, player);
        }
    }

    private int computeDamage(Combatant attacker, Combatant target) {
        return Math.max(0, attacker.getEffectiveAttack() - target.getEffectiveDefense());
    }
}