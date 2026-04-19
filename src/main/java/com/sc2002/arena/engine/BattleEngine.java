package com.sc2002.arena.engine;

import java.util.List;

import com.sc2002.arena.battleUI.BattleUI;
import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.level.SpawnManager;
/**
 * Orchestrates the entire battle loop from start to finish.
 *
 * BattleEngine is the conductor of the system — it delegates every specific
 * concern to a focused collaborator and never handles those concerns itself:
 *   TurnManager   → determines turn order and processes each combatant's turn
 *   SpawnManager  → handles initial and backup enemy wave spawning
 *   BattleUI      → all output to the player (never calls System.out directly)
 *
 * SOLID:
 *   SRP – manages round progression and end conditions only; no action/effect logic.
 *   DIP – depends on BattleUI interface and BattleState/TurnManager abstractions,
 *         never on ConsoleBattleUI or any concrete combatant class.
 */
public class BattleEngine {
    private final BattleState context;
    private final TurnManager turnManager;
    private final SpawnManager spawnManager;
    private final BattleUI ui;

    public BattleEngine(
            BattleState context,
            TurnManager turnManager,
            SpawnManager spawnManager,
            BattleUI ui
    ) {
        this.context = context;
        this.turnManager = turnManager;
        this.spawnManager = spawnManager;
        this.ui = ui;
    }
     /**
     * Spawns the initial enemy wave into BattleState before the first round.
     * Called once at the start of runBattle().
     */
    public void initialize() {
        spawnManager.spawnInitialWave(context);
    }
    /**
     * Runs the full battle loop until a winner is determined.
     *
     * Flow:
     *   1. Spawn initial wave (initialize)
     *   2. Loop: process rounds until outcome is no longer ONGOING
     *   3. Print victory or defeat screen
     *
     * @return the final BattleOutcome (VICTORY or DEFEAT)
     */
    public BattleOutcome runBattle() {
        initialize();

        BattleOutcome outcome = checkVictoryConditions();
        while (outcome == BattleOutcome.ONGOING) {
            processRound();
            outcome = checkVictoryConditions();
        }

        if (outcome == BattleOutcome.VICTORY) {
            ui.printVictoryScreen(context);
        } else {
            ui.printDefeatScreen(context);
        }
        return outcome;
    }

    public BattleOutcome checkVictoryConditions() {
        if (!context.getPlayer().isAlive()) {
            return BattleOutcome.DEFEAT;
        }
        if (context.getLivingEnemies().isEmpty() && (context.isBackupSpawned() || !spawnManager.hasBackupWave())) {
            return BattleOutcome.VICTORY;
        }
        return BattleOutcome.ONGOING;
    }
    /**
     * Processes one full round of combat.
     *
     * Round flow:
     *   1. Increment round counter and print round header
     *   2. Generate turn order via TurnManager (sorted by speed, highest first)
     *   3. For each combatant in turn order:
     *      a. Process their turn via TurnManager
     *      b. Print the action result via UI
     *      c. Check win/lose — return immediately if battle has ended
     *      d. Trigger backup spawn if all active enemies are now defeated
     *   4. Apply round-end effects and remove expired effects for all living combatants
     *   5. Print round summary
     */
    private void processRound() {
        context.incrementRound();
        ui.printRoundHeader(context.getRoundNumber());

        List<Combatant> turnOrder = turnManager.generateTurnOrder(context);
        for (Combatant combatant : turnOrder) {
            TurnResolution resolution = turnManager.processTurn(combatant, context);
            if (resolution.actionResult() != null) {
                ui.printActionResult(resolution.actionResult());
            }

            BattleOutcome outcome = checkVictoryConditions();
            if (outcome != BattleOutcome.ONGOING) {
                return;
            }

            if (context.getLivingEnemies().isEmpty()) {
                spawnManager.triggerBackupIfReady(context);
            }
        }

        for (Combatant combatant : context.getLivingCombatants()) {
            combatant.onRoundEnd(context);
            combatant.removeExpiredEffects();
        }
        ui.printRoundSummary(context);
    }
}
