package com.sc2002.arena.engine;

import java.util.List;

import com.sc2002.arena.battleUI.BattleUI;
import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.level.SpawnManager;

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

    public void initialize() {
        spawnManager.spawnInitialWave(context);
    }

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
