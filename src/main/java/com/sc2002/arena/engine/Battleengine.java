package com.sc2002.arena.engine;

import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.combatant.Player;
import com.sc2002.arena.combatant.Enemy;
import com.sc2002.arena.item.Item;
import com.sc2002.arena.BattleUI.BattleUI;
import com.sc2002.arena.level.SpawnManager;
import com.sc2002.arena.action.CombatAction;
import java.util.List;
import java.util.stream.Collectors;
/*
 * Round flow:
 *   1. Increment round counter
 *   2. Print round header
 *   3. Get turn order from TurnManager
 *   4. For each combatant in turn order:
 *      a. Skip if dead (may have been killed mid-round)
 *      b. Apply turn-start effects via EffectManager
 *      c. If stunned → print skip, tick effects, do NOT tick cooldown
 *      d. Execute action (player: prompt via UI; enemy: BasicAttack)
 *      e. Tick effects and expire via EffectManager
 *      f. If player: tick cooldown via CooldownManager
 *      g. Check win/lose condition → end battle if met
 *      h. Check backup spawn trigger
 *   5. Print round summary
 */
public class Battleengine {
    private final BattleContext    context;
    private final TurnManager      turnManager;
    private final ActionResolver   actionResolver;
    private final EffectManager    effectManager;
    private final CooldownManager  cooldownManager;
    private final SpawnManager     spawnManager;
    private final BattleUI         ui;
 
    public Battleengine(
            BattleContext   context,
            TurnManager     turnManager,
            ActionResolver  actionResolver,
            EffectManager   effectManager,
            CooldownManager cooldownManager,
            SpawnManager    spawnManager,
            BattleUI        ui) {
 
        this.context        = context;
        this.turnManager    = turnManager;
        this.actionResolver = actionResolver;
        this.effectManager  = effectManager;
        this.cooldownManager = cooldownManager;
        this.spawnManager   = spawnManager;
        this.ui             = ui;
    }
 

    /**
     * Start the battle. Spawns the initial enemy wave then runs rounds
     * until a winner is determined.
     */
    public void startBattle() {
        spawnManager.spawnInitialWave(context);
 
        while (!context.isBattleOver()) {
            processRound();
        }
 
        if (context.didPlayerWin()) {
            ui.printVictoryScreen(context);
        } else {
            ui.printDefeatScreen(context);
        }
    }
 
    /*
     * Run one complete round: advance round counter, process every
     * combatant's turn in order, then print the round summary.
     */
    private void processRound() {
        context.incrementRound();
        ui.printRoundHeader(context.getRoundNumber());
 
        List<Combatant> turnOrder = turnManager.getTurnOrder(context);
 
        for (Combatant combatant : turnOrder) {
            
            if (!combatant.isAlive()) continue;
 
            executeTurn(combatant);

            if (checkAndHandleEndCondition()) return;
 
            spawnManager.triggerBackupIfReady(context);
        }
 
        ui.printRoundSummary(context);
    }
 
    /*
     * Execute a single combatant's turn, following the spec timing rules.
     */
    private void executeTurn(Combatant combatant) {
        ui.printTurnHeader(combatant);
 
        effectManager.applyEffectsOnTurnStart(combatant);

        if (combatant.isStunned()) {
            ui.printStunSkip(combatant);
            effectManager.tickAndExpire(combatant);
            return;
        }

        if (combatant instanceof Player player) {
            executePlayerTurn(player);
        } else if (combatant instanceof Enemy enemy) {
            executeEnemyTurn(enemy);
        }

        effectManager.tickAndExpire(combatant);
 
        if (combatant instanceof Player player) {
            cooldownManager.tickCooldown(player);
        }
    }
 
 
    private void executePlayerTurn(Player player) {
        List<Enemy> livingEnemies = context.getLivingEnemies();
 
        // Prompt action choice via UI (never directly here — DIP)
        ActionType chosenAction = ui.promptActionChoice(player, livingEnemies);

        while (chosenAction == ActionType.SPECIAL_SKILL
                && !cooldownManager.isSkillReady(player)) {
            ui.printMessage("  Special skill is on cooldown ("
                    + player.getSkillCooldown() + " round(s) remaining). Choose again.");
            chosenAction = ui.promptActionChoice(player, livingEnemies);
        }

        switch (chosenAction) {
            case BASIC_ATTACK -> {
                Enemy target = ui.promptTargetSelection(livingEnemies);
                actionResolver.resolvePlayerAction(
                        player, ActionType.BASIC_ATTACK, target, livingEnemies, context);
            }
            case DEFEND -> actionResolver.resolvePlayerAction(
                    player, ActionType.DEFEND, null, livingEnemies, context);
 
            case SPECIAL_SKILL -> {
                // Wizard hits all (target = null); Warrior needs a target
                Enemy target = needsSingleTarget(player)
                        ? ui.promptTargetSelection(livingEnemies)
                        : null;
                actionResolver.resolvePlayerAction(
                        player, ActionType.SPECIAL_SKILL, target, livingEnemies, context);
            }
            case USE_ITEM -> {
                List<Item> usable = player.getItems().stream()
                        .filter(i -> !i.isUsed())
                        .collect(Collectors.toList());
                Item chosen = ui.promptItemSelection(usable);
                // Target needed for PowerStone (passes skill targets); others ignore it
                Enemy target = livingEnemies.isEmpty() ? null
                        : ui.promptTargetSelection(livingEnemies);
                actionResolver.resolveItemUse(player, chosen, target, livingEnemies);
            }
        }
    }
 
    private boolean needsSingleTarget(Player player) {
        return !"Arcane Blast".equals(player.getSpecialSkillName());
    }

    private void executeEnemyTurn(Enemy enemy) {
        Player player = context.getPlayer();
        actionResolver.resolveEnemyAction(enemy, player);
    }

    private boolean checkAndHandleEndCondition() {
        if (!context.getPlayer().isAlive()) {
            context.endBattle(false);
            return true;
        }

        boolean noEnemiesLeft = context.getLivingEnemies().isEmpty();
        boolean backupPending = !context.isBackupSpawned()
                && spawnManager.triggerBackupIfReady(context);
 
        if (noEnemiesLeft && !backupPending) {
            context.endBattle(true);
            return true;
        }
 
        return false;
    }
}

